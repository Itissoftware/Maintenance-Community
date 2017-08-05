package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.ModelSpare;

import java.util.ArrayList;

public class SpareOnCompanyRecyclerAdapter extends RecyclerView.Adapter<SpareOnCompanyRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<ModelSpare> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;
    public static OnItemClickListener mItemTextClickListener;

    public SpareOnCompanyRecyclerAdapter(Context context, ArrayList<ModelSpare> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ModelSpare item = list.get(i);
        contactViewHolder.title_tv.setText(item.getTotal().get(i).getNameProduct());

//        Picasso.with(context)
//                .load(item.getPost().get(i).getFile_img())
//                .fit().centerCrop()
//                .into(contactViewHolder.image_logo);

        String url = "http://mn-community.com/admin_mc/"+item.getTotal().get(i).getImg();
        Log.e("url", url);

        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(contactViewHolder.img_logo);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_spare_on_company, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img_logo;
        TextView title_tv;
        TextView txt_check;
        LinearLayout ls_onclick;

        public ContactViewHolder(View v) {
            super(v);
            img_logo = (ImageView) v.findViewById(R.id.img_logo);
            title_tv = (TextView) v.findViewById(R.id.title_tv);
            txt_check = (TextView) v.findViewById(R.id.txt_check);
            ls_onclick = (LinearLayout) v.findViewById(R.id.ls_onclick);
            v.setOnClickListener(this);
            ls_onclick.setOnClickListener(this);
            txt_check.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ls_onclick:
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }
                   break;
                case R.id.txt_check:
                    if (mItemTextClickListener != null) {
                        mItemTextClickListener.onItemClick(v, getPosition());
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

    public void SetOnItemTextClickListener(final OnItemClickListener mItemTextClickListener) {
        this.mItemTextClickListener = mItemTextClickListener;
    }

}