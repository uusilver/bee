package org.tmind.bee.entity;
/**
 * @COPYRIGHT (C) 2018 Schenker AG
 * <p>
 * All rights reserved
 */


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * TODO The class AppInfoModel is supposed to be documented...
 *
 * @author Vani Li
 */
@Entity
public class AppInfoModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String targetPhoneNo;
    private String applicationName;
    private String pkg;
    private String allowFlag;
    private String startTimeHour;
    private String startTimeMinute;
    private String endTimeHour;
    private String endTimeMinute;
    private String systemFlag;
    private String imgLocation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTargetPhoneNo() {
        return targetPhoneNo;
    }

    public void setTargetPhoneNo(String targetPhoneNo) {
        this.targetPhoneNo = targetPhoneNo;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getAllowFlag() {
        return allowFlag;
    }

    public void setAllowFlag(String allowFlag) {
        this.allowFlag = allowFlag;
    }

    public String getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(String startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public String getStartTimeMinute() {
        return startTimeMinute;
    }

    public void setStartTimeMinute(String startTimeMinute) {
        this.startTimeMinute = startTimeMinute;
    }

    public String getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(String endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public String getEndTimeMinute() {
        return endTimeMinute;
    }

    public void setEndTimeMinute(String endTimeMinute) {
        this.endTimeMinute = endTimeMinute;
    }

    public String getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag;
    }

    public String getImgLocation() {
        return imgLocation;
    }

    public void setImgLocation(String imgLocation) {
        this.imgLocation = imgLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppInfoModel that = (AppInfoModel) o;

        if (targetPhoneNo != null ? !targetPhoneNo.equals(that.targetPhoneNo) : that.targetPhoneNo != null) return false;
        return pkg != null ? pkg.equals(that.pkg) : that.pkg == null;
    }

    @Override
    public int hashCode() {
        int result = targetPhoneNo != null ? targetPhoneNo.hashCode() : 0;
        result = 31 * result + (pkg != null ? pkg.hashCode() : 0);
        return result;
    }
}
