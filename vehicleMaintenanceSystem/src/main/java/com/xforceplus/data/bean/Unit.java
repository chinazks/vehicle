package com.xforceplus.data.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 单位
 * Created by Administrator on 2018/5/5 0005.
 */
@Entity
@Table(name="Unit")
public class Unit implements Serializable {
    private static final long serialintUID = 630093303149142892L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * 单位id
     */
    @Column(name = "unit_id",length = 50)
    private String unitId;
    /**
     * 单位名称
     */
    @Column(name = "unit_name",length = 50)
    private String unitName;

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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Unit() {
    }

    public Unit(Long id, String unitId, String unitName) {
        this.id = id;
        this.unitId = unitId;
        this.unitName = unitName;
    }
}
