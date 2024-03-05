package com.yovvis.example.common.model;

import java.io.Serializable;

/**
 * 用户model
 *
 * @author yovvis
 * @date 2024/3/5
 */
public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
