package com.boredream.borecontact;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.boredream.borecontact.LetterBar.OnLetterSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private LetterBar lb;
    private TextView tv_overlay;

    private Button btn_add;
    private PinnedSectionListView lv_contacts;

    private ContactAdapter adapter;
    private List<ContactBean> contacts = new ArrayList<ContactBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lb = (LetterBar) findViewById(R.id.lb);
        tv_overlay = (TextView) findViewById(R.id.tv_overlay);

        btn_add = (Button) findViewById(R.id.btn_add);
        lv_contacts = (PinnedSectionListView) findViewById(R.id.lv_contacts);
        lv_contacts.setShadowVisible(false);

        adapter = new ContactAdapter(this, contacts);
        lv_contacts.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();

                setContactsData();
            }
        });

        lv_contacts.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Object item = adapter.getItem(position);
                if (item instanceof ContactBean) {
                    ContactBean contact = (ContactBean) item;
                    showLongClickDialog(contact);
                }

            }
        });

        lv_contacts.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Object item = adapter.getItem(position);
                if (item instanceof ContactBean) {
                    ContactBean contact = (ContactBean) item;
                    ContactManager.deleteContact(MainActivity.this, contact);
                    setContactsData();
                }
                return true;
            }
        });

        lb.setOnLetterSelectedListener(new OnLetterSelectedListener() {

            @Override
            public void onLetterSelected(String letter) {
                if (TextUtils.isEmpty(letter)) {
                    tv_overlay.setVisibility(View.GONE);
                } else {
                    tv_overlay.setVisibility(View.VISIBLE);
                    tv_overlay.setText(letter);

                    int position = adapter.getLetterPosition(letter);
                    if (position != -1) {
                        lv_contacts.setSelection(position);
                    }
                }
            }
        });

        setContactsData();
    }

    private void showLongClickDialog(final ContactBean contact) {
        new AlertDialog.Builder(this)
                .setItems(new String[]{"����绰", "���Ͷ���", "�޸���ϵ��"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent intentCall = new Intent();
                                        intentCall.setAction(Intent.ACTION_CALL);
                                        intentCall.setData(Uri.parse("tel:" + contact.getPhone()));
                                        startActivity(intentCall);
                                        break;
                                    case 1:
                                        Intent intentSend = new Intent();
                                        intentSend.setAction(Intent.ACTION_SENDTO);
                                        intentSend.setData(Uri.parse("smsto://" + contact.getPhone()));
                                        startActivity(intentSend);
                                        break;
                                    case 2:
                                        showUpdateDialog(contact);

                                        setContactsData();
                                        break;

                                    default:
                                        break;
                                }
                            }
                        })
                .show();
    }

    private void setContactsData() {
        List<ContactBean> contactData = ContactManager.getContacts(this);
        contacts.clear();
        contacts.addAll(contactData);
        adapter.updateList();
    }

    private void showAddDialog() {
        View view = View.inflate(this, R.layout.dialog_contact, null);

        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);

        new AlertDialog.Builder(this)
                .setTitle("�����ϵ��")
                .setView(view)
                .setPositiveButton("ȷ��", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContactBean contact = new ContactBean();
                        contact.setName(et_name.getText() + "");
                        contact.setPhone(et_phone.getText() + "");

                        ContactManager.addContact(MainActivity.this, contact);
                        setContactsData();
                    }
                })
                .setNegativeButton("ȡ��", null)
                .show();
    }

    private void showUpdateDialog(final ContactBean oldContact) {
        View view = View.inflate(this, R.layout.dialog_contact, null);

        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);

        et_name.setText(oldContact.getName());
        et_phone.setText(oldContact.getPhone());

        new AlertDialog.Builder(this)
                .setTitle("�޸���ϵ��")
                .setView(view)
                .setPositiveButton("ȷ��", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContactBean contact = new ContactBean();
                        contact.setRawContactId(oldContact.getRawContactId());
                        contact.setName(et_name.getText() + "");
                        contact.setPhone(et_phone.getText() + "");

                        ContactManager.updateContact(MainActivity.this, contact);
                        setContactsData();
                    }
                })
                .setNegativeButton("ȡ��", null)
                .show();
    }
}
