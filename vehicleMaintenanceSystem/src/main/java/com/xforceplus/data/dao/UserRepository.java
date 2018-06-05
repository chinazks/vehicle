package com.xforceplus.data.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.xforceplus.data.bean.User;

public interface UserRepository  extends JpaRepository<User,Long>{
	User findByUserName(String userName);
	    
	Page<User> findAll(Pageable pageable);	 
}
