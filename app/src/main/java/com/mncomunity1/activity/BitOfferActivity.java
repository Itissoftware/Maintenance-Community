package com.mncomunity1.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mncomunity1.R;
import com.mncomunity1.adapter.CategorySpareRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.Category;
import com.mncomunity1.model.Result;
import com.mncomunity1.model.getOrder;
import com.mncomunity1.service.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import ir.hamsaa.RtlMaterialSpinner;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import siclo.com.ezphotopicker.api.EZPhotoPick;
import siclo.com.ezphotopicker.api.EZPhotoPickStorage;
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig;
import siclo.com.ezphotopicker.api.models.PhotoSource;


public class BitOfferActivity extends AppCompatActivity {
    EZPhotoPickStorage ezPhotoPickStorage;

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Toolbar toolbar;
    String imagePath;

    EditText ed_name;
    EditText ed_number;
    EditText ed_details;
    Button btn_post;

    ImageView img_add;
    Spinner spinner;

    ArrayList<String> list = new ArrayList<>();

    String userId;

    Bitmap pickedPhoto;
    Bitmap pickedCamera;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bit_offer_activity);




        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_number = (EditText) findViewById(R.id.ed_number);
        ed_details = (EditText) findViewById(R.id.ed_details);
        img_add = (ImageView) findViewById(R.id.img_add);
        ezPhotoPickStorage = new EZPhotoPickStorage(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_post = (Button) findViewById(R.id.btn_post);


        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        userId = sp.getString("userId", "000");

        toolbar.setTitle("หาสินค้าที่ต้องการ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogOne();
            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(ed_name.getText().toString()) || TextUtils.isEmpty(ed_number.getText().toString()) || TextUtils.isEmpty(ed_details.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "กรุณาใส่ข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                } else {

                    if(imagePath != null){

                        uploadImage();
                    }else{

                        uploadText(ed_name.getText().toString(),userId,ed_details.getText().toString(),"",ed_number.getText().toString());
                    }




                }


            }
        });


    }

    private void showAlertDialogOne() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("เลือกรูปภาพ")
                .setPositiveButton("แกลอรี่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do something on Share
                        chooseImage();
                    }
                })
                .setNegativeButton("กล้อง", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do something on Cancel
                        chooseCamera();
                    }
                });
        builder.show();
    }

    private void uploadImage() {

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(BitOfferActivity.this);
        progressDialog.setMessage("กำลังอัพโหลด");
        progressDialog.show();

        //Create Upload Server Client
        APIService service = ApiClient.getApiService();

        //File creating from selected URL
        File file = new File(imagePath);


        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        String tile = ed_name.getText().toString();
        RequestBody titles = RequestBody.create(MediaType.parse("multipart/form-data"), tile);

        String cate = "4";
        RequestBody category = RequestBody.create(MediaType.parse("multipart/form-data"), cate);

        String detail = ed_details.getText().toString();
        RequestBody details = RequestBody.create(MediaType.parse("multipart/form-data"), detail);


        String user = userId;
        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), user);

        String count = ed_number.getText().toString();
        RequestBody counts = RequestBody.create(MediaType.parse("multipart/form-data"), count);


        Call<Result> resultCall = service.uploadImage(body, titles, userId, details, category, counts);
//        Call<Result> resultCall = service.uploadImage(body);

        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("success")) {
                        Toast.makeText(getApplicationContext(), "โพสสำเร็จ", Toast.LENGTH_SHORT).show();
                        String id = response.body().getId();
                        String title = response.body().getTitle();

                        ed_name.setText("");
                        ed_number.setText("");
                        ed_details.setText("");


                        pickedPhoto = null;
                        pickedCamera = null;

                        Intent i = new Intent(getApplicationContext(), BitItemListVendorActivity.class);
                        i.putExtra("title", title);
                        i.putExtra("amount", ed_number.getText().toString());
                        i.putExtra("id", id);
                        startActivity(i);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "โพสสำเร็จไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                    }

                    imagePath = "";

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {


                Log.e("getMessage", t.getMessage());
                Log.e("getMessage", t.getLocalizedMessage());

            }
        });


    }


    private void chooseImage() {
        EZPhotoPickConfig config = new EZPhotoPickConfig();
        config.photoSource = PhotoSource.GALLERY;
        config.exportingSize = 900;
        EZPhotoPick.startPhotoPickActivity(this, config);
    }

    private void chooseCamera() {
        EZPhotoPickConfig config = new EZPhotoPickConfig();
        config.photoSource = PhotoSource.CAMERA;
        config.exportingSize = 900;
        EZPhotoPick.startPhotoPickActivity(this, config);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {
                pickedPhoto = new EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap();

                Uri uri = getImageUri(this, pickedPhoto);

                Uri selectedImage = uri;
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);

                imagePath = picturePath;

                Log.e("PHOTO_PICK_CAMERA", picturePath);
                //  sendImage(pickedPhoto);
                img_add.setImageBitmap(pickedPhoto);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        if (requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {
                pickedCamera = new EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap();
                img_add.setImageBitmap(pickedCamera);
                Uri uri = getImageUri(this, pickedCamera);

                Uri selectedImage = uri;
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);

                imagePath = picturePath;
                // sendImage(pickedCamera);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private void uploadText(String title, String user_id, String details, String category, String count) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Result> userCall = service.uploadText(title, user_id, details, category, count);

        userCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {

                    if (response.body().getResult().equals("success")) {

                        Toast.makeText(getApplicationContext(), "โพสสำเร็จ", Toast.LENGTH_SHORT).show();
                        String id = response.body().getId();
                        String title = response.body().getTitle();

                        ed_name.setText("");
                        ed_number.setText("");
                        ed_details.setText("");


                        pickedPhoto = null;
                        pickedCamera = null;

                        Intent i = new Intent(getApplicationContext(), BitItemListVendorActivity.class);
                        i.putExtra("title", title);
                        i.putExtra("amount", ed_number.getText().toString());
                        i.putExtra("id", id);
                        startActivity(i);
                        finish();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "โพสไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                    }

                    imagePath = "";

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


}
