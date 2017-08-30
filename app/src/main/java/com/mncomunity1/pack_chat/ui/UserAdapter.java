package com.mncomunity1.pack_chat.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mncomunity1.R;
import com.mncomunity1.pack_chat.data.FriendDB;
import com.mncomunity1.pack_chat.data.GroupDB;
import com.mncomunity1.pack_chat.model.Configuration;
import com.mncomunity1.pack_chat.model.User;
import com.mncomunity1.pack_chat.model.User2;
import com.mncomunity1.pack_chat.service.ServiceUtils;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User2> profileConfig;
    Context context;

    public UserAdapter(Context context,List<User2> profileConfig) {
        this.profileConfig = profileConfig;
        this.context = context;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_info_item_layout, parent, false);
        return new UserAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        final User2 config = profileConfig.get(position);
        holder.tv_title.setText(config.name);
        holder.tv_detail.setText(config.email);

        Log.e("dapter",config.name);
        Log.e("dapter",config.email);
       // holder.icon.setImageResource(config.getIcon());

    }

    @Override
    public int getItemCount() {
        return profileConfig.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_title, tv_detail;
        public ImageView icon;

        public ViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            icon = (ImageView) view.findViewById(R.id.img_icon);
        }
    }

}
