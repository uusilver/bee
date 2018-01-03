package org.tmind.bee.repository;
/**
 * @COPYRIGHT (C) 2017 Schenker AG
 * <p>
 * All rights reserved
 */


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tmind.bee.entity.HelpLocationInfo;

import java.util.List;

/**
 * TODO The class HelpInfoRepository is supposed to be documented...
 *
 * @author Vani Li
 */
public interface HelpInfoRepository extends JpaRepository<HelpLocationInfo,Integer> {
    @Query("select helpInfo from HelpLocationInfo helpInfo where helpInfo.emergenceCallNo = ?1 order by helpInfo.id desc")
    List<HelpLocationInfo> findByEmergenceCallNo(String emergenceCallNo);
}
