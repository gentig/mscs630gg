package com.anaptec.genti.ucrypt;

import android.support.v4.util.LogWriter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.id.input;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editText, editText2, textMsg;
    ArrayList<String> smsMessagesList = new ArrayList<>();
    ListView messages;
    ArrayAdapter arrayAdapter;
    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;
    IntentFilter intentFilter;
    private String hexKey = "5468617473206D79204B756E67204675";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
            }
        }else{
            //do nothing
        }

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editText.getText().toString();
                String sms = editText2.getText().toString();
                //Encrypt message
                AESCipher asCph = new AESCipher();
                asCph.init(hexKey,sms);
                sms = asCph.getMessage();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, sms, null, null);
                    Toast.makeText(MainActivity.this, "Sent!", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView inText = (TextView) findViewById(R.id.textMsg);
            inText.setText(intent.getExtras().getString("message"));
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int [] grantResults){
        switch (requestCode){
            case 1:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"Permissions granted!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"No permissions granted!",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onResume(){
        registerReceiver(intentReceiver,intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause(){
        unregisterReceiver(intentReceiver);
        super.onPause();
    }
}
