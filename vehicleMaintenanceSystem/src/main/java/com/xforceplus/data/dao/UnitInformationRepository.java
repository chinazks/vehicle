package com.xforceplus.data.dao;

import com.xforceplus.data.bean.UnitInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/5/5 0005.
 */
@Repository("UnitInformationRepository")
public interface UnitInformationRepository extends JpaRepository<UnitInformation,Long>, JpaSpecificationExecutor<UnitInformation>{
   // Page<UnitInformation> findAll(Pageable pageable);
   // Page<UnitInformation> findByUnitIdLike(String unitId,Pageable pageable);   
}
