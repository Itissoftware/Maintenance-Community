package com.mncomunity1.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mncomunity1.R;


public class ViewHolderPhoto extends RecyclerView.ViewHolder {
    public ImageView imageViewPhoto;

    public ViewHolderPhoto(View itemView) {
        super(itemView);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.img_photo_title);
    }

}