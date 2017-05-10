package com.anaptec.genti.ucrypt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by genti on 5/1/2017.
 */

public class SmsReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                str += "SMS from " + msgs[i].getOriginatingAddress();
                str += " :";
                str += msgs[i].getMessageBody().toString();
            }
            //---display the new SMS message---
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
        Intent broadCastIntent = new Intent();
        broadCastIntent.setAction("SMS_RECEIVED_ACTION");
        broadCastIntent.putExtra("message",str);
        context.sendBroadcast(broadCastIntent);
    }
}