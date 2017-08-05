package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.GetVendorBanner;

import java.util.ArrayList;

public class GetVendorBannerRecyclerAdapter extends RecyclerView.Adapter<GetVendorBannerRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<GetVendorBanner> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public GetVendorBannerRecyclerAdapter(Context context, ArrayList<GetVendorBanner> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        GetVendorBanner item = list.get(i);


        String cover = "http://mn-community.com/admin_mc/" + item.getTotal().get(i).getImg_path();
        Log.e("cover",cover);
        Glide.with(context)
                .load(cover)
                .crossFade()
                .into(contactViewHolder.img_cover);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_get_banner, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img_cover;
        LinearLayout ls_onclick;


        public ContactViewHolder(View v) {
            super(v);
            img_cover = (ImageView) v.findViewById(R.id.img_cover);
            ls_onclick = (LinearLayout) v.findViewById(R.id.ls_onclick);
            v.setOnClickListener(this);
            ls_onclick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ls_onclick:
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }

            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

    }

    public void SetOnItemVideiosClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}