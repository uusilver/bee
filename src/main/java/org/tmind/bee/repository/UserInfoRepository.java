package org.tmind.bee.repository;
/**
 * @COPYRIGHT (C) 2017 Schenker AG
 * <p>
 * All rights reserved
 */


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tmind.bee.entity.UserInfo;

import java.util.List;

/**
 * TODO The class UserInfoRepository is supposed to be documented...
 *
 * @author Vani Li
 */
public interface  UserInfoRepository   extends JpaRepository<UserInfo,Integer> {
    @Query("select userInfo from UserInfo userInfo where userInfo.emergencePhoneNo = ?1")
    List<UserInfo> findByEmergencePhoneNo(String emergencePhoneNo);

    @Query("select userInfo from UserInfo userInfo where userInfo.emergencePhoneNo = ?1 and userInfo.parentCtrlPwd=?2")
    List<UserInfo> findByEmergencePhoneNoAndPwd(String emergencePhoneNo, String pwd);
}
