package com.xlauncher.entity;

import java.util.List;

public class User {
    private String name;
    private Integer age;
    private List<Customer> customerList;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }
}
