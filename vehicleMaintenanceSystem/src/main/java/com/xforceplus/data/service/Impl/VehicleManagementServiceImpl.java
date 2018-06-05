package com.xforceplus.data.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.xforceplus.data.bean.UnitInformation;
import com.xforceplus.data.bean.VehicleManagement;
import com.xforceplus.data.dao.VehicleManagementRepository;
import com.xforceplus.data.service.VehicleManagementService;

@Service(value="VehiclemanagementService")
public class VehicleManagementServiceImpl implements VehicleManagementService {
	
	@Resource
	private VehicleManagementRepository vehicleManagementRepository;

	@Override
	public Page<VehicleManagement> VehicleManagementormation(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        return vehicleManagementRepository.findAll(pageable);
	}

	@Override
	public Page<VehicleManagement> findVehicleManagementormationByLicensePlateNumberCriteria(Integer page, Integer size,
			VehicleManagement vehicleManagement) {
		//System.out.println("page的值为"+page+"size的值为"+size+"unitInformation的unitid的值为"+unitInformation.getUnitId());
				Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "unitId");
		        Page<VehicleManagement> vehicleManagementPage = vehicleManagementRepository.findAll(new Specification<VehicleManagement>(){
		            @Override
		            public Predicate toPredicate(Root<VehicleManagement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		                List<Predicate> list = new ArrayList<Predicate>();
		                if(null!=vehicleManagement.getLicensePlateNumber()&&!"".equals(vehicleManagement.getLicensePlateNumber())){
		                    list.add(criteriaBuilder.like(root.get("licensePlateNumber").as(String.class), "%"+vehicleManagement.getLicensePlateNumber()+"%"));
		                }
		                Predicate[] p = new Predicate[list.size()];
		                return criteriaBuilder.and(list.toArray(p));
		            }
		        },pageable);
		        System.out.println(vehicleManagementPage.getSize());
		        return vehicleManagementPage;
	}

}
