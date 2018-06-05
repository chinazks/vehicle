package com.xforceplus.data.service;

import org.springframework.data.domain.Page;

import com.xforceplus.data.bean.UnitInformation;

public interface UnitInformationService {
	Page<UnitInformation> findUnitInformationCriteria(Integer page,Integer size);
    Page<UnitInformation> findUnitInformationByUnitIdCriteria(Integer page,Integer size,UnitInformation unitInformation);
}
