package com.chenyl.zeju;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by iluvsnail on 2017/5/21.
 */
public class Zeju {
    private String name;
    private String addr;
    private String money;
    private String discount;
    private List<HouseType> types;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public List<HouseType> getTypes() {
        return types;
    }

    public void setTypes(List<HouseType> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
