package com.zzz.newsapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzz.newsapplication.R;
import com.zzz.newsapplication.bean.NewsLink;

import java.util.List;

/**
 * Created by Administrator on 2018/2/14.
 */

public class NewsListAdapter  extends RecyclerView.Adapter<NewsListAdapter.ViewHolder>{
    private List<NewsLink> mNewsLink;
    private OnRecyclerItemClickListener itemClickListener;

    public enum ITEM_TYPE {
        ITEM_BLACK,
        ITEM_RED
    }
    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        TextView newsTitleText;
        private OnRecyclerItemClickListener itemClickListener;

        public ViewHolder(View view,OnRecyclerItemClickListener itemClickListener){
            super(view);
            this.itemClickListener = itemClickListener;
            view.setOnClickListener(this);
            newsTitleText = (TextView) view.findViewById(R.id.news_title);
        }
        @Override
        public void onClick(View view){
            if(itemClickListener == null)return;
            itemClickListener.click(view,getAdapterPosition());
        }
    }
    public NewsListAdapter(List<NewsLink> newsLink){
        mNewsLink = newsLink;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType){
        View view;
        if(viewType == ITEM_TYPE.ITEM_RED.ordinal()){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_red,parent,false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_black,parent,false);
        }
        final ViewHolder holder = new ViewHolder(view,itemClickListener);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder , int position){
        NewsLink newsLink = mNewsLink.get(position);
        holder.newsTitleText.setText(newsLink.getTitle());//绑定recyclerlayout中item的title
    }
    @Override
    public int getItemViewType(int position) {
        boolean colorisblack = mNewsLink.get(position).getColorisblack();
//        Log.i("get", String.valueOf(colorisblack)+position+mNewsLink.get(position).getTitle());
        return colorisblack ? ITEM_TYPE.ITEM_BLACK.ordinal() : ITEM_TYPE.ITEM_RED.ordinal();
    }

    @Override
    public int getItemCount(){
        return mNewsLink.size();
    }
    public void setItemClickListener(OnRecyclerItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
