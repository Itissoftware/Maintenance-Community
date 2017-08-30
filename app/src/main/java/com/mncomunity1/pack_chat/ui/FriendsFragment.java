package com.mncomunity1.pack_chat.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mncomunity1.R;
import com.mncomunity1.pack_chat.MainActivityChat;
import com.mncomunity1.pack_chat.data.FriendDB;
import com.mncomunity1.pack_chat.data.StaticConfig;
import com.mncomunity1.pack_chat.model.Add;
import com.mncomunity1.pack_chat.model.Friend;
import com.mncomunity1.pack_chat.model.ListFriend;
import com.mncomunity1.pack_chat.service.ServiceUtils;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsFragment extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerListFrends;
    private ListFriendsAdapter adapter;

    private ListFriend dataListFriend = null;
    private ArrayList<String> listFriendID = null;
    private LovelyProgressDialog dialogFindAllFriend;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CountDownTimer detectFriendOnline;
    public static int ACTION_START_CHAT = 1;
    private TextView txt_add_fridends, txt_add;

    LovelyProgressDialog dialogWait;

    public static final String ACTION_DELETE_FRIEND = "com.android.rivchat.DELETE_FRIEND";

    private BroadcastReceiver deleteFriendReceiver;

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String userIdM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_people);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userIdM = sp.getString("userId", "000");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        dialogWait = new LovelyProgressDialog(getApplicationContext());
        recyclerListFrends = (RecyclerView) findViewById(R.id.recycleListFriend);
        txt_add_fridends = (TextView) findViewById(R.id.txt_add_fridends);
        txt_add = (TextView) findViewById(R.id.txt_add);
        getListFriendUId();


        detectFriendOnline = new CountDownTimer(System.currentTimeMillis(), StaticConfig.TIME_TO_REFRESH) {
            @Override
            public void onTick(long l) {
                ServiceUtils.updateFriendStatus(getApplicationContext(), dataListFriend);
                ServiceUtils.updateUserStatus(getApplicationContext());
            }

            @Override
            public void onFinish() {

            }
        };
        if (dataListFriend == null) {
            dataListFriend = FriendDB.getInstance(getApplicationContext()).getListFriend();
            if (dataListFriend.getListFriend().size() > 0) {
                listFriendID = new ArrayList<>();
                for (Friend friend : dataListFriend.getListFriend()) {
                    listFriendID.add(friend.id);
                }
                detectFriendOnline.start();

                txt_add_fridends.setVisibility(View.GONE);
                recyclerListFrends.setVisibility(View.VISIBLE);
            }
        }



        recyclerListFrends.setLayoutManager(linearLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        adapter = new ListFriendsAdapter(getApplicationContext(), dataListFriend,userIdM, this);
        recyclerListFrends.setAdapter(adapter);
        dialogFindAllFriend = new LovelyProgressDialog(FriendsFragment.this);
        if (listFriendID == null) {
            listFriendID = new ArrayList<>();
            dialogFindAllFriend.setCancelable(false)
                    .setIcon(R.drawable.ic_add_friend)
                    .setTitle("กำลังโหลดข้อมูลเพื่อน")
                    .setTopColorRes(R.color.colorPrimary)
                    .show();

            txt_add_fridends.setVisibility(View.GONE);
            recyclerListFrends.setVisibility(View.VISIBLE);
        }

        deleteFriendReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String idDeleted = intent.getExtras().getString("idFriend");
                for (Friend friend : dataListFriend.getListFriend()) {
                    if (idDeleted.equals(friend.id)) {
                        ArrayList<Friend> friends = dataListFriend.getListFriend();
                        friends.remove(friend);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        };

        IntentFilter intentFilter = new IntentFilter(ACTION_DELETE_FRIEND);
        registerReceiver(deleteFriendReceiver, intentFilter);

        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new LovelyTextInputDialog(getApplicationContext(), R.style.EditTextTintTheme)
                        .setTitle("เพิ่มเพื่อน")
                        .setMessage("ใส่อีเมล์เพื่อน")
                        .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                        .setInputFilter("อีเมล์ไม่มีถูกต้อง", new LovelyTextInputDialog.TextFilter() {
                            @Override
                            public boolean check(String text) {
                                Pattern VALID_EMAIL_ADDRESS_REGEX =
                                        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(text);
                                return matcher.find();
                            }
                        })
                        .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                            @Override
                            public void onTextInputConfirmed(String text) {
                                //Tim id user id
                                findIDEmail(text);
                            }
                        })
                        .show();


            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ACTION_START_CHAT == requestCode && data != null && ListFriendsAdapter.mapMark != null) {
            ListFriendsAdapter.mapMark.put(data.getStringExtra("idFriend"), false);
        }
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getApplicationContext(), "onRefresh", Toast.LENGTH_SHORT).show();
        listFriendID.clear();
        dataListFriend.getListFriend().clear();
        adapter.notifyDataSetChanged();
        FriendDB.getInstance(getApplicationContext()).dropDB();
        detectFriendOnline.cancel();
        getListFriendUId();
    }


    public void findIDEmail(String email) {
        dialogWait.setCancelable(false)
                .setIcon(R.drawable.ic_add_friend)
                .setTitle("ค้าหา เพื่อน....")
                .setTopColorRes(R.color.colorPrimary)
                .show();
        FirebaseDatabase.getInstance().getReference().child("user").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialogWait.dismiss();
                if (dataSnapshot.getValue() == null) {
                    //email not found
                    new LovelyInfoDialog(getApplicationContext())
                            .setTopColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.ic_add_friend)
                            .setTitle("ไม่เจอ")
                            .setMessage("อีเมล์นี้ไม่มีในระบบ")
                            .show();
                } else {
                    String id = ((HashMap) dataSnapshot.getValue()).keySet().iterator().next().toString();

                    if (id.equals(StaticConfig.UID)) {
                        new LovelyInfoDialog(getApplicationContext())
                                .setTopColorRes(R.color.colorAccent)
                                .setIcon(R.drawable.ic_add_friend)
                                .setTitle("ไม่เจอ")
                                .setMessage("อีเมล์นี้ไม่มีในระบบ")
                                .show();
                    } else {
                        HashMap userMap = (HashMap) ((HashMap) dataSnapshot.getValue()).get(id);
                        String userIdF;
                        Friend user = new Friend();
                        user.name = (String) userMap.get("name");
                        user.email = (String) userMap.get("email");
                        user.avata = (String) userMap.get("avata");
                        user.id = id;
                        user.userId = (String) userMap.get("userId");
                        user.idRoom = id.compareTo(StaticConfig.UID) > 0 ? (StaticConfig.UID + id).hashCode() + "" : "" + (id + StaticConfig.UID).hashCode();
                        userIdF = user.userId;
                        Log.e("userIdFFF", user.userId);

                        checkBeforAddFriend(userIdF, user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void checkBeforAddFriend(final String idFriend, Friend userInfo) {
        dialogWait.setCancelable(false)
                .setIcon(R.drawable.ic_add_friend)
                .setTitle("เพิ่ม เพื่อน....")
                .setTopColorRes(R.color.colorPrimary)
                .show();

        //Check xem da ton tai id trong danh sach id chua
        if (listFriendID.contains(idFriend)) {
            dialogWait.dismiss();
            new LovelyInfoDialog(getApplicationContext())
                    .setTopColorRes(R.color.colorPrimary)
                    .setIcon(R.drawable.ic_add_friend)
                    .setTitle("เพื่อน")
                    .setMessage("User " + userInfo.email + " has been friend")
                    .show();
        } else {
            addFriend(idFriend, true);
            listFriendID.add(idFriend);
            dataListFriend.getListFriend().add(userInfo);
            FriendDB.getInstance(getApplicationContext()).addFriend(userInfo);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * เพิ่มเพื่อน
     */
    private void addFriend(final String idFriend, boolean isIdFriend) {
        if (idFriend != null) {
            if (isIdFriend) {

                ArrayList<Add> list = new ArrayList<>();

                Add add = new Add(userIdM);
                list.add(add);

                FirebaseDatabase.getInstance().getReference().child("friend/" + idFriend).push().setValue(add)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    addFriend(idFriend, false);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogWait.dismiss();
                                new LovelyInfoDialog(getApplicationContext())
                                        .setTopColorRes(R.color.colorAccent)
                                        .setIcon(R.drawable.ic_add_friend)
                                        .setTitle("ผิดพลาด")
                                        .setMessage("เพิ่มเพื่อนไม่ได้")
                                        .show();
                            }
                        });
            } else {
                Add addIdM = new Add(idFriend);

                FirebaseDatabase.getInstance().getReference().child("friend/" + userIdM).push().setValue(addIdM).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            addFriend(null, false);
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogWait.dismiss();
                                new LovelyInfoDialog(getApplicationContext())
                                        .setTopColorRes(R.color.colorAccent)
                                        .setIcon(R.drawable.ic_add_friend)
                                        .setTitle("ผิดพลาด")
                                        .setMessage("เพิ่มเพื่อนไม่ได้")
                                        .show();
                            }
                        });
            }
        } else {
            dialogWait.dismiss();
            new LovelyInfoDialog(getApplicationContext())
                    .setTopColorRes(R.color.colorPrimary)
                    .setIcon(R.drawable.ic_add_friend)
                    .setTitle("สำเร็จ")
                    .setMessage("เพิ่มเพื่อนสำเร็จ")
                    .show();

//            Intent i = new Intent(getActivity(), MainActivityChat.class);
//            startActivity(i);
//            getActivity().finish();

            listFriendID.clear();
            dataListFriend.getListFriend().clear();
            adapter.notifyDataSetChanged();
            FriendDB.getInstance(getApplicationContext()).dropDB();
            detectFriendOnline.cancel();
            getListFriendUId();
        }
    }

    private void getListFriendUId() {

        FirebaseDatabase.getInstance().getReference().child("friend").child(userIdM).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Log.e("gggggg", "=======" + postSnapshot.child("userId").getValue().toString());

                        FirebaseDatabase.getInstance().getReference().child("user").orderByChild("userId").equalTo(postSnapshot.child("userId").getValue().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Log.e("eeeeeee", "=======" + postSnapshot.child("email").getValue().toString());


                                    Friend user = new Friend();
                                    HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();
                                    user.name = postSnapshot.child("name").getValue().toString();
                                    user.email = postSnapshot.child("email").getValue().toString();
                                    user.avata = postSnapshot.child("avata").getValue().toString();
                                    user.userId = postSnapshot.child("userId").getValue().toString();

                                    int userIdMM = new Integer(userIdM);
                                    int userIdFF = new Integer(postSnapshot.child("userId").getValue().toString());
                                    int room = userIdMM * userIdFF * 3;

                                    Log.e("SUM--->", userIdMM + "*" + userIdFF + "*3" + room);


                                    dataListFriend.getListFriend().add(user);
//                                    FriendDB.getInstance(getContext()).addFriend(user);
//
                                    adapter.notifyDataSetChanged();
                                    dialogFindAllFriend.dismiss();
                                    mSwipeRefreshLayout.setRefreshing(false);
                                    detectFriendOnline.start();

                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    //getAllFriendInfo(0);
                } else {
                    dialogFindAllFriend.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getAllFriendInfo(final int index) {
        if (index == listFriendID.size()) {
            //save list friend
            adapter.notifyDataSetChanged();
            dialogFindAllFriend.dismiss();
            mSwipeRefreshLayout.setRefreshing(false);
            detectFriendOnline.start();
        } else {
            final String id = listFriendID.get(index);
            Log.e("id", id);
            FirebaseDatabase.getInstance().getReference().child("friend/" + id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {


                        Friend user = new Friend();
                        HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();
                        user.name = (String) mapUserInfo.get("name");
                        user.email = (String) mapUserInfo.get("email");
                        user.avata = (String) mapUserInfo.get("avata");
                        user.id = id;
                        user.idRoom = id.compareTo(StaticConfig.UID) > 0 ? (StaticConfig.UID + id).hashCode() + "" : "" + (id + StaticConfig.UID).hashCode();

                        // Log.e("ddddddd", id.compareTo(StaticConfig.UID) > 0 ? (StaticConfig.UID + id).hashCode() + "" : "" + (id + StaticConfig.UID).hashCode());

                        dataListFriend.getListFriend().add(user);
                        FriendDB.getInstance(getApplicationContext()).addFriend(user);
                    }
                    getAllFriendInfo(index + 1);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}

class ListFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ListFriend listFriend;
    private String userIdM;
    private Context context;
    public static Map<String, Query> mapQuery;
    public static Map<String, DatabaseReference> mapQueryOnline;
    public static Map<String, ChildEventListener> mapChildListener;
    public static Map<String, ChildEventListener> mapChildListenerOnline;
    public static Map<String, Boolean> mapMark;
    private FriendsFragment fragment;
    LovelyProgressDialog dialogWaitDeleting;

    public ListFriendsAdapter(Context context, ListFriend listFriend,String userIdM, FriendsFragment fragment) {
        this.listFriend = listFriend;
        this.userIdM = userIdM;
        this.context = context;
        mapQuery = new HashMap<>();
        mapChildListener = new HashMap<>();
        mapMark = new HashMap<>();
        mapChildListenerOnline = new HashMap<>();
        mapQueryOnline = new HashMap<>();
        this.fragment = fragment;
        dialogWaitDeleting = new LovelyProgressDialog(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_friend, parent, false);
        return new ItemFriendViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final String name = listFriend.getListFriend().get(position).name;
        final String userId = listFriend.getListFriend().get(position).userId;
        final String id = listFriend.getListFriend().get(position).id;
        final String idRoom = listFriend.getListFriend().get(position).idRoom;
        final String avata = listFriend.getListFriend().get(position).avata;
        final String email = listFriend.getListFriend().get(position).email;


        ((ItemFriendViewHolder) holder).txtName.setText(name);

        ((View) ((ItemFriendViewHolder) holder).txtName.getParent().getParent().getParent())
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((ItemFriendViewHolder) holder).txtMessage.setTypeface(Typeface.DEFAULT);
                        ((ItemFriendViewHolder) holder).txtName.setTypeface(Typeface.DEFAULT);

                        int userIdMM = new Integer(userIdM);
                        int userIdFF = new Integer(userId);
                        int room = userIdMM * userIdFF * 3;

                        int roomId = room;
                        Log.e("roomId",roomId+"");
                        String roomIdStr = Integer.toString(roomId);

                        Intent intent = new Intent(context, ChatActivity.class);
                        intent.putExtra(StaticConfig.INTENT_KEY_CHAT_FRIEND, name);
                        intent.putExtra("email_friend", email);
                        ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
                        idFriend.add(id);
                        intent.putCharSequenceArrayListExtra(StaticConfig.INTENT_KEY_CHAT_ID, idFriend);
                        intent.putExtra("UID", StaticConfig.UID);
                        intent.putExtra("UIDFRIEND", id);
                        intent.putExtra("USERIDFRIEND",userId);
                        intent.putExtra(StaticConfig.INTENT_KEY_CHAT_ROOM_ID, roomIdStr);
                        // Log.e("Room ID", idRoom);
                        ChatActivity.bitmapAvataFriend = new HashMap<>();
                        if (!avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
                           // byte[] decodedString = Base64.decode(avata, Base64.DEFAULT);
                            //ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                        } else {
                           // ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avata));
                        }

                        mapMark.put(id, null);
                        fragment.startActivityForResult(intent, FriendsFragment.ACTION_START_CHAT);
                    }
                });

        ((View) ((ItemFriendViewHolder) holder).txtName.getParent().getParent().getParent())
                .setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        String friendName = (String) ((ItemFriendViewHolder) holder).txtName.getText();

                        new AlertDialog.Builder(context)
                                .setTitle("Delete Friend")
                                .setMessage("Are you sure want to delete " + friendName + "?")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        final String idFriendRemoval = listFriend.getListFriend().get(position).id;
                                        dialogWaitDeleting.setTitle("Deleting...")
                                                .setCancelable(false)
                                                .setTopColorRes(R.color.colorAccent)
                                                .show();
                                        deleteFriend(idFriendRemoval);
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();

                        return true;
                    }
                });



       ((ItemFriendViewHolder) holder).avata.setImageResource(R.drawable.default_avata);
//        if (listFriend.getListFriend().get(position).avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
//            ((ItemFriendViewHolder) holder).avata.setImageResource(R.drawable.default_avata);
//        } else {
//            byte[] decodedString = Base64.decode(listFriend.getListFriend().get(position).avata, Base64.DEFAULT);
//            Bitmap src = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            ((ItemFriendViewHolder) holder).avata.setImageBitmap(src);
//        }


        if (mapQueryOnline.get(id) == null && mapChildListenerOnline.get(id) == null) {
            mapQueryOnline.put(id, FirebaseDatabase.getInstance().getReference().child("user/" + id + "/status"));
            mapChildListenerOnline.put(id, new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getValue() != null && dataSnapshot.getKey().equals("isOnline")) {
                        Log.d("FriendsFragment add " + id, (boolean) dataSnapshot.getValue() + "");
//                        listFriend.getListFriend().get(position).status.isOnline = (boolean) dataSnapshot.getValue();
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getValue() != null && dataSnapshot.getKey().equals("isOnline")) {
                        Log.d("FriendsFragment change " + id, (boolean) dataSnapshot.getValue() + "");
//                        listFriend.getListFriend().get(position).status.isOnline = (boolean) dataSnapshot.getValue();
                        notifyDataSetChanged();
                    }
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
            mapQueryOnline.get(id).addChildEventListener(mapChildListenerOnline.get(id));
        }


    }

    @Override
    public int getItemCount() {
        return listFriend.getListFriend() != null ? listFriend.getListFriend().size() : 0;
    }

    /**
     * Delete friend
     *
     * @param idFriend
     */
    private void deleteFriend(final String idFriend) {
        if (idFriend != null) {
            FirebaseDatabase.getInstance().getReference().child("friend").child(StaticConfig.UID)
                    .orderByValue().equalTo(idFriend).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() == null) {
                        //email not found
                        dialogWaitDeleting.dismiss();
                        new LovelyInfoDialog(context)
                                .setTopColorRes(R.color.colorAccent)
                                .setTitle("Error")
                                .setMessage("Error occurred during deleting friend")
                                .show();
                    } else {
                        String idRemoval = ((HashMap) dataSnapshot.getValue()).keySet().iterator().next().toString();
                        FirebaseDatabase.getInstance().getReference().child("friend")
                                .child(StaticConfig.UID).child(idRemoval).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialogWaitDeleting.dismiss();

                                        new LovelyInfoDialog(context)
                                                .setTopColorRes(R.color.colorAccent)
                                                .setTitle("Success")
                                                .setMessage("Friend deleting successfully")
                                                .show();

                                        Intent intentDeleted = new Intent(FriendsFragment.ACTION_DELETE_FRIEND);
                                        intentDeleted.putExtra("idFriend", idFriend);
                                        context.sendBroadcast(intentDeleted);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogWaitDeleting.dismiss();
                                        new LovelyInfoDialog(context)
                                                .setTopColorRes(R.color.colorAccent)
                                                .setTitle("Error")
                                                .setMessage("Error occurred during deleting friend")
                                                .show();
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            dialogWaitDeleting.dismiss();
            new LovelyInfoDialog(context)
                    .setTopColorRes(R.color.colorPrimary)
                    .setTitle("Error")
                    .setMessage("Error occurred during deleting friend")
                    .show();
        }
    }
}


class ItemFriendViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView avata;
    public TextView txtName, txtTime, txtMessage;
    private Context context;

    ItemFriendViewHolder(Context context, View itemView) {
        super(itemView);
        avata = (CircleImageView) itemView.findViewById(R.id.icon_avata);
        txtName = (TextView) itemView.findViewById(R.id.txtName);
        txtTime = (TextView) itemView.findViewById(R.id.txtTime);
        txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
        this.context = context;
    }


}

