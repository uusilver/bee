package org.tmind.bee.rest;
/**
 * @COPYRIGHT (C) 2017 Schenker AG
 * <p>
 * All rights reserved
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private HelpInfoRepository helpInfoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private CrashInfoRepository crashInfoRepository;


    @RequestMapping("/doLogin/{emergencePhoneNo}/{pwd}")
    String doLogin(@PathVariable("emergencePhoneNo") String emergencePhoneNo, @PathVariable("pwd") String pwd, HashMap<String,Object> map, HttpSession session) {
        List<UserInfo> userInfos = userInfoRepository.findByEmergencePhoneNoAndPwd(emergencePhoneNo, pwd);
        session.setAttribute(WebSecurityConfig.SESSION_KEY, emergencePhoneNo);
        if(userInfos.size()>0){
            return "redirect:/getHelpInfoByEmergencePhoneNo/"+emergencePhoneNo;
        }
        map.put("msg","用户名或者密码错误");
        return "error";
    }

    @RequestMapping("/test")
    public  String test(HashMap<String,Object> map) {
        map.put("hello","hello");
        return"/test";
    }

    @GetMapping("/login")
    public  String login() {
        return"login";
    }

    @RequestMapping("/getHelpInfoByEmergencePhoneNo/{emergencePhoneNo}")
    public String getHelpInfoByEmergencePhoneNo(@PathVariable("emergencePhoneNo") String emergencePhoneNo, HashMap<String,Object> map){
        List<HelpLocationInfo> list = helpInfoRepository.findByEmergenceCallNo(emergencePhoneNo);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(HelpLocationInfo info:list){
            if(info.getCallDate()!=null) {
                Date d = new Date(Long.valueOf(info.getCallDate()));
                info.setCallDate(formatter.format(d));
            }
        }
        map.put("helpInfos",list);
        return "LoadHelpLocationInfo";
    }

    @RequestMapping("/loadAllCrashInfo")
    public String loadAllCrashInfo(HashMap<String,Object> map){
        List<CrashInfoModel> list = crashInfoRepository.findAll();
        map.put("crashInfo",list);
        return "LoadCrashInfo";
    }
    // static class
    // static constants
    // static method
    // instance data
    // constructor
    // public
    // private
    // protected
}
