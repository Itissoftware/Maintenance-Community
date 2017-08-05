package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mncomunity1.R;
import com.mncomunity1.model.OrderForDetails;

import java.util.ArrayList;

public class GetOrderForVendorDetailsRecyclerAdapter extends RecyclerView.Adapter<GetOrderForVendorDetailsRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<OrderForDetails> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public GetOrderForVendorDetailsRecyclerAdapter(Context context, ArrayList<OrderForDetails> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        OrderForDetails item = list.get(i);
        contactViewHolder.title_tv.setText(item.getTotal().get(i).getNameth());
        contactViewHolder.txt_total.setText(item.getTotal().get(i).getTotal()+" จำนวน");

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_order_vendor2, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image_logo;
        TextView title_tv;
        LinearLayout ls_onclick;
        TextView txt_total;

        public ContactViewHolder(View v) {
            super(v);
            image_logo = (ImageView) v.findViewById(R.id.image_logo);
            title_tv = (TextView) v.findViewById(R.id.title);
            ls_onclick = (LinearLayout) v.findViewById(R.id.ls_onclick);
            txt_total = (TextView) v.findViewById(R.id.txt_total);
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