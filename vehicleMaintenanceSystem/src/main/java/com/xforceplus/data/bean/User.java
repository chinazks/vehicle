package com.xforceplus.data.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/21 0021.
 */
@Entity
@Table(name="vehicle_user")
public class User implements Serializable {
    private static final long serialVersionUID = 8722183384639625742L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * userName
     */
    @Column(name = "user_name",columnDefinition = "varchar(30)")
    private String userName;
    /**
     * password
     */
    @Column(name = "password",columnDefinition = "varchar(30)")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
