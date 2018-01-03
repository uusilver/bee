package org.tmind.bee.entity;
/**
 * @COPYRIGHT (C) 2018 Schenker AG
 * <p>
 * All rights reserved
 */


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * TODO The class CrashInfoModel is supposed to be documented...
 *
 * @author Vani Li
 */
@Entity
public class CrashInfoModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String crashDate;
    private String checkFlag;
    @Column(length = 21000)
    private String crashinfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCrashDate() {
        return crashDate;
    }

    public void setCrashDate(String crashDate) {
        this.crashDate = crashDate;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getCrashinfo() {
        return crashinfo;
    }

    public void setCrashinfo(String crashinfo) {
        this.crashinfo = crashinfo;
    }
}
