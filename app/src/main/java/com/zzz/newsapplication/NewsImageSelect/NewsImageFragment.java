package com.zzz.newsapplication.NewsImageSelect;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jelly.mango.ImageSelectListener;
import com.jelly.mango.Mango;
import com.jelly.mango.MultiplexImage;
import com.zzz.newsapplication.R;
import com.zzz.newsapplication.Adapter.ClickListener;
import com.zzz.newsapplication.Adapter.ImageListAdapter;
import com.zzz.newsapplication.Utils.NewsInterface;
//import com.zzz.newsapplication.view.PaperView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class NewsImageFragment extends Fragment{

    private View view;
    private RecyclerView mRv;

    private ArrayList<String> mImageList = new ArrayList<String>();
    private ImageListAdapter mAdapter;

    private NewsImgPresenter mImgPresenter;
    private ConcurrentHashMap<Integer,String> ordImageList = new ConcurrentHashMap<>();

    private int mPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.news_detail_frag, container, false);

//        Log.e("pagelink",newsLink);

            findViewById();
            initView();
            initData();

            return view;
        }
    @Override
    public void onDestroy () {
        super.onDestroy();
        mImageList.clear();
        ordImageList.clear();
    }

    public void setPresenter (NewsImgPresenter newsImgPresenter){
        mImgPresenter = newsImgPresenter;
    }

    public static NewsImageFragment newsInstance (){
        return new NewsImageFragment();
    }

    public void showImageList (ArrayList < String > imageList) {
        mAdapter.replaceData(imageList);
    }


    public void refresh (String newsTitle, String newsLink,int position, boolean complete){

    }

    public void findViewById() {
        mRv = (RecyclerView) view.findViewById(R.id.news_content_recycler_view);
    }
    NewsInterface.NetworkWithPosCallback networkCallback = new NewsInterface.NetworkWithPosCallback() {
        @Override
        public void onGetSuccess(int position,String resHtml) {
            Document doc = Jsoup.parse(resHtml);
            //获取报纸图片信息
            Elements image_doc = doc.getElementsByTag("img");
            String imgsrc = image_doc.get(0).attr("src");

            Elements title_doc = doc.getElementsByTag("meta");
            String title = title_doc.get(0).attr("content");

            Log.i("warmming","abc"+imgsrc);

            ordImageList.put(position,imgsrc);

            final ArrayList<String> imageList = new ArrayList<>();
            imageList.addAll(ordImageList.values());
            mImageList = imageList;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mImgPresenter.replaceImage(imageList);
                }
            });
        }
        @Override
        public void onGetFail(Exception ex) {

        }
    };


    public void initData() {
        mImgPresenter.loadImages(networkCallback);
    }
//    ClickListener.OnImageItemClickListener onRecyclerItemClickListener = new ClickListener.OnImageItemClickListener() {
//        @Override
//        public void click(int position) {
//            //test Mange static
////            Log.e(position,mImageList)
//            Mango.setImages(mImageList);
//
//            Mango.setPosition(position);
//            Mango.setImageSelectListener(new ImageSelectListener() {
//                @Override
//                public void select(int index) {
//                    Log.d("Mango", "select: " + index);
//                }
//            });
//            try {
//                Mango.open(getContext());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
    public void initView() {
        if (mAdapter == null) {
            mRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            mRv.setItemAnimator(new DefaultItemAnimator());
            mAdapter = new ImageListAdapter(getContext(), mImageList);
//            mAdapter.setItemClickListener(onRecyclerItemClickListener);
            mRv.setAdapter(mAdapter);
        }
    }

}
