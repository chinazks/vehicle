package com.xforceplus.data.dao;

import com.xforceplus.data.bean.ReleaseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/5/27 0027.
 */
@Repository
public interface ReleaseRecordRepository  extends JpaRepository<ReleaseRecord,Long>{
    void deleteAllByUuid(String uuid);
    ReleaseRecord findAllByUuid(String uuid);
}
