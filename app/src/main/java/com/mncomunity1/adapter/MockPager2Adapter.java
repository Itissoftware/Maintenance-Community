package com.mncomunity1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.ModelVP;
import com.zanlabs.widget.infiniteviewpager.InfinitePagerAdapter;

import java.util.List;


public class MockPager2Adapter extends InfinitePagerAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;

    private List<String> mList;


    public void setDataList(List<String> list) {
        if (list == null || list.size() == 0)
            throw new IllegalArgumentException("list can not be null or has an empty size");
        this.mList = list;
        this.notifyDataSetChanged();
    }


    public MockPager2Adapter(Context context) {
        mContext=context;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.item_image_view_pager, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        String item = mList.get(position);
        holder.position = position;
        String url = "http://mn-community.com/admin_mc/"+item.toString();
        Log.e("url adapter",item);

       // holder.description.setText(item.getDesc()+"position:"+position);
        Glide.with(mContext)
                .load(item)
                .into(holder.image);
        return view;
    }


    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


    private static class ViewHolder {
        public int position;

        ImageView image;

        public ViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.img_view);

        }
    }

}
