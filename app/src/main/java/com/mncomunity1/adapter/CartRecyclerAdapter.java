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
import com.mncomunity1.model.getOrder;

import java.util.ArrayList;
import java.util.List;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ContactViewHolder> {

    Context context;
    List<getOrder> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;
    public static OnItemEditClickListener mItemEditClickListener;
    public static OnItemDelteClickListener mItemDelteClickListener;
    public static OnItemLongClickListener mItemLongClick;

    public CartRecyclerAdapter(Context context, List<getOrder> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        getOrder item = list.get(i);
        contactViewHolder.title_tv.setText(item.getTotal().get(i).getNameth());
        contactViewHolder.title_total.setText(item.getTotal().get(i).getTotal());
        String url = "http://mn-community.com/admin_mc/"+item.getTotal().get(i).getImage();

        Log.e("url",url);

        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(contactViewHolder.image_logo);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_cart, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image_logo;
        TextView title_tv,title_total;
        TextView txt_edit;
        TextView txt_delete;
        LinearLayout ls_onclick;

        public ContactViewHolder(View v) {
            super(v);
            image_logo = (ImageView) v.findViewById(R.id.image_logo);
            txt_edit = (TextView) v.findViewById(R.id.txt_edit);
            title_tv = (TextView) v.findViewById(R.id.title_tv);
            txt_delete = (TextView) v.findViewById(R.id.txt_delete);
            title_total = (TextView) v.findViewById(R.id.title_total);
            ls_onclick = (LinearLayout) v.findViewById(R.id.ls_onclick);
            v.setOnClickListener(this);

            txt_delete.setOnClickListener(this);
            txt_edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txt_edit:
                    if (mItemEditClickListener != null) {
                        mItemEditClickListener.onItemEditClick(v, getPosition());
                    }
                    break;
                case R.id.txt_delete:
                    if (mItemDelteClickListener != null) {
                        mItemDelteClickListener.onItemDeleteClick(v, getPosition());
                    }
                    break;

            }
        }
    }


    public interface OnItemLongClickListener {
        public void onItemLongClick(View view, int position);

    }

    public void SetOnItemLongClickListener(final OnItemLongClickListener mItemLongClick) {
        this.mItemLongClick = mItemLongClick;
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

    }


    public void SetOnItemVideiosClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface OnItemEditClickListener {
        public void onItemEditClick(View view, int position);

    }


    public void SetOnItemEditClickListener(final OnItemEditClickListener mItemEditClickListener) {
        this.mItemEditClickListener = mItemEditClickListener;
    }


    public interface OnItemDelteClickListener {
        public void onItemDeleteClick(View view, int position);

    }

    public void SetOnItemDelteClickListener(final OnItemDelteClickListener mItemDelteClickListener) {
        this.mItemDelteClickListener = mItemDelteClickListener;
    }





}