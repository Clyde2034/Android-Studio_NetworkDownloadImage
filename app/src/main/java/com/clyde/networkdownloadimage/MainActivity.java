package com.clyde.networkdownloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button btn_get;
    private EditText ed_url;
    private ImageView iv_image;
    private ProgressBar progressbar;
    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_get = findViewById(R.id.btn_get);
        ed_url = findViewById(R.id.ed_url);
        iv_image = findViewById(R.id.iv_image);
        progressbar=findViewById(R.id.progressbar);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL = ed_url.getText().toString();
                if (!URL.isEmpty()) {
                    progressbar.setVisibility(View.VISIBLE);
                    new DownloadImage(URL, iv_image).start();
                }
            }
        });
    }

    class DownloadImage extends Thread {
        String url;
        ImageView imageView;

        public DownloadImage(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            //Backend event
            try {
                InputStream inputStream = new URL(url).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI event
                        //After Backend event finish,UI event will execute.
                        imageView.setImageBitmap(bitmap);
                        progressbar.setVisibility(View.GONE);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}