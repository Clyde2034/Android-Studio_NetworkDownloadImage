# Android-Studio_NetworkDownloadImage
This project is used to Download the image from internet by Inputstream and BitmapFactory.decode

>API Require
>
>>(None)

>UI and Java Code
>>UI：activity_main.xml
>>Java：MainActivity.java

<img align="left" src="https://user-images.githubusercontent.com/41913354/160986984-75dc8c45-d65d-4f29-ac46-dfb0b70b9307.png" width="250"/>
<img align="center" src="https://user-images.githubusercontent.com/41913354/160987844-2f585086-aaba-46af-8d28-f212c0b09a9f.png" width="250"/>


## AndroidManifest.xml
```
    //...
    <uses-permission android:name="android.permission.INTERNET"/>
    
    <application>
    //...
    </application>   
-------------------------------------------------------------------------------------------
dependencies {
    //...
}  
```

## activity_main.xml
```
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/ed_url"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="30dp"
        android:layout_marginTop="50dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btn_get"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="50dp"
        android:text="GET"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/ed_url" />

    <ImageView
        android:id="@+id/iv_image"
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:layout_marginTop="80dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/btn_get" />

    <ProgressBar
        android:visibility="gone"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:id="@+id/progressbar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

## MainActivity.java
```
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

    //Network Download,usind thread to handle
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
```
