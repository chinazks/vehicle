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
import com.xforceplus.data.dao.UnitInformationRepository;
import com.xforceplus.data.service.UnitInformationService;

@Service(value="UnitInformationService")
public class UnitInformationServerImpl implements UnitInformationService {

	 @Resource
	 UnitInformationRepository unitInformationRepository;
	
	@Override
	public Page<UnitInformation> findUnitInformationCriteria(Integer page, Integer size) {
		 Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
	        return unitInformationRepository.findAll(pageable);
	}

	@Override
	public Page<UnitInformation> findUnitInformationByUnitIdCriteria(Integer page, Integer size, UnitInformation unitInformation) {
		//System.out.println("page的值为"+page+"size的值为"+size+"unitInformation的unitid的值为"+unitInformation.getUnitId());
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "unitId");
        Page<UnitInformation> unitInformationPage = unitInformationRepository.findAll(new Specification<UnitInformation>(){
            @Override
            public Predicate toPredicate(Root<UnitInformation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(null!=unitInformation.getUnitId()&&!"".equals(unitInformation.getUnitId())){
                    list.add(criteriaBuilder.equal(root.get("unitId").as(String.class), unitInformation.getUnitId()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        System.out.println(unitInformationPage.getSize());
        return unitInformationPage;
	}

}
