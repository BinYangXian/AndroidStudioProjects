//
// Created by fangc on 2016/6/7.//2、创建与配置NDK项目需要此空文件。
// 在此实现同名文件中的Java_com_jikexueyuan_hellojni_JniBridge_getString函数：
//3、运行NDK项目：
#include "com_jikexueyuan_hellojni_JniBridge.h"
/*
* Class:     com_jikexueyuan_hellojni_JniBridge
* Method:    getString
* Signature: ()Ljava/lang/String;
*/
/*参数1.是JNI环境对象；参数2.为类（* Class: com_jikexueyuan_hellojni_JniBridge）的定义*/
jstring JNICALL Java_com_jikexueyuan_hellojni_JniBridge_getString
        (JNIEnv *env, jclass){
    return env->NewStringUTF("Static Method call,hello NDK"); //如果直接写"hello NDK"是c++的字符串，而需要java（其运行在jvm虚拟机中），
    // 则需要使用jni的环境来创建java语法的字符串。
};

/*
 * Class:     com_jikexueyuan_hellojni_JniBridge 江由皮丁
 * Method:    getStr
 * Signature: ()Ljava/lang/String;
 */
jstring JNICALL Java_com_jikexueyuan_hellojni_JniBridge_getStr
        (JNIEnv *env, jobject){
    return env->NewStringUTF("Method call");
};//将此函数拷贝到c++的源文件中去实现，参数2.为当前类（* Class: com_jikexueyuan_hellojni_JniBridge）实例的this指针

/*
 * Class:     com_jikexueyuan_hellojni_JniBridge
 * Method:    add
 * Signature: (II)I
 */
jint JNICALL Java_com_jikexueyuan_hellojni_JniBridge_add
        (JNIEnv *, jobject, jint a, jint b){
    return a+b;
};//将此函数拷贝到c++的源文件中去实现，参数2.为当前类（* Class: com_jikexueyuan_hellojni_JniBridge）实例的this指针；参数3.jint其实就是int