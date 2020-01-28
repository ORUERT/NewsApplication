package com.zzz.newsapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
    private List<NewsLink> mNewsLinkList;
    private ClickListener.OnRecyclerItemClickListener itemClickListener;
    private Context mContext;
    public enum ITEM_TYPE {
        ITEM_BLACK,
        ITEM_RED
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitleText;
        private ClickListener.OnRecyclerItemClickListener itemClickListener;

        public ViewHolder(View view, ClickListener.OnRecyclerItemClickListener itemClickListener){
            super(view);
            this.itemClickListener = itemClickListener;
            newsTitleText = (TextView) view.findViewById(R.id.news_title);
            mContext = view.getContext();
        }
    }
    public NewsListAdapter(List<NewsLink> newsLinkList){
        mNewsLinkList = newsLinkList;
    }
    public void replaceData(List<NewsLink> newsLinkList){
        mNewsLinkList = newsLinkList;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType){
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_red,parent,false);
        final ViewHolder holder = new ViewHolder(view,itemClickListener);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder , final int position){
        NewsLink newsLink = mNewsLinkList.get(position);
        holder.newsTitleText.setText(newsLink.getTitle());//绑定recyclerlayout中item的title
        if(mNewsLinkList.get(position).getColorisblack())holder.newsTitleText.setTextColor(mContext.getResources().getColor(R.color.colorItemBlue));
        else holder.newsTitleText.setTextColor(mContext.getResources().getColor(R.color.colorItemOrg));
        holder.newsTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.click(mNewsLinkList.get(position));
            }
        });
    }
    @Override
    public int getItemViewType(int position) {
        boolean colorisblack = mNewsLinkList.get(position).getColorisblack();
//        Log.i("get", String.valueOf(colorisblack)+position+mNewsLink.get(position).getTitle());
        return colorisblack ? ITEM_TYPE.ITEM_BLACK.ordinal() : ITEM_TYPE.ITEM_RED.ordinal();
    }

    @Override
    public int getItemCount(){
        return mNewsLinkList.size();
    }
    public void setItemClickListener(ClickListener.OnRecyclerItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
