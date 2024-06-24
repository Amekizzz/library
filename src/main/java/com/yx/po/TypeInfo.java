package com.yx.po;

import java.io.Serializable;

public class TypeInfo implements Serializable {

    private Integer id;//类型信息的标识
    private String name;//类型名称
    private String remarks;//备注

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

}