package com.mncomunity1.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mncomunity1.R;


public class ViewHolderText extends RecyclerView.ViewHolder {


    public TextView tvContent;


    public ViewHolderText(View v) {
        super(v);

        tvContent = (TextView) v.findViewById(R.id.img_title);

    }


}
