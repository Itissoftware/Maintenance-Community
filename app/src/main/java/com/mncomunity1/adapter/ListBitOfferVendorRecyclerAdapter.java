package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.BitOfferDetails;
import com.mncomunity1.model.Post;

import java.util.ArrayList;

public class ListBitOfferVendorRecyclerAdapter extends RecyclerView.Adapter<ListBitOfferVendorRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<BitOfferDetails> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public ListBitOfferVendorRecyclerAdapter(Context context, ArrayList<BitOfferDetails> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, final int i) {
        final BitOfferDetails item = list.get(i);

        contactViewHolder.textONEs.setText(item.getTotal().get(i).getNameth());


        String cover = "http://mn-community.com/admin_mc/" + item.getTotal().get(i).getImg_path();
        Glide.with(context)
                .load(cover)
                .crossFade()
                .into(contactViewHolder.img_cover);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_bit_offer_vendor, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textONEs;
        private ImageView img_cover;

        public ContactViewHolder(View v) {
            super(v);

            textONEs = (TextView) v.findViewById(R.id.textONEs);
            img_cover = (ImageView) v.findViewById(R.id.img_cover);
        }

        @Override
        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.ls_onclick:
//                    if (mItemClickListener != null) {
//                        mItemClickListener.onItemClick(v, getPosition());
//                    }
//
//            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

    }

    public void SetOnItemVideiosClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}