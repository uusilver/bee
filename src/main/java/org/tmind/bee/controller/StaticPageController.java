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
import org.tmind.bee.entity.AppInfoModel;
import org.tmind.bee.entity.CrashInfoModel;
import org.tmind.bee.entity.HelpLocationInfo;
import org.tmind.bee.entity.UserInfo;
import org.tmind.bee.repository.AppInfoRepository;
import org.tmind.bee.repository.CrashInfoRepository;
import org.tmind.bee.repository.HelpInfoRepository;
import org.tmind.bee.repository.UserInfoRepository;

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

}
