package com.example.administrator.kalulli.litepal;

public interface DataManipulation {
    /*存储或更新此对象到数据库
    若该对象重未被存储过，则save的功能是保存对象到数据库
    若该对象已存在数据库中，则save的功能是更新该对象到数据库
    返回true如果保存或更新成功
    使用范例:
    FoodItem item1 = new FoodItem("水果沙拉",452,new Date(),"img/item1-20200807.jpg");
    item1.save();
     */
    boolean saveOrUpdateData();

    /*从数据库删除此对象
     */
    int deleteData();

}
