package com.xforceplus.data.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 器材管理
 * Created by Administrator on 2018/5/5 0005.
 */
@Entity
@Table(name="equipment_management")
public class EquipmentManagement implements Serializable {
    private static final long serialVersionUID = -969872788355668530L;
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * 库房号
     */
    @Column(name = "store_room",columnDefinition = "varchar(10)",length = 10)
    private String storeRoom;
    /**
     * 收料单位
     */
    @Column(name = "material_issuing_unit",columnDefinition = "varchar(50)")
    private String materialIssuingUnit;
    /**
     * 车牌号
     */
    @Column(name = "license_plate_number",length = 10)
    private String licensePlateNumber;
    /**
     * 车辆类型
     */
    @Column(name = "vehicle_type",length = 20)
    private String vehicleType;
    /**
     * 配件id
     */
    @Column(name = "accessories_id",columnDefinition = "varchar(50)",length = 50)
    private String accessoriesId;
    /**
     * 配件名字
     */
    @Column(name = "accessories_Name",length = 50)
    private String accessoriesName;
    /**
     * 规格
     */
    @Column(name = "specifications",length = 50)
    private String specifications;
    /**
     * 原厂编号
     */
    @Column(name = "original_factory_number",length = 50)
    private String originalFactoryNumber;
    /**
     * 单位
     */
    @Column(name = "unit",length = 5)
    private String unit;
    /**
     * 入库单价
     */
    @Column(name = "warehouse_unit_price",length = 50)
    private String warehouseUnitPrice;
    /**
     * 库房总库存
     */
    @Column(name = "stock",columnDefinition = "int")
    private int stock;
    /**
     * 货位号
     */
    @Column(name = "good_num",columnDefinition = "varchar(20)")
    private String goodsNum;
    /**
     * 到货日期
     */
    @Column(name = "delivery_date",columnDefinition = "varchar(10)")
    private String deliveryDate;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreRoom() {
        return storeRoom;
    }

    public void setStoreRoom(String storeRoom) {
        this.storeRoom = storeRoom;
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

    public String getAccessoriesId() {
        return accessoriesId;
    }

    public void setAccessoriesId(String accessoriesId) {
        this.accessoriesId = accessoriesId;
    }

    public String getAccessoriesName() {
        return accessoriesName;
    }

    public void setAccessoriesName(String accessoriesName) {
        this.accessoriesName = accessoriesName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getOriginalFactoryNumber() {
        return originalFactoryNumber;
    }

    public String getMaterialIssuingUnit() {
        return materialIssuingUnit;
    }

    public void setMaterialIssuingUnit(String materialIssuingUnit) {
        this.materialIssuingUnit = materialIssuingUnit;
    }

    public void setOriginalFactoryNumber(String originalFactoryNumber) {
        this.originalFactoryNumber = originalFactoryNumber;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWarehouseUnitPrice() {
        return warehouseUnitPrice;
    }

    public void setWarehouseUnitPrice(String warehouseUnitPrice) {
        this.warehouseUnitPrice = warehouseUnitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

	public EquipmentManagement(String storeRoom, String materialIssuingUnit, String licensePlateNumber,
			String vehicleType, String accessoriesId, String accessoriesName, String specifications,
			String originalFactoryNumber, String unit, String warehouseUnitPrice, int stock, String goodsNum,
			String deliveryDate) {
		super();
		this.storeRoom = storeRoom;
		this.materialIssuingUnit = materialIssuingUnit;
		this.licensePlateNumber = licensePlateNumber;
		this.vehicleType = vehicleType;
		this.accessoriesId = accessoriesId;
		this.accessoriesName = accessoriesName;
		this.specifications = specifications;
		this.originalFactoryNumber = originalFactoryNumber;
		this.unit = unit;
		this.warehouseUnitPrice = warehouseUnitPrice;
		this.stock = stock;
		this.goodsNum = goodsNum;
		this.deliveryDate = deliveryDate;
	}

	public EquipmentManagement() {
		super();
	}
    
    

}
