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
import com.mncomunity1.model.News;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<News> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public NewsRecyclerAdapter(Context context, ArrayList<News> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        News item = list.get(i);
        contactViewHolder.title_tv.setText(item.getTotal().get(i).getNameth());
        contactViewHolder.txt_ts.setText(item.getTotal().get(i).getTs());

        String cover = "http://mn-community.com/admin_mc/" + item.getTotal().get(i).getCover();

        Log.e("Read",item.getTotal().get(i).getRead());

        if(item.getTotal().get(i).getRead().equals("1")){
            contactViewHolder.txt_n.setVisibility(View.GONE);
        }else{

            contactViewHolder.txt_n.setText("New!");
            Shimmer shimmer = new Shimmer();
            shimmer.start(contactViewHolder.txt_n);
        }

        Glide.with(context)
                .load(cover)
                .centerCrop()
                .crossFade()
                .into(contactViewHolder.img_cover);



    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_news, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img_cover;
        TextView title_tv;
        LinearLayout ls_onclick;
        ShimmerTextView txt_n;
        TextView txt_ts;

        public ContactViewHolder(View v) {
            super(v);
            img_cover = (ImageView) v.findViewById(R.id.img_cover);
            title_tv = (TextView) v.findViewById(R.id.title_tv);
            txt_ts = (TextView) v.findViewById(R.id.txt_ts);
            ls_onclick = (LinearLayout) v.findViewById(R.id.ls_onclick);
            txt_n = (ShimmerTextView) v.findViewById(R.id.txt_n);
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