package com.jikexueyuan.hellojni;

/**
 * Created by fangc on 2016/6/7.
 用此类来生成c++的头文件，以此为c++源文件的创建做准备*/
public class JniBridge {

    public native static String getString();//2、创建与配置NDK项目:在java语言中加native后，代表抽象方法，由c/c++的代码来实现。
    // 在terminal中javah -d ../jni/ -jni com.jikexueyuan.hellojni.JniBridge 其含义：javah -d ../jni/ 为指定输出目录为到父级目录为jni下；
    // -jni com.jikexueyuan.hellojni.JniBridge： 生成 JNI 样式的标头文件 (默认值)，文件名为 com.jikexueyuan.hellojni.JniBridge。

    public native String getStr();

    public native int add(int a,int b);

    static {//3、运行NDK项目
        System.loadLibrary("hellojni");    }//静态模块加载名字为"hellojni"的本地库，既是build.gradle 中的ndk的配置中的moduleName
}
