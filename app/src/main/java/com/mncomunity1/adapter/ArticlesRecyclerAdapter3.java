package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.Post;

import java.util.ArrayList;

public class ArticlesRecyclerAdapter3 extends RecyclerView.Adapter<ArticlesRecyclerAdapter3.ContactViewHolder> {

    Context context;
    ArrayList<Post> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public ArticlesRecyclerAdapter3(Context context, ArrayList<Post> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Post item = list.get(i);
        contactViewHolder.title_tv.setText(item.getPost().get(i).getTitle());
        contactViewHolder.txt_ts.setText(item.getPost().get(i).getTitle());


        String cover = item.getPost().get(i).getFile_img();
        Glide.with(context)
                .load(cover)
                .crossFade()
                .into(contactViewHolder.img_cover);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_a, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img_cover;
        TextView title_tv;
        LinearLayout ls_onclick;
        TextView txt_n;
        TextView txt_ts;

        public ContactViewHolder(View v) {
            super(v);
            img_cover = (ImageView) v.findViewById(R.id.img_cover);
            title_tv = (TextView) v.findViewById(R.id.title_tv);
            txt_ts = (TextView) v.findViewById(R.id.txt_ts);
            ls_onclick = (LinearLayout) v.findViewById(R.id.ls_onclick);
            txt_n = (TextView) v.findViewById(R.id.txt_n);
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