package com.jikexueyuan.bluetoothchat_1051;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fangc on 2016/4/20.
 * 为了更高的定制化，自定义该adapter
 */
public class DevicesListAdapter extends BaseAdapter {

    private final Context context;
    private final int cellResId;
    private List<BluetoothDeviceWrapper> items = new ArrayList<>();

    public DevicesListAdapter(Context context, int cellResId) {
        this.context = context;
        this.cellResId = cellResId;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){  //列表优化
            convertView= LayoutInflater.from(context).inflate(cellResId,null);
        }
        assert convertView instanceof TextView; //只当convertView为TextView时候才做下述处理。
        ((TextView) convertView).setText(getItem(position).toString());//此处重写了BluetoothDeviceWrapper中的toString()
        return convertView;
    }

    public void add(BluetoothDeviceWrapper bluetoothDeviceWrapper) {
        if (!items.contains(bluetoothDeviceWrapper)) {//每一次传进来的都是新创建的蓝牙设备包裹，而在此扫描并判断的时候
            // 会发生重复扫描后添加的情况，所以需要重写contains会执行传入对象(bluetoothDeviceWrapper)中的equals方法，
            // 所以通过重写其equals方法，通过判断MAC地址是否相等，来过滤需要添加的设备!!
            items.add(bluetoothDeviceWrapper);
            notifyDataSetChanged();  //更新数据地方法
        }
    }
}
