package com.zzz.newsapplication.NewsSelect;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.zzz.newsapplication.Adapter.ClickListener;
import com.zzz.newsapplication.Adapter.NewsListAdapter;
import com.zzz.newsapplication.Bean.NewsLink;
import com.zzz.newsapplication.NewsImageSelect.NewsImageActivity;
import com.zzz.newsapplication.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;



/**
 * Created by oruret on 2018/1/30.
 */

public class NewsFragment extends Fragment implements OnRefreshListener {

    public static final String NEWS_BASE_URL = "http://www.hqck.net/";
    private SwipeToLoadLayout swipeToLoadLayout;
    private FloatingActionButton floatingActionButton1;
    private FloatingActionButton floatingActionButton2;
    private FloatingActionButton floatingActionButton3;
    private FloatingActionMenu floatingActionMenu;
    private List<NewsLink> mNewsList = new ArrayList<NewsLink>();
    private NewsListAdapter adapter;
    private RecyclerView ry;
    private TextView failurepage;
    private Toolbar toolbar;
    private View view;
    private NewsPresenter mPresenter;
    private String updateJson = "";

    public NewsFragment() {
        // Requires empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState){

        view = inflater.inflate(R.layout.newslist_frag,container,false);
        setHasOptionsMenu(true);
        findViewById();
        initView();
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        if(mNewsList.size() == 0) {
            initData();
        }
        checkUpdate();

        return view;
    }
    /* 懒得写接口了就丢在这里把*/
    public void checkUpdate(){
        OkHttpClient client =  new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();
        Request request = new Request.Builder()
                .url("https://github.com/ORUERT/NewsApplication/blob/master/readme.md")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("UpdateCheckError", e.toString());
                showFailuePage();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] responseByte = response.body().bytes();
                String responseData = new String(responseByte, StandardCharsets.UTF_8);
                Document doc = Jsoup.parse(responseData);
                Element readme = doc.getElementById("readme");
                updateJson = readme.text();
                AppUpdateUtils.getInstance().checkUpdate(updateJson);
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_layout, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
    public void showFailuePage(){
        ry.setVisibility(View.INVISIBLE);
        failurepage.setVisibility(View.VISIBLE);
    }
    public void hideFailuePage(){
        ry.setVisibility(View.VISIBLE);
        failurepage.setVisibility(View.INVISIBLE);
    }
    public static NewsFragment newInstance(){return new NewsFragment();}

    public void initData(){
        mPresenter.loadNews();

    }
    public void findViewById() {
        toolbar = (Toolbar) view.findViewById(R.id.tool_bar_2);
        ry = (RecyclerView) view.findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        floatingActionButton1 = (FloatingActionButton) view.findViewById(R.id.menu_item1);
        floatingActionButton2 = (FloatingActionButton) view.findViewById(R.id.menu_item2);
        floatingActionButton3 = (FloatingActionButton) view.findViewById(R.id.menu_item3);
        floatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.menu);
        failurepage = (TextView) view.findViewById(R.id.failurepage);
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NewsActivity temp = (NewsActivity)getActivity();
//            getActivity().onBackPressed();//销毁自己
            switch (v.getId()) {
                case R.id.menu_item1:
                    mSetRefreshing(true);
                    assert temp != null;
                    temp.reLoadFragView(1);
                    break;
                case R.id.menu_item2:
                    mSetRefreshing(true);
                    assert temp != null;
                    temp.reLoadFragView(2);
                    break;
                case R.id.menu_item3:
                    mSetRefreshing(true);
                    assert temp != null;
                    temp.reLoadFragView(3);
                    break;
            }
            floatingActionMenu.close(false);

        }
    };
    ClickListener.OnRecyclerItemClickListener mItemListener = new ClickListener.OnRecyclerItemClickListener() {
        @Override
        public void click(NewsLink item) {
            mPresenter.openNewsDetail(item);
        }
    };



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //自定义UI
        switch (item.getItemId()) {
            case R.id.action_01:
                if(TextUtils.isEmpty(updateJson)){
                    Toast.makeText(getContext(),"已经是最新版本",Toast.LENGTH_SHORT).show();
                }else {
                    AppUpdateUtils.getInstance().checkUpdate(updateJson);
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public void initView(){
        if(adapter == null){
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            ry.setLayoutManager(layoutManager);
            swipeToLoadLayout.setOnRefreshListener(this);
            adapter = new NewsListAdapter(this.mNewsList);
            adapter.setItemClickListener(mItemListener);
            ry.setAdapter(adapter);
            floatingActionButton1.setOnClickListener(clickListener);
            floatingActionButton2.setOnClickListener(clickListener);
            floatingActionButton3.setOnClickListener(clickListener);

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                mPresenter.loadNews();

            }
        }, 1000);

    }
    public void mSetRefreshing(boolean flag){
        swipeToLoadLayout.setRefreshing(flag);
    }
}
