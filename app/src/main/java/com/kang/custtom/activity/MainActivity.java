package com.kang.custtom.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import com.communication.server.constant.Constant;
import com.communication.server.handler.ServerAcceptor;
import com.communication.server.httpd.NanoHTTPd;
import com.kang.custtom.R;
import com.kang.custtom.service.MinaClient;
import com.kang.custtom.service.MinaServer;

public class MainActivity extends AppCompatActivity{
    private final String TAG = "MainActivity";
    private NanoHTTPd na;
    private Intent mIntent;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;


        //mina port 8081
        Button startServer = findViewById(R.id.startServer);
        startServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(mContext, MinaServer.class);
                startService(mIntent);
            }
        });
        Button stopServer = findViewById(R.id.stopServer);
        stopServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MinaServer.getInstance().stopServer();
                mIntent = new Intent(mContext, MinaServer.class);
                stopService(mIntent);
            }
        });

        Button startClient = findViewById(R.id.startClient);
        startClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(mContext, MinaClient.class);
                startService(mIntent);
            }
        });
        Button stopClient = findViewById(R.id.stopClient);
        stopClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MinaClient.getInstance().stopClient();
                mIntent = new Intent(mContext, MinaClient.class);
                stopService(mIntent);

            }
        });

        //httpd port 8080
        Button startHttpd = findViewById(R.id.startHttpd);
        startHttpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(na == null){
                        na = new NanoHTTPd(Constant.HTTPD_PORT);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "start com.communication.server.httpd");
            }
        });

        Button stopHttpd = findViewById(R.id.stopHttpd);
        stopHttpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(na != null){
                    na.stop();
                }
                Log.i(TAG, "stop com.communication.server.httpd");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity onDestroy:");
        MinaServer.getInstance().stopServer();
        MinaClient.getInstance().stopClient();
    }
}

