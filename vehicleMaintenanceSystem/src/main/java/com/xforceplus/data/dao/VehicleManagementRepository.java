package com.xforceplus.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.xforceplus.data.bean.VehicleManagement;

/**
 * Created by Administrator on 2018/5/5 0005.
 */
@Repository
public interface VehicleManagementRepository extends JpaRepository<VehicleManagement,Long>,JpaSpecificationExecutor<VehicleManagement> {
	 //Page<VehicleManagement> findAll(Pageable pageable);	 
}
