package com.xforceplus.data.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.xforceplus.data.bean.VehicleManagement;

@Repository("VehicleManagementRepository")
public interface VehicleManagementService {
	Page<VehicleManagement> VehicleManagementormation(Integer page,Integer size);
    Page<VehicleManagement> findVehicleManagementormationByLicensePlateNumberCriteria(Integer page,Integer size,VehicleManagement vehicleManagement);
}
