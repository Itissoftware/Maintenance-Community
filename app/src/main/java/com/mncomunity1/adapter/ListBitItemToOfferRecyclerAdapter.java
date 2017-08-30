package com.mncomunity1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.BitItem;
import com.mncomunity1.model.BitOfferDetails;
import com.mncomunity1.model.PriceVndor;

import java.util.ArrayList;

public class ListBitItemToOfferRecyclerAdapter extends RecyclerView.Adapter<ListBitItemToOfferRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<PriceVndor> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public ListBitItemToOfferRecyclerAdapter(Context context, ArrayList<PriceVndor> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, final int i) {
        final PriceVndor item = list.get(i);

        contactViewHolder.textONEs.setText("รายการ: " + item.getTotal().get(i).getTitle());
        contactViewHolder.txt_custommer.setText("ชื่อลูกค้า: " + item.getTotal().get(i).getNameth());
        contactViewHolder.txt_time.setText("บริษัท: " + item.getTotal().get(i).getCompany_nameth());
        contactViewHolder.txt_amount.setText("จำนวน: " + item.getTotal().get(i).getAmount());
        contactViewHolder.txt_email.setText("อีเมล์: " + item.getTotal().get(i).getEmail());


        if (item.getTotal().get(i).getStatus().equals("0")) {
            contactViewHolder.txt_status.setText("รอยืนยัน");
            contactViewHolder.txt_status.setTextColor(Color.parseColor("#009688"));
        } else {
            contactViewHolder.txt_status.setText("ยืนยันแล้ว");
            contactViewHolder.txt_status.setTextColor(Color.parseColor("#e81e63"));
        }


        String cover = "http://mn-community.com/web/api/upload/" + item.getTotal().get(i).getPath_photo();
        Glide.with(context)
                .load(cover)
                .crossFade()
                .into(contactViewHolder.img_cover);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_bit_offer_vendor2, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textONEs;
        private ImageView img_cover;
        private CardView card_view;
        private TextView txt_custommer;
        private TextView txt_time;
        private TextView txt_amount;
        private TextView txt_email;
        private TextView txt_status;

        public ContactViewHolder(View v) {
            super(v);

            textONEs = (TextView) v.findViewById(R.id.textONEs);
            txt_amount = (TextView) v.findViewById(R.id.txt_amount);
            txt_custommer = (TextView) v.findViewById(R.id.txt_custommer);
            txt_email = (TextView) v.findViewById(R.id.txt_email);
            txt_time = (TextView) v.findViewById(R.id.txt_time);
            img_cover = (ImageView) v.findViewById(R.id.img_cover);
            card_view = (CardView) v.findViewById(R.id.card_view);
            txt_status = (TextView) v.findViewById(R.id.txt_status);
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