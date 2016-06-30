package com.jikexueyuan.usinglayoutdirectory;

/**
 * Created by fangc on 2016/3/23.
 */
public class People {
    private String name;
    private int age;

    private static People people;

    public static People getPeopleInstance(){ //简单的单例模式
        if (people==null){
            people=new People();
        }
        return people;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
