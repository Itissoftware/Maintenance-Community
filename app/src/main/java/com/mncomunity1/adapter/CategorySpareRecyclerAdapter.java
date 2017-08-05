package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.Category;

import java.util.ArrayList;

public class CategorySpareRecyclerAdapter extends RecyclerView.Adapter<CategorySpareRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<Category> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public CategorySpareRecyclerAdapter(Context context, ArrayList<Category> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Category item = list.get(i);
        contactViewHolder.title_tv.setText(item.getTotal().get(i).getNameth());

        String url ="http://mn-community.com/web/"+item.getTotal().get(i).getImage();

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
                inflate(R.layout.item_category, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image_logo;
        TextView title_tv;
        LinearLayout ls_onclick;
        CardView card_view;

        public ContactViewHolder(View v) {
            super(v);
           image_logo = (ImageView) v.findViewById(R.id.image_logo);
            title_tv = (TextView) v.findViewById(R.id.title);
            ls_onclick = (LinearLayout) v.findViewById(R.id.ls_onclick);
            card_view = (CardView) v.findViewById(R.id.card_view);
            v.setOnClickListener(this);
            card_view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_view:
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