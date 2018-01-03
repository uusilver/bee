package org.tmind.bee.entity;
/**
 * @COPYRIGHT (C) 2017 Schenker AG
 * <p>
 * All rights reserved
 */


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * TODO The class UserInfo is supposed to be documented...
 *
 * @author Vani Li
 */
@Entity
public class UserInfo {
    //自增ID
    @Id
    @GeneratedValue
    private Integer id;
    private String imei;
    private String emergencePhoneNo;
    private String parentCtrlPwd;

    //需要声明无参数的构造函数
    public UserInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getEmergencePhoneNo() {
        return emergencePhoneNo;
    }

    public void setEmergencePhoneNo(String emergencePhoneNo) {
        this.emergencePhoneNo = emergencePhoneNo;
    }

    public String getParentCtrlPwd() {
        return parentCtrlPwd;
    }

    public void setParentCtrlPwd(String parentCtrlPwd) {
        this.parentCtrlPwd = parentCtrlPwd;
    }
}
