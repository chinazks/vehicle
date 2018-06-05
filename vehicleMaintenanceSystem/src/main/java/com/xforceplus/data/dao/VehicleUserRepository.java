package com.xforceplus.data.dao;

import com.xforceplus.data.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/5/21 0021.
 */
public interface VehicleUserRepository extends JpaRepository<User,Long> {
    User findByUserNameAndPassword(String userName,String password);
}
