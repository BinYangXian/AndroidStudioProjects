package com.zhanghao.socket;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText ip,dia;
    TextView textView;
    Button con,send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        con.setOnClickListener(this);
        send.setOnClickListener(this);

    }

    private void init() {
        ip = (EditText) findViewById(R.id.ip);
        dia = (EditText) findViewById(R.id.dialog);
        textView = (TextView) findViewById(R.id.text);
        con = (Button) findViewById(R.id.btn_con);
        send = (Button) findViewById(R.id.btn_send);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_con:
                connect();
                break;
            case R.id.btn_send:
                sendMessage();
                break;
        }
    }

    Socket socket = null;
    BufferedReader br = null;
    BufferedWriter bw = null;


    private void connect() {
            String ips = ip.getText().toString();
            AsyncTask<String,String,Void> read = new AsyncTask<String, String, Void>() {
                @Override
                protected Void doInBackground(String... params) {
                    try {
                        socket = new Socket(params[0],12345);
                        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        publishProgress("@success");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                    String line;
//                        publishProgress(br.readLine());
//                        System.out.println(br.readLine()+"----------------");
                        while ((line = br.readLine()) != null){
                            publishProgress(line);
//                            System.out.println(line + "----------------while");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(String... values) {
                    if (values[0].equals("@success")){
                        Toast.makeText(MainActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
                    }
                    textView.append("别人说："+ values[0]+"\n");
                    super.onProgressUpdate(values);
                }
            };
            read.execute(ips);
    }

    private void sendMessage() {
        try {
            textView.append("我说："+dia.getText().toString()+"\n");
            bw.write(dia.getText().toString() + "\n");
            bw.flush();
            dia.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
