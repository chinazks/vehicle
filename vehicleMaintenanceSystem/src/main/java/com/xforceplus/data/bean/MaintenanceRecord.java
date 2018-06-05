package com.xforceplus.data.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 维修记录
 * Created by Administrator on 2018/5/5 0005.
 */
@Entity
@Table(name="maintenance_record")
public class MaintenanceRecord implements Serializable {
    private static final long serialVersionUID = 6748417636267487017L;
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
     * 车牌号
     */
    @Column(name = "license_plate_number",length = 10)
    private String licensePlateNumber;
    /**
     * 司机名称
     */
    @Column(name = "driver_name",columnDefinition = "varchar(50)",length = 50)
    private String driverName;
    /**
     * 库房号
     */
    @Column(name = "store_room",length = 10)
    private String storeRoom;
    /**
     * 车辆类型
     */
    @Column(name = "vehicle_type",length = 10)
    private String vehicleType;

    /**
     * 配件id 就是器材编码
     */
    @Column(name = "accessories_id",columnDefinition = "varchar(50)",length = 50)
    private String accessoriesId;
    /**
     * 使用数量
     */
    @Column(name = "accessories_number",columnDefinition = "int",length = 50)
    private int accessoriesNumber;
    /**
     * 配件使用情况
     */
    @Column(name = "use_of_accessories",length = 100)
    private String useOfAccessories;
    /**
     * 配件缺少情况
     */
    @Column(name = "lack_of_accessories",length = 1)
    private String lackOfAccessories;
    /**
     * 维修价格
     */
    @Column(name = "maintenance_price")
    private String maintenancePrice;
    /**
     * 维修时间
     */
    @Column(name = "maintenance_time")
    private String maintenanceTime;
    /**
     * 备注
     */
    @Column(name = "remark",length = 500)
    private String remark;
    /**
     * 使用单位materialReceiveUnit
     */
    @Column(name = "material_receive_unit",columnDefinition = "varchar(50)",length = 500)
    private String materialReceiveUnit;
    /**
     * uuid 唯一一一对应发放记录
     * @return
     */
    @Column(name = "uuid",columnDefinition = "varchar(50)",length = 50)
    private String uuid;

    public int getAccessoriesNumber() {
        return accessoriesNumber;
    }

    public void setAccessoriesNumber(int accessoriesNumber) {
        this.accessoriesNumber = accessoriesNumber;
    }

    public String getMaterialReceiveUnit() {
        return materialReceiveUnit;
    }

    public void setMaterialReceiveUnit(String materialReceiveUnit) {
        this.materialReceiveUnit = materialReceiveUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(String maintenanceTime) {
        this.maintenanceTime = maintenanceTime;
    }

    public String getUseOfAccessories() {
        return useOfAccessories;
    }

    public void setUseOfAccessories(String useOfAccessories) {
        this.useOfAccessories = useOfAccessories;
    }

    public String getStoreRoom() {
        return storeRoom;
    }

    public void setStoreRoom(String storeRoom) {
        this.storeRoom = storeRoom;
    }

    public String getMaintenancePrice() {
        return maintenancePrice;
    }

    public void setMaintenancePrice(String maintenancePrice) {
        this.maintenancePrice = maintenancePrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccessoriesId() {
        return accessoriesId;
    }

    public void setAccessoriesId(String accessoriesId) {
        this.accessoriesId = accessoriesId;
    }

    public MaintenanceRecord() {
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getLackOfAccessories() {
        return lackOfAccessories;
    }

    public void setLackOfAccessories(String lackOfAccessories) {
        this.lackOfAccessories = lackOfAccessories;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public MaintenanceRecord(String unitId, String licensePlateNumber, String driverName, String storeRoom, String materialReceiveUnit ,String vehicleType, String accessoriesId, int accessoriesNumber, String useOfAccessories, String lackOfAccessories, String maintenancePrice, String maintenanceTime, String remark) {
        this.unitId = unitId;
        this.licensePlateNumber = licensePlateNumber;
        this.driverName = driverName;
        this.storeRoom = storeRoom;
        this.vehicleType = vehicleType;
        this.accessoriesId = accessoriesId;
        this.accessoriesNumber = accessoriesNumber;
        this.useOfAccessories = useOfAccessories;
        this.lackOfAccessories = lackOfAccessories;
        this.maintenancePrice = maintenancePrice;
        this.maintenanceTime = maintenanceTime;
        this.remark = remark;
        this.materialReceiveUnit = materialReceiveUnit;
    }

    public MaintenanceRecord(String unitId, String licensePlateNumber, String driverName, String storeRoom,String materialReceiveUnit, String vehicleType, String accessoriesId, int accessoriesNumber, String useOfAccessories, String lackOfAccessories, String maintenancePrice, String maintenanceTime, String remark, String uuid) {
        this.unitId = unitId;
        this.licensePlateNumber = licensePlateNumber;
        this.driverName = driverName;
        this.storeRoom = storeRoom;
        this.vehicleType = vehicleType;
        this.accessoriesId = accessoriesId;
        this.accessoriesNumber = accessoriesNumber;
        this.useOfAccessories = useOfAccessories;
        this.lackOfAccessories = lackOfAccessories;
        this.maintenancePrice = maintenancePrice;
        this.maintenanceTime = maintenanceTime;
        this.remark = remark;
        this.materialReceiveUnit = materialReceiveUnit;
        this.uuid = uuid;
    }
}
