package com.jikexueyuan.jikecontactsreview;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    private ListView listView;
    private String name,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showContactsList();
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        clickItemAction();
    }

    private void clickItemAction() {
        listView= (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this).setTitle(R.string.choice).
                        setItems(new String[]{getString(R.string.phone), getString(R.string.send_messages)}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String realNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                if (which == 0) {
//                            Uri uri = Uri.parse("tel:" + ((PhoneInfo) adapter.getItem(position)).getNumber());
//                            Intent intentCall = new Intent(Intent.ACTION_DIAL, uri);
//                            startActivity(intentCall);
                                    //显式intent：明确指定目标页面内，然后定向的跳转过去。
                                    //隐式intent：没有明确的目标，需要通过设置setAction或者categray等，
                                    // 让系统自动的匹配到对应的类，然后进行相应的跳转；跳转的过程可以用setData传递所需数据。
                                    Intent intentCall = new Intent();
                                    intentCall.setAction(Intent.ACTION_DIAL);
                                    intentCall.setData(Uri.parse("tel:" + realNumber));
                                    startActivity(intentCall);
                                } else if (which == 1) {
//                            Intent intentSend = new Intent(Intent.ACTION_VIEW);
//                            intentSend.setType("vnd.android-dir/mms-sms");
//                            intentSend.setData(Uri.parse(("content://mms-sms/" + ((PhoneInfo) adapter.getItem(position)).getNumber()) + "/"));//此为号码
                                    Intent intentSend = new Intent();
                                    intentSend.setAction(Intent.ACTION_SENDTO);

                                    intentSend.setData(Uri.parse("smsto:" + realNumber));
                                    startActivity(intentSend);
                                }
                            }
                        }).setPositiveButton(R.string.add_cancel, null).show();
            }
        });
    }

    private void showContactsList() {
        setContentView(R.layout.activity_main);
        cursor = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        name = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        number = ContactsContract.CommonDataKinds.Phone.NUMBER;
        adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.add_cell, cursor, new String[]{name, number}, new int[]{R.id.name, R.id.number});
        setListAdapter(adapter);
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = View.inflate(MainActivity.this, R.layout.dialog_cell, null);
        final EditText et_name = (EditText) view.findViewById(R.id.edit_name);
        final EditText et_phone = (EditText) view.findViewById(R.id.edit_num);

        builder.setView(view)
                .setTitle(R.string.add_title).setPositiveButton(R.string.add_cancel, null)
                .setNegativeButton(R.string.add_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = new ContentValues();
                        // 向RawContacts.CONTENT_URI执行一个空值插入，
                        // 目的是获取系统返回的rawContactId，可以换个更好的方法不？？？？不能！每次点添加，不输入，后确定也会加一个无名氏?是因为下边没有判断
                        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
                        long rawContactId = ContentUris.parseId(rawContactUri);
                        values.clear();

                        if (!et_name.getText().toString().trim().equals("")) {  //若输入不为空
                            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                            // 设置内容类型
                            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                            // 设置联系人名字
                            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, et_name.getText().toString());
                            // 向联系人URI添加联系人名字
                            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
                            values.clear();
                        }

                        if (!et_phone.getText().toString().trim().equals("")) {

                            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                            // 设置电话类型
                            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
//                            values.put(Phone.TYPE, Phone.TYPE_MOBILE);  此条可省略  搬砖所得
                            // 设置联系人的电话号码
                            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, et_phone.getText().toString());
                            // 向联系人电话号码URI添加电话号码
                            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
                            values.clear();
                        }
                        refreshView();
                    }
                }).show();
    }

    private void refreshView() {
        cursor = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        adapter.changeCursor(cursor);
    }
}
