package com.example.administrator.kalulli.litepal;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Weight extends LitePalSupport implements DataManipulation {
    private long id;
    private double weight;//kg
    private Date date;//体重录入日期

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override

    public boolean saveOrUpdateData() {
        return this.save();
    }

    @Override
    public int deleteData() {
        return LitePal.delete(Weight.class,this.getId());
    }
}
