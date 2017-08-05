package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;

import java.util.ArrayList;

public class MyImagePagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> list = new ArrayList<>();
    LayoutInflater layoutInflater;


    public MyImagePagerAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_image_view_pager, container, false);
        final String c = list.get(position);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_view);

        //  imageView.setImageResource(images[position]);

//        Log.e("getUserId",c.getUserId());

        String url = "http://mn-community.com/admin_mc/"+c.toString();


        Glide.with(context)
                .load(url)
                .into(imageView);



        container.addView(itemView);



        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}