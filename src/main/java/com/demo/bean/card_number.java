package com.demo.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity(name = "card_number")
public class card_number {

    @Id
    @GeneratedValue
    @NotNull
    private int id;
    private String cardno;
    private String password;
    private int card_type_id;
    private int companyid;
    private int card_state;
    private Timestamp createdate;
    private int isfalg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getCard_state() {
        return card_state;
    }

    public void setCard_state(int card_state) {
        this.card_state = card_state;
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
}
