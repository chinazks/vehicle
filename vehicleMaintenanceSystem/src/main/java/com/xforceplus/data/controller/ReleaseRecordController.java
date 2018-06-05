package com.xforceplus.data.controller;

import com.alibaba.fastjson.JSONObject;
import com.xforceplus.data.bean.ReleaseRecord;
import com.xforceplus.data.dao.ReleaseRecordRepository;
import com.xforceplus.data.tools.JSONResult;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 发放记录
 * Created by Administrator on 2018/5/27 0027.
 */
@Controller
public class ReleaseRecordController {
    @Autowired
    private ReleaseRecordRepository releaseRecordRepository;

    @RequestMapping("/releaseRecord_list")
    public String releaseRecordListRequest(){
        return "/releaseRecord/releaseRecord_list";
    }
    @RequestMapping("/releaseRecord/list")
    @ResponseBody
    public String equmentManagementList(@RequestParam("page") int pages, @RequestParam("limit") int limit){
        Page<ReleaseRecord> releaseRecordPage=releaseRecordRepository.findAll(new PageRequest(pages-1,limit));
        List<Map<String,String>> listMap=new ArrayList<>();//传到前端
        for(int i=0;i<releaseRecordPage.getContent().size();i++){
            Map<String,String> map1=new HashMap<>();
            map1.put("id",String.valueOf(releaseRecordPage.getContent().get(i).getId()));
            map1.put("materialIssuingUnit",releaseRecordPage.getContent().get(i).getMaterialIssuingUnit());
            map1.put("materialReceiveUnit",releaseRecordPage.getContent().get(i).getMaterialReceiveUnit());
            map1.put("outboundCategory",releaseRecordPage.getContent().get(i).getOutboundCategory());
            map1.put("accessoriesId",releaseRecordPage.getContent().get(i).getAccessoriesId());
            map1.put("specification",releaseRecordPage.getContent().get(i).getSpecification());
            map1.put("units",releaseRecordPage.getContent().get(i).getUnits());
            map1.put("orginalNumber",releaseRecordPage.getContent().get(i).getOrginalNumber());
            map1.put("deliveryNumber",String.valueOf(releaseRecordPage.getContent().get(i).getDeliveryNumber()));
            map1.put("price",releaseRecordPage.getContent().get(i).getPrice());
            map1.put("licensePlateNumber",releaseRecordPage.getContent().get(i).getLicensePlateNumber());
            map1.put("deliveryDate",releaseRecordPage.getContent().get(i).getDeliveryDate());
            map1.put("sumMoney",releaseRecordPage.getContent().get(i).getSumMoney());
            map1.put("reponsiableName",releaseRecordPage.getContent().get(i).getReponsiableName());
            map1.put("uuid",releaseRecordPage.getContent().get(i).getUuid());//
            listMap.add(map1);
        }
        JSONObject object = new JSONObject();
        object.put("code", 0);
        object.put("msg", "");
        object.put("count",releaseRecordPage.getTotalElements());
        object.put("data", listMap);
        return  object.toString();
    }
    @RequestMapping("/releaseRecord_insert")
    public String maintenanceRecordInsert(){
        return "releaseRecord/releaseRecord_insert";
    }
    @RequestMapping(value = "/releaseRecord/insert",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public JSONResult equmentManagementInsert(@RequestParam(value = "materialIssuingUnit",required = false) String materialIssuingUnit,
                                              @RequestParam(value = "materialReceiveUnit",required = false) String materialReceiveUnit,
                                              @RequestParam(value = "outboundCategory",required = false) String outboundCategory,
                                              @RequestParam(value = "accessoriesId",required = false) String accessoriesId,
                                              @RequestParam(value = "specification",required = false) String specification,
                                              @RequestParam(value = "units",required = false) String units,
                                              @RequestParam(value = "orginalNumber",required = false) String orginalNumber,
                                              @RequestParam(value = "deliveryNumber",required = false) int deliveryNumber,
                                              @RequestParam(value = "price",required = false) String price,
                                              @RequestParam(value = "licensePlateNumber",required = false) String licensePlateNumber,
                                              @RequestParam(value = "deliveryDate",required = false) String deliveryDate,
                                              @RequestParam(value = "reponsiableName",required = false) String reponsiableName) {
        String sumMoney = String.valueOf(Double.parseDouble(price)*deliveryNumber);
        ReleaseRecord maintenanceRecord = new ReleaseRecord(materialIssuingUnit, materialReceiveUnit, outboundCategory, accessoriesId, specification, units, orginalNumber,  deliveryNumber, price, licensePlateNumber, deliveryDate, sumMoney, reponsiableName, UUID.randomUUID().toString());
        releaseRecordRepository.save(maintenanceRecord);
        return JSONResult.build(200, "新增成功", "");
    }

    //导出excel
    @RequestMapping("/releaseRecord/putout")
    @ResponseBody
    public JSONResult putout(HttpServletRequest request){
        // String path = Class.class.getClass().getResource("/").getPath()+"/static/download/";
        String path = "E://upload";
        ArrayList<ReleaseRecord> list=new ArrayList<ReleaseRecord>();
        list = (ArrayList<ReleaseRecord>) releaseRecordRepository.findAll();
        try {
            File files=new File(path);
            if (!files.exists()) {
                files.mkdirs();
            }
            File file=new File(path+"/releaseRecord.xls");
            if(file.exists()){
                file.delete();
            }

            WritableWorkbook book= Workbook.createWorkbook(new File(path+"/maintenanceRecord.xls"));
            //设置表名
            WritableSheet sheet=book.createSheet("发放记录",0);
            //设置表第一行
            sheet.addCell(new Label(0,0,"发料单位"));
            sheet.addCell(new Label(1,0,"收料单位"));
            sheet.addCell(new Label(2,0,"出库类别"));
            sheet.addCell(new Label(3,0,"器材编码"));
            sheet.addCell(new Label(4,0,"规格"));
            sheet.addCell(new Label(5,0,"单位"));
            sheet.addCell(new Label(6,0,"原厂编号"));
            sheet.addCell(new Label(7,0,"出库数"));
            sheet.addCell(new Label(8,0,"供应单价"));
            sheet.addCell(new Label(9,0,"车牌号"));
            sheet.addCell(new Label(10,0,"出库日期"));
            sheet.addCell(new Label(11,0,"总金额"));
            //添加数据
            for(int i=0;i<list.size();i++){
                sheet.addCell(new Label(0,i+1,list.get(i).getMaterialIssuingUnit()));
                sheet.addCell(new Label(1,i+1,list.get(i).getMaterialReceiveUnit()));
                sheet.addCell(new Label(2,i+1,list.get(i).getOutboundCategory()));
                sheet.addCell(new Label(3,i+1,list.get(i).getAccessoriesId()));
                sheet.addCell(new Label(4,i+1,list.get(i).getSpecification()));
                sheet.addCell(new Label(5,i+1,list.get(i).getUnits()));
                sheet.addCell(new Label(6,i+1,list.get(i).getOrginalNumber()));
                sheet.addCell(new jxl.write.Number(7,i+1,list.get(i).getDeliveryNumber()));
                sheet.addCell(new Label(8,i+1,list.get(i).getPrice()));
                sheet.addCell(new Label(9,i+1,list.get(i).getLicensePlateNumber()));
                sheet.addCell(new Label(10,i+1,list.get(i).getDeliveryDate()));
                sheet.addCell(new Label(11,i+1,list.get(i).getSumMoney()));
            }
            book.write();//将所做的操作写入
            book.close();//关闭文件
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONResult.build(200, "生成excel", "download/releaseRecord.xls");
    }

    //导入excel
    @RequestMapping(value = "/releaseRecord/lead", method = RequestMethod.POST)
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
                //发放记录
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                ReleaseRecord releaseRecord = new ReleaseRecord();
                releaseRecord.setMaterialIssuingUnit(sheet.getCell(0,i).getContents());
                releaseRecord.setMaterialReceiveUnit(sheet.getCell(1,i).getContents());//收料单位
                releaseRecord.setOutboundCategory(sheet.getCell(2,i).getContents());
                releaseRecord.setAccessoriesId(sheet.getCell(3,i).getContents());
                releaseRecord.setSpecification(sheet.getCell(4,i).getContents());//规格
                releaseRecord.setUnits(sheet.getCell(5,i).getContents());//单位
                releaseRecord.setOrginalNumber(sheet.getCell(6,i).getContents());//原厂编号
                releaseRecord.setDeliveryNumber(Integer.parseInt(sheet.getCell(7,i).getContents()));//出库数
                releaseRecord.setPrice(sheet.getCell(8,i).getContents());//单价
                releaseRecord.setLicensePlateNumber(sheet.getCell(9,i).getContents());//车牌号
                releaseRecord.setDeliveryDate(sdf.format(new Date()));//发放日期
                releaseRecord.setSumMoney(sheet.getCell(11,i).getContents());//总金额
                releaseRecord.setReponsiableName(null);
                releaseRecord.setUuid(UUID.randomUUID().toString());
                releaseRecordRepository.save(releaseRecord);
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
    @RequestMapping(value = "/releaseRecord/delete",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public JSONResult equmentManagementDelete(@RequestParam("id") Long id) {
        releaseRecordRepository.delete(id);
        return JSONResult.build(200, "删除成功", "");
    }
}
