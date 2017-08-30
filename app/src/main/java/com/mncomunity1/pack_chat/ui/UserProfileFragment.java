package com.mncomunity1.pack_chat.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mncomunity1.R;
import com.mncomunity1.pack_chat.model.Configuration;
import com.mncomunity1.pack_chat.model.User;
import com.mncomunity1.pack_chat.model.User2;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import siclo.com.ezphotopicker.api.EZPhotoPick;
import siclo.com.ezphotopicker.api.EZPhotoPickStorage;
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig;
import siclo.com.ezphotopicker.api.models.PhotoSource;

import static android.app.Activity.RESULT_OK;


public class UserProfileFragment extends AppCompatActivity {
    TextView tvUserName;
    CircleImageView avatar;

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String userIdM;
    String email;
    String name;

    private List<Configuration> listConfig = new ArrayList<>();
    private List<User2> listuser = new ArrayList<>();
    private RecyclerView recyclerView;


    private static final String USERNAME_LABEL = "Username";
    private static final String EMAIL_LABEL = "Email";
    private static final String SIGNOUT_LABEL = "Sign out";
    private static final String RESETPASS_LABEL = "Change Password";

    private static final int PICK_IMAGE = 1994;
    private LovelyProgressDialog waitingDialog;

    private DatabaseReference userDB;
    private FirebaseAuth mAuth;
    private User myAccount;
    private Context context;
    private String urlProfile;

    private TextView txt_name;
    private TextView txt_email;

    UserAdapter userAdapter;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://pmii-60fd1.appspot.com");

    Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_info);
    
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userIdM = sp.getString("userId", "000");
        email = sp.getString("email", "no@email");
        name = sp.getString("name", "noname");
        Log.e("userIdM", userIdM);
        userDB = FirebaseDatabase.getInstance().getReference().child("user").child(userIdM);
        mAuth = FirebaseAuth.getInstance();


        context = getApplicationContext();
        avatar = (CircleImageView) findViewById(R.id.img_avatar);
        tvUserName = (TextView) findViewById(R.id.tv_username);

        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_email = (TextView) findViewById(R.id.txt_email);

        recyclerView = (RecyclerView) findViewById(R.id.info_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);


        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


//                listConfig.clear();
                myAccount = dataSnapshot.getValue(User.class);

                Log.e("email", myAccount.email.toString());

                User2 i = new User2();
                i.setName(myAccount.name.toString());
                i.setEmail(myAccount.email);
                listuser.add(i);

                txt_name.setText(myAccount.name);
                txt_email.setText(myAccount.email);
                urlProfile = myAccount.avata;

                userAdapter = new UserAdapter(getApplicationContext(), listuser);
                userAdapter.notifyDataSetChanged();

                if (urlProfile.equals("default")) {

                } else {
                    Glide.with(context)
                            .load(urlProfile)
                            .centerCrop()
                            .crossFade()
                            .into(avatar);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogOne();
            }
        });


     
    }

    private void sendImage(Bitmap bitmap) {
        DatabaseReference databaseReference = userDB.push();
        String key = databaseReference.getKey();
        uploadFileToStorage(databaseReference, bitmap, key);
    }

    private void uploadFileToStorage(final DatabaseReference databaseReference, Bitmap bitmap, String filename) {
        StorageReference childRef = storageRef.child("user/" + filename + ".jpg");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        UploadTask uploadTask = childRef.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                onUploadPhotoSuccess(databaseReference, taskSnapshot);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onUploadPhotoFailure();
            }
        });


    }

    @SuppressWarnings("VisibleForTests")
    private void onUploadPhotoSuccess(DatabaseReference databaseReference, UploadTask.TaskSnapshot taskSnapshot) {

        Uri downloadUrl = taskSnapshot.getDownloadUrl();

        Log.e("downloadUrl", downloadUrl.toString());

        User newUser = new User();
        newUser.userId = userIdM;
        newUser.email = email;
        newUser.name = name;
        newUser.avata = downloadUrl.toString();
        FirebaseDatabase.getInstance().getReference().child("user/" + userIdM).setValue(newUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {
                Bitmap pickedPhoto = new EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap();
                avatar.setImageBitmap(pickedPhoto);
                sendImage(pickedPhoto);
            } catch (IOException e) {
                e.printStackTrace();
                onUploadPhotoFailure();
            }
        }
        if (requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {
                Bitmap pickedCamera = new EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap();
                avatar.setImageBitmap(pickedCamera);
                sendImage(pickedCamera);
            } catch (IOException e) {
                e.printStackTrace();
                onUploadPhotoFailure();
            }
        }
    }

    private void onUploadPhotoFailure() {

        Toast.makeText(getApplicationContext(), "อัพโหลดภาพไมไ่ด้", Toast.LENGTH_SHORT).show();
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
    public void onDestroy() {
        super.onDestroy();
    }



}
