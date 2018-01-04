package org.tmind.bee.repository;
/**
 * @COPYRIGHT (C) 2018 Schenker AG
 * <p>
 * All rights reserved
 */


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tmind.bee.entity.AppInfoModel;

import java.util.List;

/**
 * TODO The class AppInfoRepository is supposed to be documented...
 *
 * @author Vani Li
 */
public interface AppInfoRepository extends JpaRepository<AppInfoModel,Integer> {
    @Query("select appinfoModel from AppInfoModel appinfoModel where appinfoModel.targetPhoneNo = ?1 order by appinfoModel.id desc")
    List<AppInfoModel> findByEmergenceCallNo(String emergenceCallNo);
}
