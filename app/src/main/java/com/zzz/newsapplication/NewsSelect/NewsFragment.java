package com.zzz.newsapplication.NewsSelect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzz.newsapplication.NewsImageSelect.NewsImageActivity;
import com.zzz.newsapplication.R;
import com.zzz.newsapplication.adapter.ClickListener;
import com.zzz.newsapplication.adapter.NewsListAdapter;
import com.zzz.newsapplication.bean.NewsLink;
import com.zzz.newsapplication.view.PaperView;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

//import com.example.newsapplication.adapter.


/**
 * Created by oruret on 2018/1/30.
 */

public class NewsFragment extends Fragment implements PaperView {

    private boolean isTwoPane;
    public static final String NEWS_BASE_URL = "http://www.hqck.net/";
    private List<NewsLink> mNewsList = new ArrayList<NewsLink>();
    private NewsListAdapter adapter;
    private RecyclerView ry;
    private View view;
    private boolean flagstop = false;
//    private Paperpresenter pp;
    private String html;
    private NewsPresenter mPresenter;
    public NewsFragment() {
        // Requires empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState){

        view = inflater.inflate(R.layout.newslist_frag,container,false);

        findViewById();
        initView();
        if(mNewsList.size() == 0) {
            initData();
        }
        return view;
    }

    public void setPresenter(@NonNull NewsPresenter presenter){
        mPresenter = checkNotNull(presenter);
    }

    public void showNewsDetail(NewsLink news){
        NewsImageActivity.actionStart(getActivity(),news.getTitle(),news.getNewsLink(),news.getColorisblack());
    }

    public void showNewsList(List<NewsLink> newsLinkList){
        adapter.replaceData(newsLinkList);
    }
    public static NewsFragment newInstance(){return new NewsFragment();}

    @Override
    public void initData(){
        mPresenter.loadNews();
    }
    @Override
    public void findViewById() {
        ry = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
    }
    ClickListener.OnRecyclerItemClickListener mItemListener = new ClickListener.OnRecyclerItemClickListener() {
        @Override
        public void click(NewsLink item) {
            mPresenter.openNewsDetail(item);
        }
    };
    public void initView(){
        if(adapter == null){
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            ry.setLayoutManager(layoutManager);

            adapter = new NewsListAdapter(this.mNewsList);
            adapter.setItemClickListener(mItemListener);
            ry.setAdapter(adapter);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isTwoPane = getActivity().findViewById(R.id.news_content_layout) != null;
    }
}
