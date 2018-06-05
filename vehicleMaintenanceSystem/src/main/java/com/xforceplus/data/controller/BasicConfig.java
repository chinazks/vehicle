package com.xforceplus.data.controller;

import com.xforceplus.data.dao.VehicleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/5/27 0027.
 */
@Controller
public class BasicConfig {
    @Autowired
    private VehicleUserRepository userRepository;

    @RequestMapping("")
    private String userAdd(){
        return null;
    }
}
