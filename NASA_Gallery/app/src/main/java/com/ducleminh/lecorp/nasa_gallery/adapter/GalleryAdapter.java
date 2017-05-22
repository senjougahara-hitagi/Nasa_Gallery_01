package com.ducleminh.lecorp.nasa_gallery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ducleminh.lecorp.nasa_gallery.R;
import com.ducleminh.lecorp.nasa_gallery.model.NasaModel;

import java.util.ArrayList;

/**
 * Created by Admin on 22/05/2017.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    private ArrayList<NasaModel> mItems;
    private Context mContext;

    public GalleryAdapter(Context context, ArrayList<NasaModel> mItems) {
        mContext = context;
        this.mItems = mItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.gallery_thumb, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NasaModel item = mItems.get(position);
        Glide.with(mContext)
            .load(item.getUrl())
            .thumbnail(0.5f)
            .crossFade()
            .placeholder(R.drawable.loading)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }
}
