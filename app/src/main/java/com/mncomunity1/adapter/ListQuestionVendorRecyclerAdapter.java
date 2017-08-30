package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.QuestionList;
import com.mncomunity1.model.QuestionListVendor;

import java.util.ArrayList;

public class ListQuestionVendorRecyclerAdapter extends RecyclerView.Adapter<ListQuestionVendorRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<QuestionListVendor> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public ListQuestionVendorRecyclerAdapter(Context context, ArrayList<QuestionListVendor> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, final int i) {
        final QuestionListVendor item = list.get(i);

        contactViewHolder.textONEs.setText(item.getTotal().get(i).getNameth());
        contactViewHolder.txt_amount.setText(item.getTotal().get(i).getPrice()+" บาท");
        contactViewHolder.txt_time.setText(item.getTotal().get(i).getTimevender());
        contactViewHolder.txt_address.setText(item.getTotal().get(i).getAddress());

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
                inflate(R.layout.item_question1, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textONEs;
        private TextView txt_amount;
        private ImageView img_cover;
        private CardView card_view;
        private TextView txt_question;
        private TextView txt_address;
        private TextView txt_time;

        public ContactViewHolder(View v) {
            super(v);

            textONEs = (TextView) v.findViewById(R.id.textONEs);
            txt_amount = (TextView) v.findViewById(R.id.txt_amount);
            txt_time = (TextView) v.findViewById(R.id.txt_time);
            txt_address = (TextView) v.findViewById(R.id.txt_address);
            img_cover = (ImageView) v.findViewById(R.id.img_cover);
            card_view = (CardView) v.findViewById(R.id.card_view);
            txt_question = (TextView) v.findViewById(R.id.txt_question);
            txt_question.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txt_question:
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