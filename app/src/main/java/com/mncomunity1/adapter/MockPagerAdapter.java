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


public class MockPagerAdapter extends InfinitePagerAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;

    private List<ModelVP> mList;


    public void setDataList(List<ModelVP> list) {
        if (list == null || list.size() == 0)
            throw new IllegalArgumentException("list can not be null or has an empty size");
        this.mList = list;
        this.notifyDataSetChanged();
    }


    public MockPagerAdapter(Context context) {
        mContext=context;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.item_infinite_viewpager, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        ModelVP item = mList.get(position);
        holder.position = position;
        holder.name.setText(item.getPost().get(position).getTitle());

       // holder.description.setText(item.getDesc()+"position:"+position);
        String url = "http://mn-community.com"+item.getPost().get(position).getFile_img();

        Glide.with(mContext)
                .load(url)
                .into(holder.image);
        return view;
    }


    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


    private static class ViewHolder {
        public int position;
        TextView name;
        ImageView image;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.item_name);
            image = (ImageView) view.findViewById(R.id.item_image);

        }
    }

}
