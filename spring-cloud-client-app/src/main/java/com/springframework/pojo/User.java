package com.springframework.pojo;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/3/8
 * @see User
 * @since 1.0
 */

public class User {

    private String name;
    private String pawssord;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPawssord() {
        return pawssord;
    }

    public void setPawssord(String pawssord) {
        this.pawssord = pawssord;
    }
}
