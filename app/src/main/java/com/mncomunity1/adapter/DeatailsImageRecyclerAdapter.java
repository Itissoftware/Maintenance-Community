package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mncomunity1.R;
import com.mncomunity1.model.DetailsImage;

import java.util.ArrayList;

public class DeatailsImageRecyclerAdapter extends RecyclerView.Adapter<DeatailsImageRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<DetailsImage> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public DeatailsImageRecyclerAdapter(Context context, ArrayList<DetailsImage> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        DetailsImage item = list.get(i);
        contactViewHolder.txt_nameth.setText(item.getTotal().get(i).getNameProduct());
        contactViewHolder.txt_brand.setText(item.getTotal().get(i).getBrand());
        contactViewHolder.txt_company.setText(item.getTotal().get(i).getCompany());
        contactViewHolder.txt_details.setText(item.getTotal().get(i).getDetails());



    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_deyails_image, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_nameth;
        TextView txt_brand;
        TextView txt_company;
        TextView txt_details;

        public ContactViewHolder(View v) {
            super(v);
            txt_nameth = (TextView) v.findViewById(R.id.txt_nameth);
            txt_brand = (TextView) v.findViewById(R.id.txt_brand);
            txt_company = (TextView) v.findViewById(R.id.txt_company);
            txt_details = (TextView) v.findViewById(R.id.txt_details);

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