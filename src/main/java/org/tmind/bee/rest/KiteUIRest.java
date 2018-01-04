package org.tmind.bee.rest;
/**
 * @COPYRIGHT (C) 2017 Schenker AG
 * <p>
 * All rights reserved
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.tmind.bee.entity.*;
import org.tmind.bee.repository.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO The class KiteUIRest is supposed to be documented...
 *
 * @author Vani Li
 */

@RestController
@EnableAutoConfiguration
public class KiteUIRest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private HelpInfoRepository helpInfoRepository;

    @Autowired
    private CrashInfoRepository crashInfoRepository;

    @Autowired
    private IconModelRepository iconModelRepository;

    @Autowired
    private AppInfoRepository appInfoRepository;

    //imei+emergencephone+password
    @RequestMapping("/rest/regist/{info:.+}")
    UserInfo regist(@PathVariable String info) {
        String[] pool = info.split("\\+");
        String imei = pool[0];
        String emergencePhone = pool[1];
        String parentCtrlPwd = pool[2];
        UserInfo userInfo = new UserInfo();
        userInfo.setImei(imei);
        userInfo.setEmergencePhoneNo(emergencePhone);
        userInfo.setParentCtrlPwd(parentCtrlPwd);
        return userInfoRepository.save(userInfo);
    }

    /**
     * 根据emergencePhoneNo获取UserInfo，接收ID参数
     * @param emergencePhoneNo
     * @return
     */
    @GetMapping("/rest/getUserInfoByEmergencePhoneNo/{emergencePhoneNo}")
    public UserInfo getUserInfoByEmergencePhoneNo(@PathVariable("emergencePhoneNo") String emergencePhoneNo){
        List<UserInfo> list = userInfoRepository.findByEmergencePhoneNo(emergencePhoneNo);
        if(list!=null && list.size()>0)
            return list.get(0);
        return null;
    }


    //telphone+imei+lng+lat+emergencephone+calldate
    @RequestMapping("/rest/insertHelpInfo/{info:.+}")
    HelpLocationInfo insertHelpInfo(@PathVariable String info) {
        String[] pool = info.split("\\+");
        String telphone = pool[0];
        String imei = pool[1];
        String lng = pool[2];
        String lat = pool[3];
        String emergencephone = pool[4];
        String calldate = pool[5];

        HelpLocationInfo helpLocationInfo = new HelpLocationInfo();
        helpLocationInfo.setPhoneNo(telphone);
        helpLocationInfo.setImei(imei);
        helpLocationInfo.setLng(lng);
        helpLocationInfo.setLat(lat);
        helpLocationInfo.setEmergenceCallNo(emergencephone);
        helpLocationInfo.setCallDate(calldate);
        return helpInfoRepository.save(helpLocationInfo);
    }

    @RequestMapping(value = "/rest/postCrashInfo", method = RequestMethod.POST)
    public String postCrashInfo(@RequestParam(value = "crashInfo", required = true) String crashInfo) {
        CrashInfoModel model = new CrashInfoModel();
        model.setCrashinfo(crashInfo);
        model.setCrashDate(new Date().toString());
        model.setCheckFlag("false");
        crashInfoRepository.save(model);
        return "success";
    }


    /**
     *
     * 13851483034 | applicationName $ packagename @   applicationName $ packagename @
     * @param appInfo
     * @return
     */
    @RequestMapping(value = "/rest/appInfo", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    public String updateAppInfo(@RequestParam(value = "appInfo", required = true) String appInfo) throws UnsupportedEncodingException {
//        appInfo = new String(appInfo.getBytes("ISO-8859-1"),"UTF-8");
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
