package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.PriceVndor;

import java.util.ArrayList;

public class ListBitItemToOfferDetailsRecyclerAdapter extends RecyclerView.Adapter<ListBitItemToOfferDetailsRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<PriceVndor> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public ListBitItemToOfferDetailsRecyclerAdapter(Context context, ArrayList<PriceVndor> list) {
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

        contactViewHolder.textONEs.setText(item.getTotal().get(i).getTitle());


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
                inflate(R.layout.item_bit_offer_vendor3, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textONEs;
        private CheckBox checkbox;
        private ImageView img_cover;
        private TextView amount;

        public ContactViewHolder(View v) {
            super(v);

            textONEs = (TextView) v.findViewById(R.id.textONEs);
            checkbox = (CheckBox) v.findViewById(R.id.checkbox);
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