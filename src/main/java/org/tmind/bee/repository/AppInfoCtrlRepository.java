package org.tmind.bee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tmind.bee.entity.AppInfoCtrl;

import java.util.List;

/**
 * @COPYRIGHT (C) 2018 Schenker AG
 * <p>
 * All rights reserved
 */

public interface AppInfoCtrlRepository extends JpaRepository<AppInfoCtrl,Integer> {
    @Query("select appInfoCtrl from AppInfoCtrl appInfoCtrl where appInfoCtrl.targetPhoneNo = ?1 order by appInfoCtrl.id desc")
    List<AppInfoCtrl> findByEmergenceCallNo(String emergenceCallNo);
}
