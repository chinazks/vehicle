package com.xforceplus.data.controller;

import com.alibaba.fastjson.JSONObject;
import com.xforceplus.data.bean.UnitInformation;
import com.xforceplus.data.bean.VehicleManagement;
import com.xforceplus.data.dao.VehicleManagementRepository;
import com.xforceplus.data.service.VehicleManagementService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vehiclemanagement")
public class VehicleManagementController {
    private Logger logger= LoggerFactory.getLogger(UnitInformationController.class);
	
    @Autowired
    private VehicleManagementRepository vehicleManagementRepository;
    
    @Autowired
    private VehicleManagementService vehicleManagementService;
    
  //显示车辆列表
    @RequestMapping("/list/vehicles")
    @ResponseBody
    public String vehiclemanagement(Map<String,Object> map, HttpServletRequest request){
    	int pages = Integer.parseInt(request.getParameter("page"));
    	int limit = Integer.parseInt(request.getParameter("limit"));
    	String licensePlateNumber = request.getParameter("licensePlateNumber");
    	Page<VehicleManagement> vehicleManagementPage=null;
    	if(licensePlateNumber==null) {
    		vehicleManagementPage=vehicleManagementRepository.findAll(new PageRequest(pages-1,limit));
    	}else {
    		VehicleManagement vehicleManagement = new VehicleManagement();
    		vehicleManagement.setLicensePlateNumber(licensePlateNumber);
    		vehicleManagementPage = vehicleManagementService.findVehicleManagementormationByLicensePlateNumberCriteria(pages-1, limit,vehicleManagement);
    	}
        List<Map<String,String>> listMap=new ArrayList<>();//传到前端
       for(int i=0;i<vehicleManagementPage.getContent().size();i++){
            Map<String,String> map1=new HashMap<>();
            map1.put("id",String.valueOf(vehicleManagementPage.getContent().get(i).getId()));
            map1.put("unitId",String.valueOf(vehicleManagementPage.getContent().get(i).getUnitId()));
            map1.put("equipmentName",vehicleManagementPage.getContent().get(i).getEquipmentName());
            map1.put("equipmentModel",vehicleManagementPage.getContent().get(i).getEquipmentModel());
            map1.put("licensePlateNumber",vehicleManagementPage.getContent().get(i).getLicensePlateNumber());
            map1.put("vehicleType",vehicleManagementPage.getContent().get(i).getVehicleType());
            map1.put("driverName",vehicleManagementPage.getContent().get(i).getDriverName());
            map1.put("remarke",vehicleManagementPage.getContent().get(i).getRemarke());
            listMap.add(map1);
        }
        JSONObject object = new JSONObject();
        object.put("code", 0);
        object.put("msg", "");
        object.put("count",vehicleManagementRepository.findAll().size());
        object.put("data", listMap);
        return  object.toString();
    }
    
    //导出excel
  	@RequestMapping("/putout")
  	@ResponseBody
  	public JSONResult putout(HttpServletRequest request){
  		String path = Class.class.getClass().getResource("/").getPath()+"/static/download/";
		ArrayList<VehicleManagement> list=new ArrayList<VehicleManagement>();
		list = (ArrayList<VehicleManagement>) vehicleManagementRepository.findAll();
  		try {
  			File files=new File(path);
  			if (!files.exists()) {
  				files.mkdirs();
  			}
  			File file=new File(path+"/vehiclemanagement.xls");
			if(file.exists()){
				file.delete();
			}
  			WritableWorkbook book=Workbook.createWorkbook(new File(path+"/vehiclemanagement.xls"));
  			//设置表名
  			WritableSheet sheet=book.createSheet("车辆管理表",0);
  			//设置表第一行
  			sheet.addCell(new Label(0,0,"装备名称"));
  			sheet.addCell(new Label(1,0,"装备型号"));
  			sheet.addCell(new Label(2,0,"车牌号"));
  			sheet.addCell(new Label(3,0,"车辆类型"));
  			sheet.addCell(new Label(4,0,"司机名称"));
  			sheet.addCell(new Label(5,0,"单位id"));	
  			sheet.addCell(new Label(6,0,"备注"));	
  			//添加数据
  			for(int i=0;i<list.size();i++){
  				sheet.addCell(new Label(0,i+1,list.get(i).getEquipmentName()));
  				sheet.addCell(new Label(1,i+1,list.get(i).getEquipmentModel()));
  				sheet.addCell(new Label(2,i+1,list.get(i).getLicensePlateNumber()));
  				sheet.addCell(new Label(3,i+1,list.get(i).getVehicleType()));
  				sheet.addCell(new Label(4,i+1,list.get(i).getDriverName()));
  				sheet.addCell(new Label(5,i+1,list.get(i).getUnitId()));
  				sheet.addCell(new Label(6,i+1,list.get(i).getRemarke()));
  			}
  			book.write();//将所做的操作写入
  			book.close();//关闭文件
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		return JSONResult.build(200, "生成excel", "download/vehiclemanagement.xls");
  	}

    //新增车辆信息
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult equmentManagementInsert(Map<String,Object> map,HttpServletRequest request){
        String unitId = request.getParameter("unitid");
        String equipmentModel = request.getParameter("equipmentModel");
        String equipmentName = request.getParameter("equipmentName");
        String licensePlateNumber = request.getParameter("licensePlateNumber");
        String vehicleType = request.getParameter("vehicleType");
        String driverName = request.getParameter("driverName");
        String remarke = request.getParameter("remarke");
        VehicleManagement vehicleManagement = new VehicleManagement(equipmentName, equipmentModel, licensePlateNumber, vehicleType, driverName, unitId, remarke);
        vehicleManagementRepository.save(vehicleManagement);
        return JSONResult.build(200, "新增成功", "");
    }
    
    
    //删除单位信息
    @RequestMapping(value = "/deletevehicle")
    @ResponseBody
    public JSONResult deletevehiclemagement(Map<String,Object> map,HttpServletRequest request){
        long id = Integer.parseInt(request.getParameter("id"));//--
        JSONResult response=null;
        String msg = "";
        try {
        	vehicleManagementRepository.delete(id);
            msg = "车辆管理数据删除成功！";
        }catch (Exception e){
            logger.error(e.getLocalizedMessage());
            msg = "车辆管理数据删除失败！";
        }finally {
            response=JSONResult.build(200, msg, "");
        }
        return response;
    }
    
    //跳转到编辑页面，传编辑的信息
    @RequestMapping(value = "/updateinfo" ,  method = RequestMethod.POST)
    @ResponseBody
    public String updateinfo(HttpServletRequest request) {
    	long id = Integer.parseInt(request.getParameter("id"));
    	VehicleManagement vehicleManagement = vehicleManagementRepository.findOne(id);
    	JSONObject jsonObject = (JSONObject) JSONObject.toJSON(vehicleManagement);
		return jsonObject.toString();
    }
    
    //编辑页面的提交
    @RequestMapping(value = "/update" ,  method = RequestMethod.POST)
    @ResponseBody
    public JSONResult updatevehiclemanagement(HttpServletRequest request) {
    	 long id = Integer.parseInt(request.getParameter("id"));
    	 String unitId = request.getParameter("unitid");
         String equipmentModel = request.getParameter("equipmentModel");
         String equipmentName = request.getParameter("equipmentName");
         String licensePlateNumber = request.getParameter("licensePlateNumber");
         String vehicleType = request.getParameter("vehicleType");
         String driverName = request.getParameter("driverName");
         String remarke = request.getParameter("remarke");
         VehicleManagement vehicleManagement = new VehicleManagement(equipmentName, equipmentModel, licensePlateNumber, vehicleType, driverName, unitId, remarke);
         vehicleManagement.setId(id);
         vehicleManagementRepository.save(vehicleManagement);
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
				 VehicleManagement vehicleManagement = new VehicleManagement();
				 vehicleManagement.setEquipmentName(sheet.getCell(0,i).getContents());
				 vehicleManagement.setEquipmentModel((sheet.getCell(1,i).getContents()));
				 vehicleManagement.setLicensePlateNumber(sheet.getCell(2,i).getContents());
				 vehicleManagement.setVehicleType(sheet.getCell(3,i).getContents());
				 vehicleManagement.setDriverName(sheet.getCell(4, i).getContents());
				 vehicleManagement.setUnitId(sheet.getCell(5,i).getContents());
				 vehicleManagement.setRemarke(sheet.getCell(6,i).getContents());
				 vehicleManagementRepository.save(vehicleManagement);
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
