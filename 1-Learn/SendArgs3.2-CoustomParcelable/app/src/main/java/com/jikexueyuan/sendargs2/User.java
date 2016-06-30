package com.jikexueyuan.sendargs2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fangc on 2016/1/5.
 */
//http://my.oschina.net/zhoulc/blog/172163 ---Parcelable与Serializable的理解与对比
      /* android自定义对象可序列化有两个选择一个是Serializable和Parcelable

    一、对象为什么需要序列化
        1.永久性保存对象，保存对象的字节序列到本地文件。
        2.通过序列化对象在网络中传递对象。
        3.通过序列化对象在进程间传递对象。

    二、当对象需要被序列化时如何选择所使用的接口
        1.在使用内存的时候Parcelable比Serializable的性能高。
        2.Serializable在序列化的时候会产生大量的临时变量，从而引起频繁的GC（内存回收）。
        3.Parcelable不能使用在将对象存储在磁盘上这种情况，因为在外界的变化下Parcelable不能很好的保证数据的持续性。*/
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
