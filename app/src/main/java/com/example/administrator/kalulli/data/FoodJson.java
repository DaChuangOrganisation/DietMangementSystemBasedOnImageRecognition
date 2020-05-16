package com.example.administrator.kalulli.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2019/4/1.
 */

public class FoodJson {
    private String foodname;
    private String number;
    private String picture_url;
    private String description;
    private List<Element> elements = new ArrayList<>();

    public FoodJson(String foodname, String number, String picture_url, String description) {
        this.foodname = foodname;
        this.number = number;
        this.picture_url = picture_url;
        this.description = description;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public static class Element {
        private String name;
        private String value;

        public Element(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 填入食物的营养物质组成，以及含量
     *
     * @param name 营养物质民
     * @param value 含量
     */
    public void push(String name, String value) {
        this.elements.add(new Element(name, value));
    }

    /**
     * 清空该食物的营养物质组成列表
     */
    public void clear() {
        this.elements.clear();
    }
}
