package com.xforceplus.data.bean;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 车辆管理
 * Created by Administrator on 2018/5/5 0005.
 */
@Entity
@Table(name="vehicle_management")
public class VehicleManagement implements Serializable {
    private static final long serialVersionUID = -332939265018015024L;
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * 装备名称
     */
    @Column(name = "equipment_Name",length = 50)
    @Length(max = 50)
    private String equipmentName;
    /**
     * 装备型号
     */
    @Column(name = "equipment_model",length = 50)
    @Length(max = 50)
    private String equipmentModel;
    /**
     * 车牌号
     */
    @Column(name = "license_plate_number",length = 10)
    @Length(max = 10)
    private String licensePlateNumber;
    /**
     * 车辆类型
     */
    @Column(name = "vehicle_type",length = 10)
    @Length(max = 10)
    private String vehicleType;
    /**
     * 司机名称
     */
    @Column(name = "driver_name",length = 4)
    @Length(max = 4)
    private String driverName;
    /**
     * 单位id
     */
    @Column(name = "unit_id",length = 3)
    @Length(max = 3)
    private String  unitId;
    /**
     * 备注
     */
    @Column(name = "remarke",length = 500)
    @Length(max = 500)
    private String remarke;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getRemarke() {
        return remarke;
    }

    public void setRemarke(String remarke) {
        this.remarke = remarke;
    }

    public VehicleManagement() {
    }

    public VehicleManagement(String equipmentName, String equipmentModel, String licensePlateNumber, String vehicleType, String driverName, String unitId, String remarke) {
        this.equipmentName = equipmentName;
        this.equipmentModel = equipmentModel;
        this.licensePlateNumber = licensePlateNumber;
        this.vehicleType = vehicleType;
        this.driverName = driverName;
        this.unitId = unitId;
        this.remarke = remarke;
    }

}
