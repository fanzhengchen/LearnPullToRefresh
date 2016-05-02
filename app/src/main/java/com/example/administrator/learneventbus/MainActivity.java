package com.example.administrator.learneventbus;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private final static String URL = "http://img03.tooopen.com/images/20131102/sy_45238929299.jpg";
    private EventBus eventBus = EventBus.getDefault();
    private Handler mHandler;

    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.server_response)
    EditText responseEditText;
    @BindView(R.id.image_view)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init("PTR");
        ButterKnife.bind(this);
        eventBus.register(this);
    }

    @Subscribe
    public void onEvent(final MessageEvent event) {
        final String msg = editText.getText().toString();
        Toast.makeText(this, event.toString() + " " + msg, Toast.LENGTH_LONG).show();
        final String ip = event.getIp();
        final int port = event.getPort();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Handler handler = new Handler(getMainLooper());
                try {
                    final Socket socket = new Socket(ip, port);
                    socket.setKeepAlive(true);
                    final InputStream inputStream = socket.getInputStream();
                    final InputReader reader = new InputReader(inputStream);
                    PrintWriter writer = new PrintWriter(socket.getOutputStream());
                    writer.println(msg);
                    writer.flush();

                    final StringBuilder builder = new StringBuilder();
                    while (reader.hasNext()) {
                        String text = reader.next();
                        builder.append(text);
                        builder.append(" ");
                    }
                    responseEditText.post(new Runnable() {
                        @Override
                        public void run() {
                            responseEditText.append(builder.toString());
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @OnClick(R.id.postEvent)
    public void postEvent() {
        eventBus.post(new MessageEvent("192.168.3.2", 20000));
    }


    @OnClick(R.id.download)
    public void downloadImage() {
        Toast.makeText(this, "download", Toast.LENGTH_LONG).show();
        Glide.with(this).load(URL).into(imageView);
    }

    @OnClick(R.id.image_view)
    public void clickImage() {
        Intent intent = new Intent(MainActivity.this, PullActivity.class);
        startActivity(intent);
    }

}
