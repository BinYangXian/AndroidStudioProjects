package com.jikexueyuan.messageblockingreview;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by fangc on 2016/2/28.
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null)
            return;

        Object[] pdus = (Object[]) extras.get("pdus");
        SmsMessage message = null;
        ContentValues cv = new ContentValues();
        for (Object pdu : pdus) {
            message = SmsMessage.createFromPdu((byte[]) pdu);
        }
    }
}
