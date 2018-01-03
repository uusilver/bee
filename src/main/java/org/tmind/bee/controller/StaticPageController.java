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
import org.tmind.bee.entity.CrashInfoModel;
import org.tmind.bee.entity.HelpLocationInfo;
import org.tmind.bee.entity.UserInfo;
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

    @GetMapping("/login")
    public  String login() {
        return"login";
    }

    @GetMapping("/home")
    public  String home() {
        return"home";
    }

    @GetMapping("/appControlTable")
    public String appControlTable(){
        return "app_control_table";
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

}
