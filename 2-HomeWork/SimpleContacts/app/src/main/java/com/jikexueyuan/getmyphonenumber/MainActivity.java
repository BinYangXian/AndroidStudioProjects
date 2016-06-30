package com.jikexueyuan.getmyphonenumber;


import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;


public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private MyAdapter adapter;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        refreshContactsListView();  //刷新通讯录后显示
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showItemClickDialog(position);
            }
        });
    }

    private void showItemClickDialog(final int position) {//在方法中的匿名内部类中使用的参数，需要是final 常量类型
        new AlertDialog.Builder(this)//新建对话框
                .setTitle(R.string.select)
                .setItems(new String[]{getString(R.string.phone), getString(R.string.send_messages)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        String action = null;
                        String data = null;
                        if (which == 0) {
                            action = Intent.ACTION_DIAL;
                            data = "tel:" + ((PhoneInfo) adapter.getItem(position)).getNumber();
                              //显式intent：明确指定目标页面内，然后定向的跳转过去。
//                            //隐式intent：没有明确的目标，需要通过设置setAction或者categray等，
//                            //让系统自动的匹配到对应的类，然后进行相应的跳转；跳转的过程可以用setData传递所需数据。
                        } else if (which == 1) {
                            action = Intent.ACTION_SENDTO;
                            data = "smsto:" + ((PhoneInfo) adapter.getItem(position)).getNumber();
                        }
                        intent.setAction(action);
                        intent.setData(Uri.parse(data));
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void refreshContactsListView() {
        GetNumber.getNumber(this);//在当前context（上下文对象）中获取到SQlite中所有的：ContactsContract.CommonDataKinds.Phone.NUMBER
        //与ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME，且由lists封装好的PhoneInfo型集合。
        lv = (ListView) findViewById(R.id.lv);//定义一个ListView的引用lv
        adapter = new MyAdapter(GetNumber.lists, this);//创建一个adapter实例，指代：将集合中数据逐条适配到我的自定义适配器中。
        lv.setAdapter(adapter);//将适配器中的数据适配到ListView中显示出来
    }

    public void showAddDialog() {
        View view = View.inflate(this, R.layout.dialog_contact, null);  //加载dialog_contact到view（显示）对象中
        final EditText et_name = (EditText) view.findViewById(R.id.et_name); // 由于et_name是在dialog_contact资源里的view，需要指定view
        // 上行的view加载的就是dialog_contact。
        final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);
        new AlertDialog.Builder(this)
                .setTitle(R.string.add_contact)
                .setView(view)//将加载过的dialog_contact到view对象呈现出来
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 创建一个空的ContentValues
                        ContentValues values = new ContentValues();
                        // 向RawContacts.CONTENT_URI执行一个空值插入，
                        // 目的是获取系统返回的rawContactId，可以换个更好的方法不？？？？不能！每次点添加，不输入，后确定也会加一个无名氏?是因为下边没有判断
                        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
                        long rawContactId = ContentUris.parseId(rawContactUri);
                        values.clear();

                        if (!et_name.getText().toString().trim().equals("")) {  //若输入不为空
                            values.put(Data.RAW_CONTACT_ID, rawContactId);
                            // 设置内容类型
                            values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
                            // 设置联系人名字
                            values.put(StructuredName.GIVEN_NAME, et_name.getText().toString());
                            // 向联系人URI添加联系人名字
                            getContentResolver().insert(Data.CONTENT_URI, values);
                            values.clear();
                        }

                        if (!et_phone.getText().toString().trim().equals("")) {

                            values.put(Data.RAW_CONTACT_ID, rawContactId);
                            // 设置电话类型
                            values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
//                            values.put(Phone.TYPE, Phone.TYPE_MOBILE);  此条可省略  搬砖所得
                            // 设置联系人的电话号码
                            values.put(Phone.NUMBER, et_phone.getText().toString());
                            // 向联系人电话号码URI添加电话号码
                            getContentResolver().insert(Data.CONTENT_URI, values);
                            values.clear();
                        }

                        refreshContactsListView();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
