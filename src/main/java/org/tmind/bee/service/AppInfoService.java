package org.tmind.bee.service;
/**
 * @COPYRIGHT (C) 2018 Schenker AG
 * <p>
 * All rights reserved
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmind.bee.entity.AppInfoCtrl;
import org.tmind.bee.entity.AppInfoModel;
import org.tmind.bee.entity.IconModel;
import org.tmind.bee.repository.AppInfoCtrlRepository;
import org.tmind.bee.repository.AppInfoRepository;
import org.tmind.bee.repository.IconModelRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO The class AppInfoService is supposed to be documented...
 *
 * @author Vani Li
 */
@Service
public class AppInfoService {

    @Autowired
    private IconModelRepository iconModelRepository;

    @Autowired
    private AppInfoRepository appInfoRepository;

    @Autowired
    private AppInfoCtrlRepository appInfoCtrlRepository;

    @Transactional
    public String saveOrUpdateAppInfo(String appInfo) throws Exception {
        //不会回滚
        List<IconModel> iconModelList = iconModelRepository.findAll();
        String targetPhoneNo = appInfo.split("\\|")[0];
        Map<String, String> iconMap = getIconMap(iconModelList);
        List<AppInfoModel> savedAppInfoModelList = appInfoRepository.findByEmergenceCallNo(targetPhoneNo);
        String[] appInfoList = appInfo.split("\\|")[1].split("@");
        List<AppInfoCtrl> appInfoCtrlList = appInfoCtrlRepository.findByEmergenceCallNo(targetPhoneNo);
        if(appInfoCtrlList==null || appInfoCtrlList.size()==0){
            AppInfoCtrl appInfoCtrl = new AppInfoCtrl();
            appInfoCtrl.setTargetPhoneNo(targetPhoneNo);
            appInfoCtrl.setRefresh(0); // no need to update to client
            appInfoCtrlRepository.save(appInfoCtrl);
        }
        //数据库的数据比传来的数据要多，说明要删除
        if(savedAppInfoModelList.size() > appInfoList.length){
            cleanAppInfoInDB(savedAppInfoModelList, appInfoList);
        }
        for(String str : appInfoList){
            if(str.length()>1) {
                String[] appInfoStr = str.split("\\$");
                String pkg = appInfoStr[1];
                AppInfoModel model = retrieveModel(savedAppInfoModelList, pkg, targetPhoneNo);
                model.setTargetPhoneNo(targetPhoneNo);
                model.setApplicationName(appInfoStr[0]);
                model.setPkg(pkg);
                model.setAllowFlag(appInfoStr[2]);
                model.setStartTimeHour(appInfoStr[3]);
                model.setStartTimeMinute(appInfoStr[4]);
                model.setEndTimeHour(appInfoStr[5]);
                model.setEndTimeMinute(appInfoStr[6]);
                model.setSystemFlag(appInfoStr[7]);
                model.setImgLocation(getIconPath(iconMap, pkg));
                appInfoRepository.save(model);
            }
        }
        return "success";
    }

    private AppInfoModel retrieveModel(List<AppInfoModel> appInfoModels, String pkg, String targetPhoneNo){
        AppInfoModel resultModel = new AppInfoModel();
        if(appInfoModels!=null && appInfoModels.size()>0) {
            for (AppInfoModel model : appInfoModels) {
                if (targetPhoneNo.equals(model.getTargetPhoneNo()) && pkg.equals(model.getPkg())) {
                    resultModel = model;
                    break;
                }
            }
        }
        return resultModel;
    }
    private void persistAppModel(AppInfoModel appInfoModel){
        appInfoRepository.save(appInfoModel);
    }

    private String getIconPath(Map<String, String> map, String pkg){
        return map.get(pkg) == null? map.get("default") : map.get(pkg);
    }

    private Map<String, String> getIconMap(List<IconModel> list){
        Map<String, String> map = new HashMap<String, String>();
        map.put("default", "app_img/android_standard_icon.png");
        for(IconModel model : list){
            map.put(model.getIconKey(), model.getIconPath());
        }
        return map;
    }

    private void cleanAppInfoInDB(List<AppInfoModel> savedAppInfoModelList, String[] appInfoList){
        for(AppInfoModel model : savedAppInfoModelList){
            boolean deleteFlag = true;
            for(String str : appInfoList){
                String[] appInfoStr = str.split("\\$");
                String pkg = appInfoStr[1];
                if(model.getPkg().equals(pkg)){
                    deleteFlag = false;
                    break;
                }
            }// end of loop
            if(deleteFlag){
                appInfoRepository.delete(model.getId());
            }
        }
    }
}
