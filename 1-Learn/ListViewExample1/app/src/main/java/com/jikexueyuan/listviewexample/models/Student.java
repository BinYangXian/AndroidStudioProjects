package com.jikexueyuan.listviewexample.models;

/**
 * Created by fangc on 2016/2/21.
 */
public class Student {
    private String name;
    private int age;


    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
//        return super.toString();
        return name;//例子2
    }
}
