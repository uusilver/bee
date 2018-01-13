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
import org.tmind.bee.service.AppInfoService;

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
    private AppInfoService appInfoService;

    @Autowired
    private AppInfoRepository appInfoRepository;

    @Autowired
    private AppInfoCtrlRepository appInfoCtrlRepository;

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
    public String updateAppInfo(@RequestParam(value = "appInfo", required = true) String appInfo) throws Exception {
//        appInfo = new String(appInfo.getBytes("ISO-8859-1"),"UTF-8");
        return appInfoService.saveOrUpdateAppInfo(appInfo);
    }

    /**
     * 根据emergencePhoneNo获取UserInfo，接收ID参数
     * @param emergencePhoneNo
     * @return
     */
    @GetMapping("/rest/retreiveAppInfo/{emergencePhoneNo}")
    public String retreiveAppInfo(@PathVariable("emergencePhoneNo") String emergencePhoneNo){
        List<AppInfoCtrl> appInfoCtrlList = appInfoCtrlRepository.findByEmergenceCallNo(emergencePhoneNo);
        if(appInfoCtrlList!=null && appInfoCtrlList.size()>0){
            AppInfoCtrl appInfoCtrl = appInfoCtrlList.get(0);
            if(appInfoCtrl.getRefresh()>0){
                List<AppInfoModel> list = appInfoRepository.findByEmergenceCallNo(emergencePhoneNo);
                StringBuilder result = new StringBuilder();
                result.append(emergencePhoneNo +"|");
                if(list!=null && list.size()>0)
                    for(AppInfoModel appInfoModel : list){
                        result.append(appInfoModel.getApplicationName()+"$"); //setApplicationName
                        result.append(appInfoModel.getPkg()+"$"); //setPkg
                        result.append(appInfoModel.getAllowFlag()+"$"); //setAllowFlag
                        result.append(appInfoModel.getStartTimeHour()+"$"); //setStartTimeHour
                        result.append(appInfoModel.getStartTimeMinute()+"$"); //setStartTimeMinute
                        result.append(appInfoModel.getEndTimeHour()+"$"); //setEndTimeHour
                        result.append(appInfoModel.getEndTimeMinute()+"$"); //setEndTimeMinute
                        result.append(appInfoModel.getSystemFlag()+"$"); //setSystemFlag
                        result.append("@");
                    }
                appInfoCtrl.setRefresh(0); // no need to update to client
                appInfoCtrlRepository.save(appInfoCtrl);
                return result.toString();
            }//need to push
        }
        return "0";
    }


    /**
     * 根据emergencePhoneNo获取UserInfo，接收ID参数
     * @param emergencePhoneNo
     * @return
     */
    @GetMapping("/rest/isPhoneNotExist/{emergencePhoneNo}")
    public String isPhoneNotExist(@PathVariable("emergencePhoneNo") String emergencePhoneNo){
        List<UserInfo> userInfos = userInfoRepository.findByEmergencePhoneNo(emergencePhoneNo);
        if(userInfos!=null && userInfos.size()>0){
            return "false";
        }else {
            return "true";
        }
    }
}
