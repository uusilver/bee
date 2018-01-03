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
 * TODO The class HelpLocationInfo is supposed to be documented...
 *
 * @author Vani Li
 */
@Entity
public class HelpLocationInfo {

    //自增ID
    @Id
    @GeneratedValue
    private Integer id;

    private String phoneNo;
    private String imei;
    private String lng;
    private String lat;
    private String emergenceCallNo;
    private String callDate;

    public HelpLocationInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getEmergenceCallNo() {
        return emergenceCallNo;
    }

    public void setEmergenceCallNo(String emergenceCallNo) {
        this.emergenceCallNo = emergenceCallNo;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
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
