package com.demo.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity(name = "card_taocan")
public class card_taocan {

    @Id
    @GeneratedValue
    @NotNull
    private int id;
    private String taocan_name;
    private int card_type_id;
    private int companyid;
    private String imgurl;
    private String content;
    private Timestamp createdate;
    private int isflag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaocan_name() {
        return taocan_name;
    }

    public void setTaocan_name(String taocan_name) {
        this.taocan_name = taocan_name;
    }

    public int getCard_type_id() {
        return card_type_id;
    }

    public void setCard_type_id(int card_type_id) {
        this.card_type_id = card_type_id;
    }

    public int getCompanyId() {
        return companyid;
    }

    public void setCompanyId(int companyId) {
        this.companyid = companyId;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public int getIsflag() {
        return isflag;
    }

    public void setIsflag(int isflag) {
        this.isflag = isflag;
    }
}
