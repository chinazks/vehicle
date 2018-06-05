package com.xforceplus.data.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 发放记录
 * Created by Administrator on 2018/5/27 0027.
 */
@Entity
@Table(name = "release_record")
public class ReleaseRecord implements Serializable {
    private static final long serialVersionUID = -8580380657563100559L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 发料单位 默认库房号
     */
    @Column(name = "material_issuing_unit",columnDefinition = "varchar(50)")
    private String materialIssuingUnit;
    /**
     * 收料单位
     */
    @Column(name = "material_receive_unit",columnDefinition = "varchar(50)")
    private String materialReceiveUnit;
    /**
     * 出库类别
     */
    @Column(name = "outbound_category",columnDefinition = "varchar(50)")
    private String outboundCategory;
    /**
     * 器材编码 对应配件id
     */
    @Column(name = "accessories_id",columnDefinition = "varchar(50)")
    private String accessoriesId;
    /**
     * 规格
     */
    @Column(name = "specification",columnDefinition = "varchar(10)")
    private String specification;
    /**
     * 单位
     */
    @Column(name = "units",columnDefinition = "varchar(10)")
    private String units;
    /**
     * 原厂编号
     */
    @Column(name = "orginal_number",columnDefinition = "varchar(50)")
    private String orginalNumber;
    /**
     * 出库数
     */
    @Column(name = "delivery_number",columnDefinition = "int")
    private int deliveryNumber;
    /**
     * 供应单价
     */
    @Column(name = "price",columnDefinition = "varchar(50)")
    private String price;
    /**
     * 车牌号
     */
    @Column(name = "license_plate_number",length = 10)
    private String licensePlateNumber;
    /**
     * 出库日期
     */
    @Column(name = "delivery_date",columnDefinition = "varchar(10)",length = 10)
    private String deliveryDate;
    /**
     * 总金额
     */
    @Column(name = "sum_money",columnDefinition = "varchar(50)")
    private String sumMoney;
    /**
     * 经办人
     */
    @Column(name = "reponsiable_name",columnDefinition = "varchar(10)",length = 10)
    private String reponsiableName;

    /**
     * uuid 唯一一一对应来源
     * @return
     */
    @Column(name = "uuid",columnDefinition = "varchar(50)",length = 50)
    private String uuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialIssuingUnit() {
        return materialIssuingUnit;
    }

    public void setMaterialIssuingUnit(String materialIssuingUnit) {
        this.materialIssuingUnit = materialIssuingUnit;
    }

    public String getMaterialReceiveUnit() {
        return materialReceiveUnit;
    }

    public void setMaterialReceiveUnit(String materialReceiveUnit) {
        this.materialReceiveUnit = materialReceiveUnit;
    }

    public String getOutboundCategory() {
        return outboundCategory;
    }

    public void setOutboundCategory(String outboundCategory) {
        this.outboundCategory = outboundCategory;
    }

    public String getAccessoriesId() {
        return accessoriesId;
    }

    public void setAccessoriesId(String accessoriesId) {
        this.accessoriesId = accessoriesId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getOrginalNumber() {
        return orginalNumber;
    }

    public void setOrginalNumber(String orginalNumber) {
        this.orginalNumber = orginalNumber;
    }

    public int getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(int deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(String sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getReponsiableName() {
        return reponsiableName;
    }

    public void setReponsiableName(String reponsiableName) {
        this.reponsiableName = reponsiableName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ReleaseRecord() {
    }

    public ReleaseRecord(String materialIssuingUnit, String materialReceiveUnit, String outboundCategory, String accessoriesId, String specification, String units, String orginalNumber, int deliveryNumber, String price, String licensePlateNumber, String deliveryDate, String sumMoney, String reponsiableName) {
        this.materialIssuingUnit = materialIssuingUnit;
        this.materialReceiveUnit = materialReceiveUnit;
        this.outboundCategory = outboundCategory;
        this.accessoriesId = accessoriesId;
        this.specification = specification;
        this.units = units;
        this.orginalNumber = orginalNumber;
        this.deliveryNumber = deliveryNumber;
        this.price = price;
        this.licensePlateNumber = licensePlateNumber;
        this.deliveryDate = deliveryDate;
        this.sumMoney = sumMoney;
        this.reponsiableName = reponsiableName;
    }

    public ReleaseRecord(String materialIssuingUnit, String materialReceiveUnit, String outboundCategory, String accessoriesId, String specification, String units, String orginalNumber, int deliveryNumber, String price, String licensePlateNumber, String deliveryDate, String sumMoney, String reponsiableName, String uuid) {
        this.materialIssuingUnit = materialIssuingUnit;
        this.materialReceiveUnit = materialReceiveUnit;
        this.outboundCategory = outboundCategory;
        this.accessoriesId = accessoriesId;
        this.specification = specification;
        this.units = units;
        this.orginalNumber = orginalNumber;
        this.deliveryNumber = deliveryNumber;
        this.price = price;
        this.licensePlateNumber = licensePlateNumber;
        this.deliveryDate = deliveryDate;
        this.sumMoney = sumMoney;
        this.reponsiableName = reponsiableName;
        this.uuid = uuid;
    }
}
