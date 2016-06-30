package com.jikexueyuan.thinknumbergame;

import static java.lang.Math.random;

/**
 * Created by fangc on 2016/1/6.
 */
public class ThinkNumber {
    private int randomNumber;

    public int getRandomNumber() {
        return randomNumber;
    }
    public ThinkNumber(){
        this.randomNumber= (int) (101*random());
    }
}
