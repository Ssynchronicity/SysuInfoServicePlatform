package com.example.song.sysuinfoserviceplatform.club;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.song.sysuinfoserviceplatform.R;
import com.example.song.sysuinfoserviceplatform.activity.RequestInterface;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplyActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PICK_IMAGE = 3;
    String imagePath;
    String actname = "";
    String acttype="";
    String actdetail="";
    String actbonus="";
    String actorganiser="";
    String acttime="";
    String actplace="";
    int hirenum= 0;
    String hireway="";
    String hirelink="";
    int actstate=0;
    String actslogan="";
    String actpic="";
    int actvalid=0;
    int picFlag = 0;
    final globalClub app = (globalClub)getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        Button submit = (Button)findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                EditText type = (EditText)findViewById(R.id.type);
                acttype = type.getText().toString();
                EditText name = (EditText)findViewById(R.id.name);
                actname = name.getText().toString();
                EditText slogan = (EditText)findViewById(R.id.slogan);
                actslogan = slogan.getText().toString();
                EditText detail = (EditText)findViewById(R.id.detail);
                actdetail = detail.getText().toString();
                EditText time = (EditText)findViewById(R.id.time);
                acttime = time.getText().toString();
                EditText bonus = (EditText)findViewById(R.id.bonus);
                actbonus = bonus.getText().toString();
                EditText organiser = (EditText)findViewById(R.id.organiser);
                actorganiser = organiser.getText().toString();
                EditText place = (EditText)findViewById(R.id.place);
                actplace = place.getText().toString();
                EditText num = (EditText)findViewById(R.id.hirenum);
                if(!num.getText().toString().equals(""))
                    hirenum = Integer.parseInt(num.getText().toString());
                EditText way = (EditText)findViewById(R.id.hireway);
                hireway = way.getText().toString();
                EditText link = (EditText)findViewById(R.id.hirelink);
                hirelink = link.getText().toString();

                if(!acttype.equals("") && !actname.equals("")&&!actslogan.equals("")
                        &&!actdetail.equals("")&&!acttime.equals("")&&!actbonus.equals("")
                        &&!actorganiser.equals("")&&!actplace.equals("")&&!hireway.equals("")
                        &&!hirelink.equals("")){
                    if(picFlag == 0){
                        Toast.makeText(ApplyActivity.this, "图片未成功上传", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        actpic += actname;
                        submit();
                    }
                }

                else{ Toast.makeText(ApplyActivity.this, "必须填写所有项并上传图片", Toast.LENGTH_SHORT).show();}
            }
        });
        Button logoButt = (Button)findViewById(R.id.photo_button);
        logoButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText name = (EditText)findViewById(R.id.name);
                actname = name.getText().toString();
                if(!actname.equals("")){
                    if (ContextCompat.checkSelfPermission(ApplyActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions(ApplyActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_PICK_IMAGE);

                    }else {
                        openAlbum();

                    }
                }else{
                    Toast.makeText(ApplyActivity.this, "必须填写活动名", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == REQUEST_CODE_PICK_IMAGE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openAlbum();
            } else
            {
                // Permission Denied
                Toast.makeText(ApplyActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    void openAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            imagePath = c.getString(columnIndex);
            if(imagePath!=null) actpic="http://172.18.217.143:8000/getpic?name=";
            showImage(imagePath);
            uploadPic();
            c.close();
        }
    }
    //加载图片
    private void showImage(String imagePath){
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ((ImageView)findViewById(R.id.actpic)).setImageBitmap(bitmap);

    }
    public boolean onOptionsItemSelected(MenuItem item) {//点击back键finish当前activity
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void submit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = request.applyActivity(actname,acttype,actdetail,actbonus,
                actorganiser, acttime, actplace, hirenum, hireway,hirelink,actstate,actslogan,actpic,actvalid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String message = response.message();
                if(response.message().equals("OK")) {
                    message = "申请已提交";
                    Toast.makeText(ApplyActivity.this, message,Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(ApplyActivity.this, message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Network request error", "Error! Network request error. ");
                Toast.makeText(ApplyActivity.this, "网络异常，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void uploadPic(){
        File image = new File(imagePath);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpg"), image);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", image.getName(), requestFile);
        Call<ResponseBody> call2 = request.uploadPic(actname,  body);
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String message = response.message();
                if(response.message().equals("OK")) {
                    if (true) picFlag = 1;
                    message = "图片已成功上传";
                }
                Toast.makeText(ApplyActivity.this, message,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Network request error", "Error! Network request error. ");
                Toast.makeText(ApplyActivity.this, "错误"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    private OkHttpClient getOkHttpClient() {
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("获取","OkHttp====Message:"+message);
            }
        });

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return okHttpClient;
    }
}
