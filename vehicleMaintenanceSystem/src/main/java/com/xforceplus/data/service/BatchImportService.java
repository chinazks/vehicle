package com.xforceplus.data.service;

import com.xforceplus.data.bean.UnitInformation;
import com.xforceplus.data.dao.UnitInformationRepository;
import com.xforceplus.data.tools.ExcleImportUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/6 0006.
 */
@Service
public class BatchImportService {
    @Autowired
    private UnitInformationRepository unitInformationRepository;
    /**
     * 上传excel文件到临时目录后并开始解析
     *
     * @param fileName
     * @return
     */
    public String batchImport(String fileName, MultipartFile mfile) {

        File uploadDir = new File("E:\\fileupload");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!uploadDir.exists()) uploadDir.mkdirs();
        //新建一个文件
        File tempFile = new File("E:\\fileupload\\" + new Date().getTime() + ".xlsx");
        //初始化输入流
        InputStream is = null;
        try {
            //将上传的文件写入新建的文件中
            mfile.transferTo(tempFile);

            //根据新建的文件实例化输入流
            is = new FileInputStream(tempFile);

            //根据版本选择创建Workbook的方式
            Workbook wb = null;
            //根据文件名判断文件是2003版本还是2007版本
            if (ExcleImportUtils.isExcel2007(fileName)) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = new HSSFWorkbook(is);
            }
            //根据excel里面的内容读取知识库信息
            return readExcelValue(wb, tempFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return "导入出错！请检查数据格式！";
    }


    /**
     * 解析Excel里面的数据
     *
     * @param wb
     * @return
     */
    private String readExcelValue(Workbook wb, File tempFile) {

        //错误信息接收器
        String errorMsg = "";
        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        //得到Excel的行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        //总列数
        int totalCells = 0;
        //得到Excel的列数(前提是有行数)，从第二行算起
        if (totalRows >= 2 && sheet.getRow(1) != null) {
            totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        }
        List<UnitInformation> unitInformationList = new ArrayList<UnitInformation>();
        UnitInformation unitInformation;

        String br = "<br/>";

        //循环Excel行数,从第二行开始。标题不入库
        for (int r = 1; r < totalRows; r++) {
            String rowMessage = "";
            Row row = sheet.getRow(r);
            if (row == null) {
                errorMsg += br + "第" + (r + 1) + "行数据有问题，请仔细检查！";
                continue;
            }
            unitInformation = new UnitInformation();

            //循环Excel的列
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String content = cell.getStringCellValue();
                if (c == 0) {
                    if(StringUtils.isEmpty(content)) {
                        rowMessage += "问题不能为空；";
                        continue;
                    }
                    unitInformation.setEquipmentModel(content);
                } else if (c == 1) {
                    unitInformation.setEquipmentModel(content);
                } else if (c == 2) {
                    unitInformation.setEquipmentName(content);
                } else if (c == 3) {
                    unitInformation.setDispensingTime(content);
                } else if (c == 4) {
                    int a = 0;
                    try {
                        a = Integer.parseInt(content);
                    } catch (Exception e) {
                        rowMessage += "库存数量请填写数字";
                        e.printStackTrace();
                        continue;
                    }
                    unitInformation.setStockQuantity(a);
                } else if (c == 4) {
                    unitInformation.setTechnicalStatus(content);
                }
            }
            //拼接每行的错误提示
            if (!StringUtils.isEmpty(rowMessage)) {
                errorMsg += br + "第" + (r + 1) + "行，" + rowMessage;
            } else {
                unitInformationList.add(unitInformation);
            }
        }

        //删除上传的临时文件
        if (tempFile.exists()) {
            tempFile.delete();
        }

        //全部验证通过才导入到数据库
        if (StringUtils.isEmpty(errorMsg)) {
            for (UnitInformation userKnowledgeBase : unitInformationList) {
                unitInformationRepository.save(userKnowledgeBase);
            }
            errorMsg = "导入成功，共" + unitInformationList.size() + "条数据！";
        }
        return errorMsg;
    }
}
