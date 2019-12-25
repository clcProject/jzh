package com.demo.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity(name = "card_order")
public class card_order {

    @Id
    @GeneratedValue
    @NotNull
    private int id;
    private String wechatid;
    private String username;
    private String phone;
    private String address;
    private int taocan_id;
    private String taocan_name;
    private String card_name;
    private String company_name;
    private String card_number;
    private int order_state;
    private String express;
    private String express_no;
    private String logistics;
    private Timestamp createdate;
    private int isfalg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTaocan_id() {
        return taocan_id;
    }

    public void setTaocan_id(int taocan_id) {
        this.taocan_id = taocan_id;
    }

    public String getTaocan_name() {
        return taocan_name;
    }

    public void setTaocan_name(String taocan_name) {
        this.taocan_name = taocan_name;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public int getIsfalg() {
        return isfalg;
    }

    public void setIsfalg(int isfalg) {
        this.isfalg = isfalg;
    }

    public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }
}
