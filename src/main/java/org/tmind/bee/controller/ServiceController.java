package org.tmind.bee.controller;
/**
 * @COPYRIGHT (C) 2018 Schenker AG
 * <p>
 * All rights reserved
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tmind.bee.config.WebSecurityConfig;
import org.tmind.bee.config.WebSecurityConfig2;
import org.tmind.bee.entity.CrashInfoModel;
import org.tmind.bee.entity.HelpLocationInfo;
import org.tmind.bee.repository.CrashInfoRepository;
import org.tmind.bee.repository.HelpInfoRepository;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * TODO The class ServiceController is supposed to be documented...
 *
 * @author Vani Li
 */
@Controller
public class ServiceController {

    @Autowired
    private HelpInfoRepository helpInfoRepository;

    @Autowired
    private CrashInfoRepository crashInfoRepository;


    @RequestMapping("/getHelpInfoByEmergencePhoneNo")
    public String getHelpInfoByEmergencePhoneNo(HashMap<String,Object> map, HttpSession session){
        String emergencePhoneNo = (String)session.getAttribute(WebSecurityConfig2.SESSION_KEY);
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
}
