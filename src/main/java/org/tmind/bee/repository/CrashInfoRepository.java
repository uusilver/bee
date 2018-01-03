package org.tmind.bee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tmind.bee.entity.CrashInfoModel;

import java.util.List;

/**
 * @COPYRIGHT (C) 2018 Schenker AG
 * <p>
 * All rights reserved
 */

public interface CrashInfoRepository extends JpaRepository<CrashInfoModel,Integer> {
}
