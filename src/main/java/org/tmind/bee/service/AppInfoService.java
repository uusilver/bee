package org.tmind.bee.service;
/**
 * @COPYRIGHT (C) 2018 Schenker AG
 * <p>
 * All rights reserved
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmind.bee.entity.AppInfoModel;
import org.tmind.bee.entity.IconModel;
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

    @Transactional
    public String saveOrUpdateAppInfo(String appInfo) throws Exception {
        //不会回滚
        List<IconModel> iconModelList = iconModelRepository.findAll();
        Map<String, String> iconMap = getIconMap(iconModelList);
        List<AppInfoModel> savedAppInfoModelList = appInfoRepository.findAll();

        String targetPhoneNo = appInfo.split("\\|")[0];
        String[] appInfoList = appInfo.split("\\|")[1].split("@");
        for(String str : appInfoList){
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

}