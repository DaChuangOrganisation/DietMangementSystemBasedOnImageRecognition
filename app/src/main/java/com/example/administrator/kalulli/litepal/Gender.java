package com.example.administrator.kalulli.litepal;

public enum Gender{
    Male(1), Female(2);

    private String name;
    private int index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    Gender(){

    }
    Gender(int index){
        this.name = index == 1 ? "男":"女";
        this.index = index;
    }


}
