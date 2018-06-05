package com.xforceplus.data.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xforceplus.data.bean.UnitInformation;
import com.xforceplus.data.dao.UnitInformationRepository;
import com.xforceplus.data.dao.UnitRepository;
import com.xforceplus.data.service.UnitInformationService;
import com.xforceplus.data.service.Impl.UnitInformationServerImpl;
import com.xforceplus.data.tools.JSONResult;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/6 0006.
 */
@Controller
@RequestMapping("/unitInformation")
public class UnitInformationController {
    private Logger logger= LoggerFactory.getLogger(UnitInformationController.class);
    @Autowired
    private UnitInformationRepository unitInformationRepository;
    
    @Autowired
    private UnitInformationService unitInformationService;
    
    @Autowired
    private UnitInformationServerImpl unitInformationServerImpl;
    
    @Autowired
    private UnitRepository unitRepository;

    
    //获取所有unitinformation数据
    @RequestMapping("/list/unitinformations")
    @ResponseBody
    public String equmentManagementList(Map<String,Object> map, HttpServletRequest request){
    	int pages = Integer.parseInt(request.getParameter("page"));
    	int limit = Integer.parseInt(request.getParameter("limit"));
    	String id = request.getParameter("id");
    	System.out.println(pages+"page"+limit+"limit"+id+"id");
    	Page<UnitInformation> unitInformationPage = null;
    	if(id==null) {
    		unitInformationPage=unitInformationRepository.findAll(new PageRequest(pages-1,limit));
    	}else {
    		UnitInformation unitInformation = new UnitInformation();
    		unitInformation.setUnitId(id);
    		unitInformationPage = unitInformationService.findUnitInformationByUnitIdCriteria(pages-1, limit,unitInformation);
    	}
       List<Map<String,String>> listMap=new ArrayList<>();//传到前端
       for(int i=0;i<unitInformationPage.getContent().size();i++){
            Map<String,String> map1=new HashMap<>();
            map1.put("id",String.valueOf(unitInformationPage.getContent().get(i).getId()));
            map1.put("unitId",String.valueOf(unitInformationPage.getContent().get(i).getUnitId()));
            map1.put("unitName",unitRepository.findByUnitId(unitInformationPage.getContent().get(i).getUnitId()).getUnitName()+String.valueOf(unitInformationPage.getContent().get(i).getUnitId()));
            map1.put("equipmentModel",unitInformationPage.getContent().get(i).getEquipmentModel());
            map1.put("equipmentName",unitInformationPage.getContent().get(i).getEquipmentName());
            map1.put("dispensingTime",unitInformationPage.getContent().get(i).getDispensingTime());
            map1.put("stockQuantity",String.valueOf(unitInformationPage.getContent().get(i).getStockQuantity()));
            map1.put("technicalStatus",unitInformationPage.getContent().get(i).getTechnicalStatus());
            listMap.add(map1);
        }
        map.put("page1",listMap);
        map.put("jsonAll", JSON.toJSONString(listMap));
        map.put("unitInformationPage",unitInformationPage);
        JSONObject object = new JSONObject();
        object.put("code", 0);
        object.put("msg", "");
        object.put("count",unitInformationRepository.findAll().size());
        object.put("data", listMap);
        System.out.println(object.toString());
        return  object.toString();
    }
    
    //导出excel
  	@RequestMapping("/putout")
  	@ResponseBody
  	public JSONResult putout(HttpServletRequest request){
  		String path = Class.class.getClass().getResource("/").getPath()+"/static/download/";
		ArrayList<UnitInformation> list=new ArrayList<UnitInformation>();
		list = (ArrayList<UnitInformation>) unitInformationRepository.findAll();
  		try {
  			File files=new File(path);
  			if (!files.exists()) {
  				files.mkdirs();
  			}
  			File file=new File(path+"/unitinformation.xls");
			if(file.exists()){
				file.delete();
			}
  			WritableWorkbook book=Workbook.createWorkbook(new File(path+"/unitinformation.xls"));
  			//设置表名
  			WritableSheet sheet=book.createSheet("单位信息表",0);
  			//设置表第一行
  			sheet.addCell(new Label(0,0,"单位id"));
  			sheet.addCell(new Label(1,0,"装备型号"));
  			sheet.addCell(new Label(2,0,"装备名称"));
  			sheet.addCell(new Label(3,0,"配发时间"));
  			sheet.addCell(new Label(4,0,"数量"));
  			sheet.addCell(new Label(5,0,"技术状况"));			
  			//添加数据
  			for(int i=0;i<list.size();i++){
  				sheet.addCell(new Label(0,i+1,list.get(i).getUnitId()));
  				sheet.addCell(new Label(1,i+1,list.get(i).getEquipmentModel()));
  				sheet.addCell(new Label(2,i+1,list.get(i).getEquipmentName()));
  				sheet.addCell(new Label(3,i+1,list.get(i).getDispensingTime()));
  				sheet.addCell(new jxl.write.Number(4,i+1,list.get(i).getStockQuantity()));
  				sheet.addCell(new Label(5,i+1,list.get(i).getTechnicalStatus()));
  			}
  			book.write();//将所做的操作写入
  			book.close();//关闭文件
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		System.out.println();
  		return JSONResult.build(200, "生成excel", "download/unitinformation.xls");
  	}

    //提交新增单位
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult equmentManagementInsert(Map<String,Object> map,HttpServletRequest request){
        String unitId = request.getParameter("unitid");
        String equipmentModel = request.getParameter("equipmentModel");
        String equipmentName = request.getParameter("equipmentName");
        String dispensingTime = request.getParameter("dispensingTime");
        int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
        String technicalStatus = request.getParameter("technicalStatus");
        UnitInformation unitInformation = new UnitInformation(unitId, equipmentModel, equipmentName, dispensingTime, stockQuantity, technicalStatus);
        unitInformationRepository.save(unitInformation);
        return JSONResult.build(200, "新增成功", "");
    }
    
    
    //删除单位信息
    @RequestMapping(value = "deleteunitinformation")
    @ResponseBody
    public JSONResult deleteunitinformation(Map<String,Object> map,HttpServletRequest request){
        long id = Integer.parseInt(request.getParameter("id"));//--
        JSONResult response=null;
        String msg = "";
        try {
            unitInformationRepository.delete(id);
            msg = "单位信息数据删除成功！";
        }catch (Exception e){
            logger.error(e.getLocalizedMessage());
            msg = "单位信息数据删除失败！";
        }finally {
            response=JSONResult.build(1, msg, "");
        }
        return response;
    }
    
    //跳转到编辑页面，传编辑的信息
    @RequestMapping(value = "/updateinfo" ,  method = RequestMethod.POST)
    @ResponseBody
    public String updateinfo(HttpServletRequest request) {
    	long id = Integer.parseInt(request.getParameter("id"));
    	UnitInformation unitInformation = unitInformationRepository.findOne(id);
    	JSONObject jsonObject = (JSONObject) JSONObject.toJSON(unitInformation);
		return jsonObject.toString();
    }
    
    //编辑页面的提交
    @RequestMapping(value = "/update" ,  method = RequestMethod.POST)
    @ResponseBody
    public JSONResult updateunitinformation(HttpServletRequest request) {
    	long id = Integer.parseInt(request.getParameter("id"));
    	String unitId = request.getParameter("unitid");
        String equipmentModel = request.getParameter("equipmentModel");
        String equipmentName = request.getParameter("equipmentName");
        String dispensingTime = request.getParameter("dispensingTime");
        int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
        String technicalStatus = request.getParameter("technicalStatus");
        UnitInformation unitInformation = new UnitInformation(unitId, equipmentModel, equipmentName, dispensingTime, stockQuantity, technicalStatus);
        unitInformation.setId(id);
        unitInformationRepository.save(unitInformation);
		return JSONResult.build(200, "修改成功", "");
    }
    
    //导入excel
    @RequestMapping(value = "/lead", method = RequestMethod.POST)
    @ResponseBody
    public String lead(MultipartFile file) {
    	String path = Class.class.getClass().getResource("/").getPath()+"static/upload/";
    	String fielName = file.getOriginalFilename();
    	File files=new File(path);
		if (!files.exists()) {
			files.mkdirs();
		}
		File tempfile = new File(path,fielName);
		if(tempfile.exists()) {
			tempfile.delete();
		}
		try {
			file.transferTo(tempfile);
			Workbook workbook = Workbook.getWorkbook(tempfile);
			Sheet sheet = workbook.getSheet(0);
			 for (int i = 1; i < sheet.getRows(); i++) {
				 UnitInformation unitInformation = new UnitInformation();
				 unitInformation.setUnitId(sheet.getCell(0,i).getContents());
				 unitInformation.setEquipmentModel((sheet.getCell(1,i).getContents()));
				 unitInformation.setEquipmentName(sheet.getCell(2,i).getContents());
				 unitInformation.setDispensingTime(sheet.getCell(3,i).getContents());
				 unitInformation.setStockQuantity(Integer.parseInt(sheet.getCell(4, i).getContents()));
				 unitInformation.setTechnicalStatus(sheet.getCell(5,i).getContents());
				 unitInformationRepository.save(unitInformation);
			 }
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}   
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("src", "baidu.com");
		json.put("code", 0);
		json.put("msg", "上传成功");
		json.put("data", data);
		return json.toString();
    }
}
