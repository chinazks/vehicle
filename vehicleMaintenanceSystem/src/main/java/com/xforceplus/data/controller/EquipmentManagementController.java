package com.xforceplus.data.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.xforceplus.data.bean.EquipmentManagement;
import com.xforceplus.data.bean.ReleaseRecord;
import com.xforceplus.data.dao.EquipmentManagementRepository;
import com.xforceplus.data.dao.ReleaseRecordRepository;
import com.xforceplus.data.tools.JSONResult;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


@Controller
@RequestMapping("/equipmentManagement")
public class EquipmentManagementController {
	
	@Autowired
	private EquipmentManagementRepository equipmentManagementRepository;
	
	@Autowired
	private ReleaseRecordRepository releaseRecordRepository;
	
	@RequestMapping("/list")
	@ResponseBody
	public String equipmentManagementlist(Map<String,Object> map, HttpServletRequest request) {
		int pages = Integer.parseInt(request.getParameter("page"));
    	int limit = Integer.parseInt(request.getParameter("limit"));
        Page<EquipmentManagement> equipmentManagement=equipmentManagementRepository.findAll(new PageRequest(pages-1,limit));
        List<Map<String,String>> listMap=new ArrayList<>();//传到前端
       for(int i=0;i<equipmentManagement.getContent().size();i++){
            Map<String,String> map1=new HashMap<>();
            map1.put("id",String.valueOf(equipmentManagement.getContent().get(i).getId()));
            map1.put("storeRoom",String.valueOf(equipmentManagement.getContent().get(i).getStoreRoom()));
            map1.put("licensePlateNumber",String.valueOf(equipmentManagement.getContent().get(i).getLicensePlateNumber()));
            map1.put("vehicleType",equipmentManagement.getContent().get(i).getVehicleType());
            map1.put("accessoriesId",equipmentManagement.getContent().get(i).getAccessoriesId());
            map1.put("accessoriesName",equipmentManagement.getContent().get(i).getAccessoriesName());
            map1.put("specifications",equipmentManagement.getContent().get(i).getSpecifications());
            map1.put("originalFactoryNumber",equipmentManagement.getContent().get(i).getOriginalFactoryNumber());
            map1.put("unit",equipmentManagement.getContent().get(i).getUnit());
            map1.put("warehouseUnitPrice",String.valueOf(equipmentManagement.getContent().get(i).getWarehouseUnitPrice()));
            map1.put("stock",equipmentManagement.getContent().get(i).getStock()+"");
            map1.put("goodsNum",equipmentManagement.getContent().get(i).getGoodsNum());
            map1.put("deliveryDate",equipmentManagement.getContent().get(i).getDeliveryDate());
            listMap.add(map1);
        }
        JSONObject object = new JSONObject();
        object.put("code", 0);
        object.put("msg", "");
        object.put("count",equipmentManagementRepository.findAll().size());
        object.put("data", listMap);
        return  object.toString();
	}
	
	//新增器材
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult equmentManagementInsert(Map<String,Object> map,
    		@RequestParam(value = "storeRoom", required = false) String storeRoom,
    		@RequestParam(value = "materialIssuingUnit", required = false) String materialIssuingUnit,
    		@RequestParam(value = "licensePlateNumber", required = false) String licensePlateNumber,
    		@RequestParam(value = "vehicleType", required = false) String vehicleType,
    		@RequestParam(value = "accessoriesId", required = false) String accessoriesId,
    		@RequestParam(value = "accessoriesName", required = false) String accessoriesName,
    		@RequestParam(value = "specifications", required = false) String specifications,
    		@RequestParam(value = "originalFactoryNumber", required = false) String originalFactoryNumber,
    		@RequestParam(value = "unit", required = false) String unit,
    		@RequestParam(value = "warehouseUnitPrice", required = false) String warehouseUnitPrice,
    		@RequestParam(value = "stock", required = false) int stock,
    		@RequestParam(value = "goodsNum", required = false) String goodsNum,
    		@RequestParam(value = "deliveryDate", required = false) String deliveryDate){
        if(equipmentManagementRepository.findAllByAccessoriesIdAndStoreRoom(accessoriesId, storeRoom)==null) {
    		EquipmentManagement equipmentManagement = new EquipmentManagement(storeRoom, materialIssuingUnit, licensePlateNumber, vehicleType, accessoriesId, accessoriesName, specifications, originalFactoryNumber, warehouseUnitPrice, warehouseUnitPrice, stock, goodsNum, deliveryDate);
    		equipmentManagementRepository.save(equipmentManagement);
    	}else {
    		EquipmentManagement equipmentManagement = new EquipmentManagement(storeRoom, materialIssuingUnit, licensePlateNumber, vehicleType, accessoriesId, accessoriesName, specifications, originalFactoryNumber, warehouseUnitPrice, warehouseUnitPrice, stock+1, goodsNum, deliveryDate);
    		equipmentManagement.setId(equipmentManagementRepository.findAllByAccessoriesIdAndStoreRoom(accessoriesId, storeRoom).getId());
    		equipmentManagementRepository.save(equipmentManagement);
    	}
        return JSONResult.build(200, "新增成功", "");
    }
    
    //导入
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
				 EquipmentManagement equipmentManagement = new EquipmentManagement();
				 equipmentManagement.setStoreRoom(sheet.getCell(0,i).getContents());
				 equipmentManagement.setMaterialIssuingUnit((sheet.getCell(1,i).getContents()));
				 equipmentManagement.setLicensePlateNumber(sheet.getCell(2,i).getContents());
				 equipmentManagement.setVehicleType(sheet.getCell(3,i).getContents());
				 equipmentManagement.setAccessoriesId(sheet.getCell(4, i).getContents());
				 equipmentManagement.setAccessoriesName(sheet.getCell(5,i).getContents());
				 equipmentManagement.setSpecifications(sheet.getCell(6,i).getContents());
				 equipmentManagement.setOriginalFactoryNumber(sheet.getCell(7,i).getContents());
				 equipmentManagement.setUnit(sheet.getCell(8,i).getContents());
				 equipmentManagement.setWarehouseUnitPrice(sheet.getCell(9,i).getContents());
				 equipmentManagement.setStock(Integer.parseInt(sheet.getCell(10,i).getContents()));
				 equipmentManagement.setGoodsNum(sheet.getCell(11,i).getContents());
				 equipmentManagement.setDeliveryDate(sheet.getCell(12,i).getContents());
				 equipmentManagementRepository.save(equipmentManagement);
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

    //编辑器材信息
    @RequestMapping("/updateinfo")
    @ResponseBody
    public String update(@RequestParam long id) {
    	EquipmentManagement equipmentManagement = equipmentManagementRepository.findOne(id);
    	JSONObject jsonObject = (JSONObject) JSONObject.toJSON(equipmentManagement);
		return jsonObject.toString();
    }
    //编辑提交
    @RequestMapping(value = "update", method= RequestMethod.POST)
    @ResponseBody
    public JSONResult update(Map<String, Object> map,
    		@RequestParam(value = "id", required = false) long id,
    		@RequestParam(value = "storeRoom", required = false) String storeRoom,
    		@RequestParam(value = "materialIssuingUnit", required = false) String materialIssuingUnit,
    		@RequestParam(value = "licensePlateNumber", required = false) String licensePlateNumber,
    		@RequestParam(value = "vehicleType", required = false) String vehicleType,
    		@RequestParam(value = "accessoriesId", required = false) String accessoriesId,
    		@RequestParam(value = "accessoriesName", required = false) String accessoriesName,
    		@RequestParam(value = "specifications", required = false) String specifications,
    		@RequestParam(value = "originalFactoryNumber", required = false) String originalFactoryNumber,
    		@RequestParam(value = "unit", required = false) String unit,
    		@RequestParam(value = "warehouseUnitPrice", required = false) String warehouseUnitPrice,
    		@RequestParam(value = "stock", required = false) int stock,
    		@RequestParam(value = "goodsNum", required = false) String goodsNum,
    		@RequestParam(value = "deliveryDate", required = false) String deliveryDate) {
    	EquipmentManagement oldequipment = equipmentManagementRepository.findOne(id);
    	if(oldequipment.getStock()-stock>0) {
    		ReleaseRecord releaseRecord = new ReleaseRecord(storeRoom, materialIssuingUnit, "", accessoriesId, specifications, unit, originalFactoryNumber, oldequipment.getStock()-stock, warehouseUnitPrice, licensePlateNumber, deliveryDate, (oldequipment.getStock()-stock)*Double.parseDouble(warehouseUnitPrice)+"", "");
    		releaseRecordRepository.save(releaseRecord);
    	}
    	EquipmentManagement equipmentManagement = new EquipmentManagement(storeRoom, materialIssuingUnit, licensePlateNumber, vehicleType, accessoriesId, accessoriesName, specifications, originalFactoryNumber, unit, warehouseUnitPrice, stock, goodsNum, deliveryDate);
		equipmentManagement.setId(id);	
		equipmentManagementRepository.save(equipmentManagement);
    	return JSONResult.build(200, "编辑成功", "");
    	
    }
    
    //删除器材
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult delete(@RequestParam long id ) {
    	EquipmentManagement oldequipment = equipmentManagementRepository.findOne(id);
    	releaseRecordRepository.save(new ReleaseRecord(oldequipment.getStoreRoom(),oldequipment.getMaterialIssuingUnit(), "", oldequipment.getAccessoriesId(), oldequipment.getSpecifications(), oldequipment.getUnit(), oldequipment.getOriginalFactoryNumber(), oldequipment.getStock(), oldequipment.getWarehouseUnitPrice(), oldequipment.getLicensePlateNumber(), oldequipment.getDeliveryDate(), oldequipment.getStock()*Double.parseDouble(oldequipment.getWarehouseUnitPrice())+"", oldequipment.getDeliveryDate()));
    	equipmentManagementRepository.delete(id);
		return JSONResult.build(200, "删除成功", "");	
    }
    
    //导出excel
  	@RequestMapping("/putout")
  	@ResponseBody
  	public JSONResult putout(HttpServletRequest request){
  		String path = Class.class.getClass().getResource("/").getPath()+"/static/download/";
		ArrayList<EquipmentManagement> list=new ArrayList<EquipmentManagement>();
		list = (ArrayList<EquipmentManagement>) equipmentManagementRepository.findAll();
  		try {
  			File files=new File(path);
  			if (!files.exists()) {
  				files.mkdirs();
  			}
  			File file=new File(path+"/equipmentManagement.xls");
			if(file.exists()){
				file.delete();
			}
  			WritableWorkbook book=Workbook.createWorkbook(new File(path+"/equipmentManagement.xls"));
  			//设置表名
  			WritableSheet sheet=book.createSheet("器材管理表",0);
  			//设置表第一行
  			sheet.addCell(new Label(0,0,"库房号"));
  			sheet.addCell(new Label(1,0,"收料单位"));
  			sheet.addCell(new Label(2,0,"车牌号"));
  			sheet.addCell(new Label(3,0,"车辆类型"));
  			sheet.addCell(new Label(4,0,"配件id"));
  			sheet.addCell(new Label(5,0,"配件名称"));	
  			sheet.addCell(new Label(6,0,"规格"));	
  			sheet.addCell(new Label(7,0,"原厂编号"));	
  			sheet.addCell(new Label(8,0,"单位"));	
  			sheet.addCell(new Label(9,0,"入库单价"));	
  			sheet.addCell(new Label(10,0,"库房总库存"));	
  			sheet.addCell(new Label(11,0,"货位号"));	
  			sheet.addCell(new Label(12,0,"到货日期"));	
  			//添加数据
  			for(int i=0;i<list.size();i++){
  				sheet.addCell(new Label(0,i+1,list.get(i).getStoreRoom()));
  				sheet.addCell(new Label(1,i+1,list.get(i).getMaterialIssuingUnit()));
  				sheet.addCell(new Label(2,i+1,list.get(i).getLicensePlateNumber()));
  				sheet.addCell(new Label(3,i+1,list.get(i).getVehicleType()));
  				sheet.addCell(new Label(4,i+1,list.get(i).getAccessoriesId()));
  				sheet.addCell(new Label(5,i+1,list.get(i).getAccessoriesName()));
  				sheet.addCell(new Label(6,i+1,list.get(i).getSpecifications()));
  				sheet.addCell(new Label(7,i+1,list.get(i).getOriginalFactoryNumber()));
  				sheet.addCell(new Label(8,i+1,list.get(i).getUnit()));
  				sheet.addCell(new Label(0,i+1,list.get(i).getWarehouseUnitPrice()));
  				sheet.addCell(new jxl.write.Number(0,i+1,list.get(i).getStock()));
  				sheet.addCell(new Label(0,i+1,list.get(i).getGoodsNum()));
  				sheet.addCell(new Label(0,i+1,list.get(i).getDeliveryDate()));
  			}
  			book.write();//将所做的操作写入
  			book.close();//关闭文件
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		return JSONResult.build(200, "生成excel", "download/equipmentManagement.xls");
  	}
}

