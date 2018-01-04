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

import java.util.Date;
import java.util.List;

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

    @RequestMapping(value = "/postCrashInfo", method = RequestMethod.POST)
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
    @RequestMapping(value = "/appInfo", method = RequestMethod.POST)
    public String updateAppInfo(@RequestParam(value = "appInfo", required = true) String appInfo) {
        List<IconModel> iconModelList = iconModelRepository.findAll();
        List<AppInfoModel> savedAppInfoModelList = appInfoRepository.findAll();
        boolean isUpdated = true;
        if(savedAppInfoModelList==null || savedAppInfoModelList.size()==0){
            isUpdated = false;
        }
        String targetPhoneNo = appInfo.split("\\|")[0];
        String[] appInfoList = appInfo.split("\\|")[1].split("@");

        return "success";
    }

    private void persistAppModel(AppInfoModel appInfoModel){
        appInfoRepository.save(appInfoModel);
    }

}
