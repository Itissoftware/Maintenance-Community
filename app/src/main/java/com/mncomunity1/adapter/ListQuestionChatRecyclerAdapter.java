package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mncomunity1.R;
import com.mncomunity1.model.GetMsgChat;
import com.mncomunity1.model.QuestionListVendor;

import java.util.ArrayList;

public class ListQuestionChatRecyclerAdapter extends RecyclerView.Adapter<ListQuestionChatRecyclerAdapter.ContactViewHolder> {

    Context context;
    ArrayList<GetMsgChat> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public ListQuestionChatRecyclerAdapter(Context context, ArrayList<GetMsgChat> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, final int i) {
        final GetMsgChat item = list.get(i);

        contactViewHolder.textONEs.setText("ข้อความ :"+item.getTotal().get(i).getMessage());

//        String cover = "http://mn-community.com/web/api/upload/" + item.getTotal().get(i).getPath_photo();
//        Log.e("cover",cover);
//        Glide.with(context)
//                .load(cover)
//                .crossFade()
//                .into(contactViewHolder.img_cover);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_question_chat, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textONEs;
        private ImageView img_cover;
        private CardView card_view;

        public ContactViewHolder(View v) {
            super(v);

            textONEs = (TextView) v.findViewById(R.id.textONEs);
            img_cover = (ImageView) v.findViewById(R.id.img_cover);
            card_view = (CardView) v.findViewById(R.id.card_view);
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