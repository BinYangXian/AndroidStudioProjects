package com.jikexueyuan.sendargs2;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import javax.xml.transform.Source;

/**
 * Created by fangc on 2016/1/5.
 */
public class User implements Parcelable {
    private String name;
    private int age;


    protected User(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public  User(String name,int age){
        this.name=name;
        this.age=age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getName());
        dest.writeInt(getAge());
    }
}
