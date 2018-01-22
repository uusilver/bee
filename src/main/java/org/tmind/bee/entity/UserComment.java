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
 * TODO The class UserComment is supposed to be documented...
 *
 * @author Vani Li
 */
@Entity
public class UserComment {
    //自增ID
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String telno;
    @Column(length = 5000)
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
