package com.zzz.newsapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
//import com.jelly.imagebrowse.R;
import com.zzz.newsapplication.R;
import com.jelly.mango.MultiplexImage;

import java.util.List;

/**
 *  图片列表适配器
 * Created by Jelly on 2016/9/3.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageHolder>{

    private List<MultiplexImage> mImageList;
    private Context context;
    private LayoutInflater inflater;
    private ClickListener.OnImageItemClickListener itemClickListener;

    public ImageListAdapter(Context context, List<MultiplexImage> imageList) {
        this.context = context;
        this.mImageList = imageList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }
    public void replaceData(List<MultiplexImage> imageList){
        mImageList = imageList;
        notifyDataSetChanged();
    }
    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(inflater.inflate(R.layout.item_image,parent,false),itemClickListener);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, final int position) {
        Glide.with(context)
                .load(mImageList.get(position).getTPath())
                .thumbnail(0.2f)
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.click(position);
            }
        });
    }

    public void setItemClickListener(ClickListener.OnImageItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static class ImageHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private ClickListener.OnImageItemClickListener itemClickListener;

        public ImageHolder(View itemView, ClickListener.OnImageItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            image = (ImageView) itemView.findViewById(R.id.image);
        }

    }

}
