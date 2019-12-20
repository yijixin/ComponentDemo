package com.uidt.module_base_one.exp;

/**
 * @author yijixin on 2019-12-12
 */
public class OrderInfo extends BaseEntity {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
