package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.NewsRc;
import com.mncomunity1.viewholder.RecyclerViewSimpleTextViewHolder;
import com.mncomunity1.viewholder.ViewHolderPhoto;
import com.mncomunity1.viewholder.ViewHolderText;

import java.util.ArrayList;


public class NewsArrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<NewsRc> list = new ArrayList<>();
    Context context;

    public NewsArrAdapter(Context context, ArrayList<NewsRc> list) {
        this.list = list;
        this.context = context;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        String postType = list.get(position).getArr1().get(position).getType();
        Log.e("getItemViewType", position + ":" + postType);
        switch (postType) {
            case "text":
                return 0;
            case "photo":
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.view_title_text, viewGroup, false);
                viewHolder = new ViewHolderText(v1);
                break;
            case 1:
                View v2 = inflater.inflate(R.layout.view_title_poto, viewGroup, false);
                viewHolder = new ViewHolderPhoto(v2);
                break;

            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        //More to come
        switch (viewHolder.getItemViewType()) {
            case 0:
                ViewHolderText vh1 = (ViewHolderText) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case 1:
                ViewHolderPhoto vh2 = (ViewHolderPhoto) viewHolder;
                configureViewHolder2(vh2, position);
                break;

            default:
                RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }

    private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {
        vh.getLabel().setText((CharSequence) list.get(position));
    }

    private void configureViewHolder1(ViewHolderText vh1, int position) {
        NewsRc item = list.get(position);
        if (item != null) {

            if(item.getArr1().get(position).getType().equals("text")) {

                vh1.tvContent.setText("\n"+item.getArr1().get(position).getContent());


            }
        }
    }

    private void configureViewHolder2(ViewHolderPhoto vh2, int position) {
        //vh2.getThumb().setImageResource(R.drawable.imge);

        final NewsRc item = list.get(position);



        if(item.getArr1() != null){

            String cover = "http://mn-community.com/mc_app/" + item.getArr1().get(position).getCover();

            Glide.with(context)
                    .load(cover)
                    .centerCrop()
                    .crossFade()
                    .into(vh2.imageViewPhoto);

        }



    }

    public String stripHtml(String html)
    {
        return Html.fromHtml(html).toString();
    }




}