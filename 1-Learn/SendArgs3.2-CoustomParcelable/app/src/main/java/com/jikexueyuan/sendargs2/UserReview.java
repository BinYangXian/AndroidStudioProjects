package com.jikexueyuan.sendargs2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fangc on 2016/4/21.
 */
public class UserReview implements Parcelable {
    private String name;
    private int age;
    protected UserReview(Parcel in) {
        name=in.readString();
        age=in.readInt();
    }

    public static final Creator<UserReview> CREATOR = new Creator<UserReview>() {
        @Override
        public UserReview createFromParcel(Parcel in) {
            return new UserReview(in);
        }

        @Override
        public UserReview[] newArray(int size) {
            return new UserReview[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public UserReview(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
