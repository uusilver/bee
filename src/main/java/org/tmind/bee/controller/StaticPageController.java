package org.tmind.bee.controller;
/**
 * @COPYRIGHT (C) 2017 Schenker AG
 * <p>
 * All rights reserved
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.tmind.bee.config.WebSecurityConfig;
import org.tmind.bee.config.WebSecurityConfig2;
import org.tmind.bee.entity.*;
import org.tmind.bee.repository.*;
import org.tmind.bee.service.AppInfoService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * TODO The class StaticPageController is supposed to be documented...
 *
 * @author Vani Li
 */
@Controller
public class StaticPageController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AppInfoRepository appInfoRepository;

    @Autowired
    private AppInfoCtrlRepository appInfoCtrlRepository;

    @Autowired
    private AppInfoService appInfoService;

    @GetMapping("/login")
    public  String login() {
        return"login";
    }

    @GetMapping("/home")
    public  String home() {
        return"home";
    }


    @PostMapping("/doLogin")
    @ResponseBody
    String doLogin(@RequestParam("emergencePhoneNo") String emergencePhoneNo, @RequestParam("pwd") String pwd, HashMap<String,Object> map, HttpSession session) {
        List<UserInfo> userInfos = userInfoRepository.findByEmergencePhoneNoAndPwd(emergencePhoneNo, pwd);
        session.setAttribute(WebSecurityConfig.SESSION_KEY, emergencePhoneNo);
        if(userInfos.size()>0){
//            return "redirect:/getHelpInfoByEmergencePhoneNo/"+emergencePhoneNo;
            return "home";
        }
        map.put("msg","用户名或者密码错误");
        return "error";
    }

    @GetMapping("/getAppInfoFromDb")
    @ResponseBody
    public String getAppInfoFromDb(HttpSession session){
        String emergencePhoneNo = (String)session.getAttribute(WebSecurityConfig2.SESSION_KEY);
        List<AppInfoModel> list = appInfoRepository.findByEmergenceCallNo(emergencePhoneNo);
        StringBuilder result = new StringBuilder();
        result.append(emergencePhoneNo +"|");
        if(list!=null && list.size()>0)
            for(AppInfoModel appInfoModel : list){
                result.append(appInfoModel.getImgLocation()+"$"); //set img location
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
        return result.toString();
    }

    @RequestMapping(value = "/saveAppInfo", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String updateAppInfo(@RequestParam(value = "appInfo", required = true) String appInfo) throws Exception {
//        appInfo = new String(appInfo.getBytes("ISO-8859-1"),"UTF-8");
        String targetPhoneNo = appInfo.split("\\|")[0];
        List<AppInfoCtrl> appInfoCtrlList = appInfoCtrlRepository.findByEmergenceCallNo(targetPhoneNo);
        if(appInfoCtrlList!=null && appInfoCtrlList.size()>0){
            AppInfoCtrl appInfoCtrl = appInfoCtrlList.get(0);
            appInfoCtrl.setRefresh(1); // no need to update to client
            appInfoCtrlRepository.save(appInfoCtrl);
        }
        return appInfoService.saveOrUpdateAppInfo(appInfo);
    }

}
