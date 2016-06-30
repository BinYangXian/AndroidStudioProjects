package com.jikexueyuan.bluetoothchat_1051;

import android.bluetooth.BluetoothDevice;

/**
 * Created by fangc on 2016/4/21.
 */
public class BluetoothDeviceWrapper {
    private final BluetoothDevice device;

    public BluetoothDeviceWrapper(BluetoothDevice device) {
        this.device = device;
    }

    public BluetoothDevice getDevice() {
        return device;
    }


    @Override
    public boolean equals(Object o) {
        if (o != null) {
            assert o instanceof BluetoothDeviceWrapper;//o 必须是BluetoothDeviceWrapper类型.(由于我们是重写
            //的 equals方法，没法规定传进参数的类型，所以这样使程序的结构与流程更清晰，看起来也更清晰)
            return ((BluetoothDeviceWrapper) o).getDevice().getAddress().equals(getDevice().getAddress());//这里
            // 就是equals方法引用处提到：通过MAC地址来判断是否已经存在!!
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", getDevice().getName(), getDevice(), getDevice().getAddress());
    }
}
