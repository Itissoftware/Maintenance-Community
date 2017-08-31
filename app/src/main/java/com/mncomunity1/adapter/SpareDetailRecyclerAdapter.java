package com.mncomunity1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.model.ModelSpareDetails;
import com.mncomunity1.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class SpareDetailRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static Context context;
    List<MovieModel> movies;
    public static OnItemClickListener mItemClickListener;

    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 1;

    public static  OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    public SpareDetailRecyclerAdapter(Context context, List<MovieModel> movies) {
        this.context = context;
        this.movies = movies;

    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_MOVIE) {
            return new MovieHolder(inflater.inflate(R.layout.item_spare_details, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == TYPE_MOVIE) {
            ((MovieHolder) holder).bindData(movies.get(position));
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).type.equals("company")) {
            return TYPE_MOVIE;
        } else {
            return TYPE_LOAD;
        }
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

    }

    public void SetOnItemVideiosClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    static class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image_logo;
        TextView title_tv;
        TextView title_address;
        LinearLayout ls_onclick;

        public MovieHolder(View v) {
            super(v);
            image_logo = (ImageView) v.findViewById(R.id.image_logo);
            title_tv = (TextView) v.findViewById(R.id.title_tv);
            title_address = (TextView) v.findViewById(R.id.title_address);
            ls_onclick = (LinearLayout) v.findViewById(R.id.ls_onclick);
            v.setOnClickListener(this);
            ls_onclick.setOnClickListener(this);
        }

        void bindData(MovieModel movieModel) {

            title_tv.setText(movieModel.nameen);
            title_address.setText(movieModel.address);

            if (movieModel.cover != null) {
                String url = "http://mn-community.com/admin_mc/" + movieModel.cover;
                Glide.with(context)
                        .load(url)
                        .centerCrop()
                        .crossFade()
                        .into(image_logo);

            }
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

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }


}