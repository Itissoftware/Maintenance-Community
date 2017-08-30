package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.NewsRc;
import com.mncomunity1.model.Post;
import com.mncomunity1.model.SeminarTitle;
import com.mncomunity1.viewholder.RecyclerViewSimpleTextViewHolder;
import com.mncomunity1.viewholder.ViewHolderPhoto;
import com.mncomunity1.viewholder.ViewHolderText;

import java.util.ArrayList;


public class SeminarTitleAdapter extends RecyclerView.Adapter<SeminarTitleAdapter.ContactViewHolder> {

    Context context;
    ArrayList<SeminarTitle> list = new ArrayList<>();
    public static ListRecyclerAdapter.OnItemClickListener mItemClickListener;

    public SeminarTitleAdapter(Context context, ArrayList<SeminarTitle> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(SeminarTitleAdapter.ContactViewHolder contactViewHolder, int i) {
        SeminarTitle item = list.get(i);
        contactViewHolder.title_tv.setText(item.getTotal().get(i).getTitle());
        contactViewHolder.title_course.setText(item.getTotal().get(i).getCourse());

        String url = item.getTotal().get(i).getPath_image();

        Glide.with(context)
                .load(url)
                .into(contactViewHolder.img_cover);


    }

    @Override
    public SeminarTitleAdapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_seminar_title, viewGroup, false);

        return new SeminarTitleAdapter.ContactViewHolder(itemView);
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img_cover;
        TextView title_tv;
        TextView title_course;
        TextView txt_ts;
        LinearLayout ls_onclick;


        public ContactViewHolder(View v) {
            super(v);
            img_cover = (ImageView) v.findViewById(R.id.img_cover);
            title_tv = (TextView) v.findViewById(R.id.title_tv);
            title_course = (TextView) v.findViewById(R.id.title_course);
            txt_ts = (TextView) v.findViewById(R.id.txt_ts);
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

    public void SetOnItemVideiosClickListener(final ListRecyclerAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}