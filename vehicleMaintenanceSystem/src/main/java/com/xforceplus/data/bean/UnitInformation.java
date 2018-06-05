package com.xforceplus.data.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 单位信息
 * Created by Administrator on 2018/5/5 0005.
 */
@Entity
@Table(name="unit_information")
public class UnitInformation  implements Serializable {

    private static final long serialVersionUID = 2391437255220606618L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * 单位id
     */
    @Column(name = "unit_id",length = 3)
    private String unitId;
    /**
     *装备型号
     */
    @Column(name = "equipment_model",length = 50)
    private String equipmentModel;
    /**
     * 装备名称
     */
    @Column(name = "equipment_Name",length = 50)
    private String equipmentName;
    /**
     * 配发时间
     */
    @Column(name = "dispensing_time",length = 10)
    private String dispensingTime;
    /**
     * 数量
     */
    @Column(name = "stock_quantity")
    private int stockQuantity;
    /**
     * 技术状况
     */
    @Column(name = "technical_status",length = 100)
    private String technicalStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getDispensingTime() {
        return dispensingTime;
    }

    public void setDispensingTime(String dispensingTime) {
        this.dispensingTime = dispensingTime;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getTechnicalStatus() {
        return technicalStatus;
    }

    public void setTechnicalStatus(String technicalStatus) {
        this.technicalStatus = technicalStatus;
    }

    public UnitInformation() {
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        return "UnitInformation{" +
                "id=" + id +
                ", unitId='" + unitId + '\'' +
                ", equipmentModel='" + equipmentModel + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", dispensingTime='" + dispensingTime + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", technicalStatus='" + technicalStatus + '\'' +
                '}';
    }

    public UnitInformation(String unitId, String equipmentModel, String equipmentName, String dispensingTime, int stockQuantity, String technicalStatus) {
        this.unitId = unitId;
        this.equipmentModel = equipmentModel;
        this.equipmentName = equipmentName;
        this.dispensingTime = dispensingTime;
        this.stockQuantity = stockQuantity;
        this.technicalStatus = technicalStatus;
    }

}
