package com.xforceplus.data.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.xforceplus.data.bean.Unit;
import com.xforceplus.data.bean.User;
import com.xforceplus.data.dao.UnitRepository;
import com.xforceplus.data.dao.UserRepository;

@Controller
@RequestMapping("/unit")
public class UnitController {
	
	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/units")
	@ResponseBody
	public String units(Map<String,Object> map, @RequestParam("page") int pages,@RequestParam("limit") int limit) {
    	Page<Unit> unitPage = unitRepository.findAll(new PageRequest(pages-1,limit));
    	 List<Map<String,String>> listMap=new ArrayList<>();//传到前端
         for(int i=0;i<unitPage.getContent().size();i++){
              Map<String,String> map1=new HashMap<>();
              map1.put("unitId",String.valueOf(unitPage.getContent().get(i).getUnitId()));
              map1.put("unitName",unitPage.getContent().get(i).getUnitName());
              listMap.add(map1);
          }
          JSONObject object = new JSONObject();
          object.put("code", 0);
          object.put("msg", "");
          object.put("count",unitRepository.findAll().size());
          object.put("data", listMap);
          return  object.toString();
	}
	
	@RequestMapping("/users")
	@ResponseBody
	public String users(Map<String,Object> map, @RequestParam("page") int pages,@RequestParam("limit") int limit) {
    	Page<User> unitPage = userRepository.findAll(new PageRequest(pages-1,limit));
    	 List<Map<String,String>> listMap=new ArrayList<>();//传到前端
         for(int i=0;i<unitPage.getContent().size();i++){
              Map<String,String> map1=new HashMap<>();
              map1.put("userName",String.valueOf(unitPage.getContent().get(i).getUserName()));
              map1.put("passwords","****");
              map1.put("password",String.valueOf(unitPage.getContent().get(i).getPassword()));
              listMap.add(map1);
          }
          JSONObject object = new JSONObject();
          object.put("code", 0);
          object.put("msg", "");
          object.put("count",userRepository.findAll().size());
          object.put("data", listMap);
          return  object.toString();
	}
}	
