package com.xforceplus.data.controller;

import com.alibaba.fastjson.JSONObject;
import com.xforceplus.data.bean.MaintenanceRecord;
import com.xforceplus.data.bean.ReleaseRecord;
import com.xforceplus.data.bean.Unit;
import com.xforceplus.data.dao.MaintenanceRecordRepository;
import com.xforceplus.data.dao.ReleaseRecordRepository;
import com.xforceplus.data.dao.UnitRepository;
import com.xforceplus.data.tools.JSONResult;
import com.xforceplus.data.tools.StringUtil;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/5/26 0026.
 */
@Controller
@RequestMapping("/maintenanceRecord")
public class MaintenanceRecordController {

 @Autowired
 private MaintenanceRecordRepository maintenanceRecordRepository;
 @Autowired
 private UnitRepository unitRepository;
 @Autowired
 private ReleaseRecordRepository releaseRecordRepository;

 @RequestMapping("/list")
 @ResponseBody
 public String equmentManagementList(@RequestParam("page") int pages,@RequestParam("limit") int limit){
  Page<MaintenanceRecord> maintenanceRecordPage=maintenanceRecordRepository.findAll(new PageRequest(pages-1,limit));
  List<Map<String,String>> listMap=new ArrayList<>();//传到前端
  for(int i=0;i<maintenanceRecordPage.getContent().size();i++){
   Map<String,String> map1=new HashMap<>();
   map1.put("id",String.valueOf(maintenanceRecordPage.getContent().get(i).getId()));
   map1.put("unitId",String.valueOf(maintenanceRecordPage.getContent().get(i).getUnitId()));
   map1.put("unitName",unitRepository.findByUnitId(maintenanceRecordPage.getContent().get(i).getUnitId()).getUnitName()+" "+String.valueOf(maintenanceRecordPage.getContent().get(i).getUnitId()));
   map1.put("licensePlateNumber",maintenanceRecordPage.getContent().get(i).getLicensePlateNumber());
   map1.put("driverName",maintenanceRecordPage.getContent().get(i).getDriverName());
   map1.put("storeRoom",maintenanceRecordPage.getContent().get(i).getStoreRoom());
   map1.put("vehicleType",String.valueOf(maintenanceRecordPage.getContent().get(i).getVehicleType()));
   map1.put("accessoriesId",maintenanceRecordPage.getContent().get(i).getAccessoriesId());
   String yn = maintenanceRecordPage.getContent().get(i).getLackOfAccessories();
   String lackOfAccessories = "";
   if(yn.equals("0")){
    lackOfAccessories = "正常";
   }else {
    lackOfAccessories = "缺少";
   }
   map1.put("vehicleType",maintenanceRecordPage.getContent().get(i).getVehicleType());
   map1.put("accessoriesId",maintenanceRecordPage.getContent().get(i).getAccessoriesId());
   map1.put("useOfAccessories",maintenanceRecordPage.getContent().get(i).getUseOfAccessories());
   map1.put("lackOfAccessories",lackOfAccessories);
   map1.put("maintenancePrice",maintenanceRecordPage.getContent().get(i).getMaintenancePrice());
   map1.put("maintenanceTime",maintenanceRecordPage.getContent().get(i).getMaintenanceTime());//remark
   map1.put("remark",maintenanceRecordPage.getContent().get(i).getRemark());//
   listMap.add(map1);
  }
  JSONObject object = new JSONObject();
  object.put("code", 0);
  object.put("msg", "");
  object.put("count",maintenanceRecordPage.getTotalElements());
  object.put("data", listMap);
  return  object.toString();
 }

 @RequestMapping(value = "/insert",method = RequestMethod.POST)
 @ResponseBody
 @Transactional
 public JSONResult equmentManagementInsert(@RequestParam(value = "unitId",required = false) String unitId,
                                       @RequestParam(value = "licensePlateNumber",required = false) String licensePlateNumber,
                                       @RequestParam(value = "driverName",required = false) String driverName,
                                       @RequestParam(value = "storeRoom") String storeRoom,
                                       @RequestParam(value = "vehicleType") String vehicleType,
                                       @RequestParam(value = "accessoriesId") String accessoriesId,
                                       @RequestParam(value = "useOfAccessories") String useOfAccessories,
                                       @RequestParam(value = "lackOfAccessories") String lackOfAccessories,
                                       @RequestParam(value = "maintenancePrice") String maintenancePrice,
                                       @RequestParam(value = "maintenanceTime") String maintenanceTime,
                                       @RequestParam(value = "remark") String remark,
                                           @RequestParam(value = "accessoriesNumber") int accessoriesNumber,
                                           @RequestParam(value = "materialReceiveUnit") String materialReceiveUnit) {
      MaintenanceRecord maintenanceRecord = new MaintenanceRecord(unitId,licensePlateNumber,driverName,storeRoom,materialReceiveUnit,vehicleType,accessoriesId,accessoriesNumber,useOfAccessories,lackOfAccessories,maintenancePrice,maintenanceTime,remark);

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       //增加发放记录
      String uuid = UUID.randomUUID().toString();
      ReleaseRecord releaseRecord = new ReleaseRecord();
      releaseRecord.setMaterialIssuingUnit(storeRoom);
      releaseRecord.setMaterialReceiveUnit(materialReceiveUnit);//收料单位
      releaseRecord.setOutboundCategory(StringUtil.outboundCategory);
      releaseRecord.setAccessoriesId(accessoriesId);
      releaseRecord.setSpecification(null);//规格
      releaseRecord.setUnits(null);//单位
      releaseRecord.setOrginalNumber(null);//原厂编号
      releaseRecord.setDeliveryNumber(accessoriesNumber);//出库数
      releaseRecord.setPrice(maintenancePrice);//单价
      releaseRecord.setLicensePlateNumber(licensePlateNumber);//车牌号
      releaseRecord.setDeliveryDate(sdf.format(new Date()));//发放日期
      releaseRecord.setSumMoney(String.valueOf(Double.parseDouble(maintenancePrice)*accessoriesNumber));//总金额
      releaseRecord.setReponsiableName(null);
      releaseRecord.setUuid(uuid);
      try {
         maintenanceRecord.setUuid(uuid);
         maintenanceRecordRepository.save(maintenanceRecord);
         releaseRecordRepository.save(releaseRecord);
      }catch (Exception e){
         TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      }
      return JSONResult.build(200, "新增成功", "");
 }

 @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
 public String equmentManagementUpdate(Map<String,Object> map,@PathVariable("id") Long id) {
  MaintenanceRecord maintenanceRecord = maintenanceRecordRepository.findOne(id);

  List<Unit> unitList = new ArrayList<>();
  unitList = unitRepository.findAll();
  map.put("maintenanceRecord",maintenanceRecord);
  map.put("unitList",unitList);
  return "/maintenanceRecord_update";
 }

 @RequestMapping(value = "/update",method = RequestMethod.POST)
 @ResponseBody
 public JSONResult equmentManagementUpdate(@RequestParam(value = "id",required = false) Long id,
                                       @RequestParam(value = "unitId",required = false) String unitId,
                                       @RequestParam(value = "licensePlateNumber",required = false) String licensePlateNumber,
                                       @RequestParam(value = "driverName",required = false) String driverName,
                                       @RequestParam(value = "storeRoom",required = false) String storeRoom,
                                       @RequestParam(value = "vehicleType",required = false) String vehicleType,
                                       @RequestParam(value = "accessoriesId",required = false) String accessoriesId,
                                       @RequestParam(value = "useOfAccessories",required = false) String useOfAccessories,
                                       @RequestParam(value = "lackOfAccessories",required = false) String lackOfAccessories,
                                       @RequestParam(value = "maintenancePrice",required = false) String maintenancePrice,
                                           @RequestParam(value = "accessoriesNumber",required = false) int accessoriesNumber,
                                       @RequestParam(value = "maintenanceTime",required = false) String maintenanceTime,
                                       @RequestParam(value = "remark",required = false) String remark,
                                           @RequestParam(value = "materialReceiveUnit",required = false) String materialReceiveUnit) {
  MaintenanceRecord maintenanceRecord = new MaintenanceRecord(unitId,licensePlateNumber,driverName,storeRoom,materialReceiveUnit,vehicleType,accessoriesId,accessoriesNumber,useOfAccessories,lackOfAccessories,maintenancePrice,maintenanceTime,remark);
  maintenanceRecord.setId(id);
  maintenanceRecordRepository.save(maintenanceRecord);
  return JSONResult.build(200, "编辑成功", "");
 }

 @RequestMapping(value = "/delete",method = RequestMethod.POST)
 @ResponseBody
 @Transactional
 public JSONResult equmentManagementDelete(@RequestParam("id") Long id) {
  MaintenanceRecord maintenanceRecord = maintenanceRecordRepository.findOne(id);
    maintenanceRecordRepository.delete(id);
    ReleaseRecord releaseRecord = releaseRecordRepository.findAllByUuid(maintenanceRecord.getUuid());
    if(releaseRecord != null){
     releaseRecordRepository.deleteAllByUuid(releaseRecord.getUuid());
    }
  return JSONResult.build(200, "删除成功", "");
 }

 //导出excel
 @RequestMapping("/putout")
 @ResponseBody
 public JSONResult putout(HttpServletRequest request){
 // String path = Class.class.getClass().getResource("/").getPath()+"/static/download/";
  String path = "E://upload";
  ArrayList<MaintenanceRecord> list=new ArrayList<MaintenanceRecord>();
  list = (ArrayList<MaintenanceRecord>) maintenanceRecordRepository.findAll();
  try {
   File files=new File(path);
   if (!files.exists()) {
    files.mkdirs();
   }
   File file=new File(path+"/maintenanceRecord.xls");
   if(file.exists()){
    file.delete();
   }
   WritableWorkbook book= Workbook.createWorkbook(new File(path+"/maintenanceRecord.xls"));
   //设置表名
   WritableSheet sheet=book.createSheet("维修记录",0);
   //设置表第一行
   sheet.addCell(new Label(0,0,"单位id"));
   sheet.addCell(new Label(1,0,"车牌号"));
   sheet.addCell(new Label(2,0,"司机名称"));
   sheet.addCell(new Label(3,0,"库房号"));
   sheet.addCell(new Label(4,0,"使用单位"));
   sheet.addCell(new Label(5,0,"车辆类型"));
   sheet.addCell(new Label(6,0,"配件id"));
   sheet.addCell(new Label(7,0,"使用数量"));
   sheet.addCell(new Label(8,0,"配件使用情况"));
   sheet.addCell(new Label(9,0,"配件缺少情况"));
   sheet.addCell(new Label(10,0,"维修价格"));
   sheet.addCell(new Label(11,0,"维修时间"));
   sheet.addCell(new Label(12,0,"备注"));
   //添加数据

   for(int i=0;i<list.size();i++){
    sheet.addCell(new Label(0,i+1,list.get(i).getUnitId()));
    sheet.addCell(new Label(1,i+1,list.get(i).getLicensePlateNumber()));
    sheet.addCell(new Label(2,i+1,list.get(i).getDriverName()));
    sheet.addCell(new Label(3,i+1,list.get(i).getStoreRoom()));
    sheet.addCell(new Label(4,i+1,list.get(i).getMaterialReceiveUnit()));
    sheet.addCell(new Label(5,i+1,list.get(i).getVehicleType()));
    sheet.addCell(new Label(6,i+1,list.get(i).getAccessoriesId()));
    sheet.addCell(new jxl.write.Number(7,i+1,list.get(i).getAccessoriesNumber()));
    sheet.addCell(new Label(8,i+1,list.get(i).getUseOfAccessories()));
    sheet.addCell(new Label(9,i+1,list.get(i).getLackOfAccessories()));
    sheet.addCell(new Label(10,i+1,list.get(i).getMaintenancePrice()));
    sheet.addCell(new Label(11,i+1,list.get(i).getMaintenanceTime()));
    sheet.addCell(new Label(12,i+1,list.get(i).getRemark()));
   }
   book.write();//将所做的操作写入
   book.close();//关闭文件
  } catch (Exception e) {
   e.printStackTrace();
  }
  return JSONResult.build(200, "生成excel", "download/maintenanceRecord.xls");
 }

 //导入excel
 @RequestMapping(value = "/lead", method = RequestMethod.POST)
 @ResponseBody
 public String lead(MultipartFile file) {
  String path = "E://upload";
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
    MaintenanceRecord maintenanceRecord = new MaintenanceRecord();
    maintenanceRecord.setUnitId(sheet.getCell(0,i).getContents());
    maintenanceRecord.setLicensePlateNumber(sheet.getCell(1,i).getContents());
    maintenanceRecord.setDriverName(sheet.getCell(2,i).getContents());
    maintenanceRecord.setStoreRoom(sheet.getCell(3,i).getContents());
    maintenanceRecord.setMaterialReceiveUnit(sheet.getCell(4,i).getContents());
    maintenanceRecord.setVehicleType(sheet.getCell(5, i).getContents());
    maintenanceRecord.setAccessoriesId(sheet.getCell(6,i).getContents());
    maintenanceRecord.setAccessoriesNumber(Integer.parseInt(sheet.getCell(7,i).getContents()));
    maintenanceRecord.setUseOfAccessories(sheet.getCell(8,i).getContents());
    maintenanceRecord.setLackOfAccessories((sheet.getCell(9,i).getContents()));
    maintenanceRecord.setMaintenancePrice(sheet.getCell(10,i).getContents());
    maintenanceRecord.setMaintenanceTime(sheet.getCell(11,i).getContents());
    maintenanceRecord.setRemark(sheet.getCell(12,i).getContents());
   //发放记录
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    ReleaseRecord releaseRecord = new ReleaseRecord();
    releaseRecord.setMaterialIssuingUnit(sheet.getCell(3,i).getContents());
    releaseRecord.setMaterialReceiveUnit(sheet.getCell(4,i).getContents());//收料单位
    releaseRecord.setOutboundCategory(StringUtil.outboundCategory);
    releaseRecord.setAccessoriesId(sheet.getCell(6,i).getContents());
    releaseRecord.setSpecification(null);//规格
    releaseRecord.setUnits(null);//单位
    releaseRecord.setOrginalNumber(null);//原厂编号
    releaseRecord.setDeliveryNumber(Integer.parseInt(sheet.getCell(7,i).getContents()));//出库数
    releaseRecord.setPrice(sheet.getCell(10,i).getContents());//单价
    releaseRecord.setLicensePlateNumber(sheet.getCell(1,i).getContents());//车牌号
    releaseRecord.setDeliveryDate(sdf.format(new Date()));//发放日期
    releaseRecord.setSumMoney(String.valueOf(Double.parseDouble(sheet.getCell(10,i).getContents())*Integer.parseInt(sheet.getCell(7,i).getContents())));//总金额
    releaseRecord.setReponsiableName(null);
    releaseRecord.setUuid(UUID.randomUUID().toString());
    try {
     maintenanceRecordRepository.save(maintenanceRecord);
     releaseRecordRepository.save(releaseRecord);
    }catch (Exception e){
     TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
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
