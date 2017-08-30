package com.mncomunity1.pack_chat.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mncomunity1.R;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.PostOrder;
import com.mncomunity1.pack_chat.data.SharedPreferenceHelper;
import com.mncomunity1.pack_chat.data.StaticConfig;
import com.mncomunity1.pack_chat.model.Consersation;
import com.mncomunity1.pack_chat.model.Message;
import com.mncomunity1.service.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import siclo.com.ezphotopicker.api.EZPhotoPick;
import siclo.com.ezphotopicker.api.EZPhotoPickStorage;
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig;
import siclo.com.ezphotopicker.api.models.PhotoSource;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerChat;
    public static final int VIEW_TYPE_USER_MESSAGE = 0;
    public static final int VIEW_TYPE_FRIEND_MESSAGE = 1;
    private ListMessageAdapter adapter;
    private String roomId;
    private ArrayList<CharSequence> idFriend;
    private Consersation consersation;
    private ImageButton btnSend;
    private EditText editWriteMessage;
    private LinearLayoutManager linearLayoutManager;
    public static HashMap<String, Bitmap> bitmapAvataFriend;
    public Bitmap bitmapAvataUser;
    public Toolbar toolbar;

    String userId;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String email;
    Button btn_select;
    String nameFriend;

    private DatabaseReference messageChatDatabase;
    private StorageReference storageReference;

    String UID;
    String UIDFRIEND;
    String USERIDFRIEND;

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        btn_select = (Button) findViewById(R.id.btn_select);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userId = sp.getString("userId", "000");

        Intent intentData = getIntent();
        idFriend = intentData.getCharSequenceArrayListExtra(StaticConfig.INTENT_KEY_CHAT_ID);
        roomId = intentData.getStringExtra(StaticConfig.INTENT_KEY_CHAT_ROOM_ID);
        nameFriend = intentData.getStringExtra(StaticConfig.INTENT_KEY_CHAT_FRIEND);
        USERIDFRIEND = intentData.getStringExtra("USERIDFRIEND");
        email = intentData.getStringExtra("email_friend");
        UID = intentData.getStringExtra("UID");

        Log.e("UID",UID+" : "+ UIDFRIEND+"");
        Log.e("roomId",roomId+"");


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        consersation = new Consersation();
        btnSend = (ImageButton) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        String base64AvataUser = SharedPreferenceHelper.getInstance(this).getUserInfo().avata;
//        if (!base64AvataUser.equals(StaticConfig.STR_DEFAULT_BASE64)) {
//            byte[] decodedString = Base64.decode(base64AvataUser, Base64.DEFAULT);
//            bitmapAvataUser = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        } else {
//            bitmapAvataUser = null;
//        }

        editWriteMessage = (EditText) findViewById(R.id.editWriteMessage);
        if (idFriend != null && nameFriend != null) {
//            getSupportActionBar().setTitle(nameFriend);
            toolbar.setTitle(nameFriend);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerChat = (RecyclerView) findViewById(R.id.recyclerChat);
            recyclerChat.setLayoutManager(linearLayoutManager);
            adapter = new ListMessageAdapter(this, consersation, bitmapAvataFriend, bitmapAvataUser,userId);
            FirebaseDatabase.getInstance().getReference().child("Message/" + roomId).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getValue() != null) {
                        HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                        Message newMessage = new Message();
                        newMessage.idSender = (String) mapMessage.get("idSender");
                        newMessage.idReceiver = (String) mapMessage.get("idReceiver");
                        newMessage.text = (String) mapMessage.get("text");
                        newMessage.type = (String) mapMessage.get("type");
                       // newMessage.timestamp = (long) mapMessage.get("timestamp");
                        consersation.getListMessageData().add(newMessage);
                        adapter.notifyDataSetChanged();
                        linearLayoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            recyclerChat.setAdapter(adapter);
        }

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogOne();
            }
        });

        messageChatDatabase = FirebaseDatabase.getInstance().getReference().child("Message/" + roomId);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent result = new Intent();
            result.putExtra("idFriend", idFriend.get(0));
            setResult(RESULT_OK, result);
            this.finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent result = new Intent();
        result.putExtra("idFriend", idFriend.get(0));
        setResult(RESULT_OK, result);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSend) {
            String content = editWriteMessage.getText().toString().trim();
            if (content.length() > 0) {
                editWriteMessage.setText("");
                Message newMessage = new Message();
                newMessage.text = content;
                newMessage.type = Message.TYPE_TEXT;
                newMessage.idSender = userId;
                newMessage.idReceiver = USERIDFRIEND;
                newMessage.timestamp = System.currentTimeMillis();
                FirebaseDatabase.getInstance().getReference().child("Message").child(roomId).push().setValue(newMessage);

                sendChat(email, content, roomId,nameFriend);
            }
        }
    }

    public void sendChat(String id, String msg, String roomId,String name) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostOrder> userCall = service.postMsgChat(id, msg, roomId,name);

        userCall.enqueue(new Callback<PostOrder>() {
            @Override
            public void onResponse(Call<PostOrder> call, Response<PostOrder> response) {


            }

            @Override
            public void onFailure(Call<PostOrder> call, Throwable t) {

            }
        });
    }


    private void sendImage(Bitmap bitmap) {
        DatabaseReference databaseReference = messageChatDatabase.push();
        String key = databaseReference.getKey();
        uploadFileToStorage(databaseReference, bitmap, key);
    }

    private void uploadFileToStorage(final DatabaseReference databaseReference, Bitmap bitmap, String filename) {
        StorageReference photoReference = storageReference.child("chat/"+filename + ".jpg");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = photoReference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                onUploadPhotoSuccess(databaseReference, taskSnapshot);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                onUploadPhotoFailure();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {
                Bitmap pickedPhoto = new EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap();
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
                sendImage(pickedCamera);
            } catch (IOException e) {
                e.printStackTrace();
                onUploadPhotoFailure();
            }
        }
    }

    @SuppressWarnings("VisibleForTests")
    private void onUploadPhotoSuccess(DatabaseReference databaseReference, UploadTask.TaskSnapshot taskSnapshot) {

        Uri downloadUrl = taskSnapshot.getDownloadUrl();
        Message newMessage = new Message();
        newMessage.text = downloadUrl.toString();
        newMessage.type = Message.TYPE_IMAGE;
        newMessage.idSender = userId;
        newMessage.idReceiver = USERIDFRIEND;
        newMessage.timestamp = System.currentTimeMillis();
        FirebaseDatabase.getInstance().getReference().child("Message/" + roomId).push().setValue(newMessage);

        sendChat(email, "รูปภาพ", roomId,nameFriend);
        editWriteMessage.setText("");
    }


    private void onUploadPhotoFailure() {

        Toast.makeText(ChatActivity.this, "อัพโหลดภาพไมไ่ด้", Toast.LENGTH_SHORT).show();
    }
}

class ListMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Consersation consersation;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private String userId;

    public ListMessageAdapter(Context context, Consersation consersation, HashMap<String, Bitmap> bitmapAvata, Bitmap bitmapAvataUser,String userId) {
        this.context = context;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataUser = bitmapAvataUser;
        bitmapAvataDB = new HashMap<>();
        this.userId = userId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ChatActivity.VIEW_TYPE_FRIEND_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend, parent, false);
            return new ItemMessageFriendHolder(view);
        } else if (viewType == ChatActivity.VIEW_TYPE_USER_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_user, parent, false);
            return new ItemMessageUserHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemMessageFriendHolder) {

            if (consersation.getListMessageData().get(position).type.equals("text")) {
                ((ItemMessageFriendHolder) holder).txtContent.setText(consersation.getListMessageData().get(position).text);
                ((ItemMessageFriendHolder) holder).ImageView_friends.setVisibility(View.GONE);
                ((ItemMessageFriendHolder) holder).txtContent.setVisibility(View.VISIBLE);
            } else {
                ((ItemMessageFriendHolder) holder).txtContent.setVisibility(View.GONE);
                ((ItemMessageFriendHolder) holder).ImageView_friends.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(consersation.getListMessageData()
                                .get(position).text)
                        .into(((ItemMessageFriendHolder) holder)
                                .ImageView_friends);

                ((ItemMessageFriendHolder) holder).SetOnItemFClickListener(new ItemMessageFriendHolder.OnItemFClickListener() {
                    @Override
                    public void onItemFClick(View view, int position) {

                        Intent i = new Intent(context, PhotoActivity.class);
                        i.putExtra("url", consersation.getListMessageData().get(position).text);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);
                    }
                });
            }
            Bitmap currentAvata = bitmapAvata.get(consersation.getListMessageData().get(position).idSender);
            if (currentAvata != null) {
                ((ItemMessageFriendHolder) holder).avata.setImageBitmap(currentAvata);
            } else {
                final String id = consersation.getListMessageData().get(position).idSender;
                if (bitmapAvataDB.get(id) == null) {
                    bitmapAvataDB.put(id, FirebaseDatabase.getInstance().getReference().child("user/" + id + "/avata"));
                    bitmapAvataDB.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                String avataStr = (String) dataSnapshot.getValue();
//                                if (!avataStr.equals(StaticConfig.STR_DEFAULT_BASE64)) {
//                                    byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
//                                    ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
//                                } else {
//                                    ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avata));
//                                }
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        } else if (holder instanceof ItemMessageUserHolder) {
            ((ItemMessageUserHolder) holder).txtContent.setVisibility(View.VISIBLE);
            if (consersation.getListMessageData().get(position).type.equals(Message.TYPE_TEXT)) {
                ((ItemMessageUserHolder) holder).ImageView_user.setVisibility(View.GONE);
                ((ItemMessageUserHolder) holder).txtContent.setText(consersation.getListMessageData().get(position).text);
                if (bitmapAvataUser != null) {
                    ((ItemMessageUserHolder) holder).avata.setImageBitmap(bitmapAvataUser);
                }
            } else {
                ((ItemMessageUserHolder) holder).ImageView_user.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(consersation.getListMessageData()
                                .get(position).text)
                        .into(((ItemMessageUserHolder) holder)
                                .ImageView_user);

                ((ItemMessageUserHolder) holder).SetOnItemSClickListener(new ItemMessageUserHolder.OnItemSClickListener() {
                    @Override
                    public void onItemSClick(View view, int position) {
                        Intent i = new Intent(context, PhotoActivity.class);
                        i.putExtra("url", consersation.getListMessageData().get(position).text);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);
                    }
                });


                ((ItemMessageUserHolder) holder).txtContent.setVisibility(View.GONE);
                if (bitmapAvataUser != null) {
                    ((ItemMessageUserHolder) holder).avata.setImageBitmap(bitmapAvataUser);
                }
            }


        }
    }

    @Override
    public int getItemViewType(int position) {
        return consersation.getListMessageData().get(position).idSender.equals(userId) ? ChatActivity.VIEW_TYPE_USER_MESSAGE : ChatActivity.VIEW_TYPE_FRIEND_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return consersation.getListMessageData().size();
    }
}

class ItemMessageUserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static OnItemSClickListener mItemSenderFlickListener;
    public TextView txtContent;
    public ImageView ImageView_user;
    public CircleImageView avata;

    public ItemMessageUserHolder(View itemView) {
        super(itemView);
        txtContent = (TextView) itemView.findViewById(R.id.textContentUser);
        avata = (CircleImageView) itemView.findViewById(R.id.imageView2);
        ImageView_user = (ImageView) itemView.findViewById(R.id.ImageView_user);
        ImageView_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ImageView_user:
                if (mItemSenderFlickListener != null) {
                    mItemSenderFlickListener.onItemSClick(v, getPosition());
                }

        }
    }

    public interface OnItemSClickListener {
        public void onItemSClick(View view, int position);

    }

    public void SetOnItemSClickListener(final OnItemSClickListener mItemSenderFlickListener) {
        this.mItemSenderFlickListener = mItemSenderFlickListener;
    }
}

class ItemMessageFriendHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static OnItemFClickListener mItemFlickListener;
    public TextView txtContent;
    public CircleImageView avata;
    public ImageView ImageView_friends;

    public ItemMessageFriendHolder(View itemView) {
        super(itemView);
        txtContent = (TextView) itemView.findViewById(R.id.textContentFriend);
        avata = (CircleImageView) itemView.findViewById(R.id.imageView3);
        ImageView_friends = (ImageView) itemView.findViewById(R.id.ImageView_friends);

        ImageView_friends.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ImageView_friends:
                if (mItemFlickListener != null) {
                    mItemFlickListener.onItemFClick(v, getPosition());
                }

        }
    }

    public interface OnItemFClickListener {
        public void onItemFClick(View view, int position);

    }

    public void SetOnItemFClickListener(final ItemMessageFriendHolder.OnItemFClickListener mItemFlickListener) {
        this.mItemFlickListener = mItemFlickListener;
    }


}
