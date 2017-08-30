package com.mncomunity1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.ModelCheckBoxQuestion;
import com.mncomunity1.model.QuestionList;

import java.util.ArrayList;

public class ListQuestionRecyclerAdapter extends RecyclerView.Adapter<ListQuestionRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<ModelCheckBoxQuestion> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public ListQuestionRecyclerAdapter(Context context, ArrayList<ModelCheckBoxQuestion> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, final int i) {
        final ModelCheckBoxQuestion item = list.get(i);

        contactViewHolder.textONEs.setText(item.getTitle());
        contactViewHolder.txt_amount.setText(item.getAmount());

//        if(item.getStatus().equals("1")){
//            contactViewHolder.txt_status.setText("**เสนอราคามาแล้ว");
//            contactViewHolder.txt_status.setTextColor(Color.parseColor("#e81e63"));
//        }else{
//            contactViewHolder.txt_status.setText("**ยังไม่ได้เสนอราคา");
//            contactViewHolder.txt_status.setTextColor(Color.parseColor("#f34336"));
//        }


        String cover = "http://mn-community.com/web/api/upload/" + item.getPath_photo();
        Log.e("cover", cover);
        Glide.with(context)
                .load(cover)
                .crossFade()
                .into(contactViewHolder.img_cover);


        contactViewHolder.chkSelected.setChecked(item.isSelected());

        contactViewHolder.chkSelected.setTag(list.get(i));

        contactViewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                ModelCheckBoxQuestion model = (ModelCheckBoxQuestion) cb.getTag();

                model.setSelected(cb.isChecked());
                list.get(i).setSelected(cb.isChecked());

            }
        });



    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_question, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textONEs;
        private TextView txt_amount;
        private TextView txt_company_name;
        private TextView txt_time;
        private TextView txt_status;
        private ImageView img_cover;
        private CardView card_view;
        private TextView txt_address;
        public CheckBox chkSelected;

        public ContactViewHolder(View v) {
            super(v);

            textONEs = (TextView) v.findViewById(R.id.textONEs);
            txt_amount = (TextView) v.findViewById(R.id.txt_amount);
            txt_status = (TextView) v.findViewById(R.id.txt_status);
            img_cover = (ImageView) v.findViewById(R.id.img_cover);
            card_view = (CardView) v.findViewById(R.id.card_view);
            txt_address = (TextView) v.findViewById(R.id.txt_address);
            txt_company_name = (TextView) v.findViewById(R.id.txt_company_name);
            txt_time = (TextView) v.findViewById(R.id.txt_time);
            chkSelected = (CheckBox) v.findViewById(R.id.chk_selected);
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

    public ArrayList<ModelCheckBoxQuestion> getStudentist() {
        return list;
    }

}