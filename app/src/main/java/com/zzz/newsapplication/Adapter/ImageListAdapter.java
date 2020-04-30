package com.zzz.newsapplication.Adapter;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
//import com.jelly.imagebrowse.R;
import com.maning.imagebrowserlibrary.ImageEngine;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.maning.imagebrowserlibrary.listeners.OnClickListener;
import com.maning.imagebrowserlibrary.listeners.OnLongClickListener;
import com.maning.imagebrowserlibrary.listeners.OnPageChangeListener;
import com.maning.imagebrowserlibrary.model.ImageBrowserConfig;
import com.zzz.newsapplication.R;
import com.jelly.mango.MultiplexImage;
import com.zzz.newsapplication.Utils.GlideImageEngine;

import java.util.ArrayList;
import java.util.List;

/**
 *  图片列表适配器
 * Created by Jelly on 2016/9/3.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageHolder>{

    private ArrayList<String> mImageList;
    private Context context;
    private LayoutInflater inflater;
//    private ClickListener.OnImageItemClickListener itemClickListener;

    public ImageBrowserConfig.TransformType transformType = ImageBrowserConfig.TransformType.Transform_Default;
    public ImageBrowserConfig.IndicatorType indicatorType = ImageBrowserConfig.IndicatorType.Indicator_Number;
    public ImageBrowserConfig.ScreenOrientationType screenOrientationType = ImageBrowserConfig.ScreenOrientationType.Screenorientation_Default;
    //显示自定义遮盖层
    private boolean showCustomShadeView = false;
    //显示ProgressView
    private boolean showCustomProgressView = false;
    //是不是全屏模式
    private boolean isFulScreenMode = false;
    //下拉缩小效果：默认开启true
    private boolean isOpenPullDownGestureEffect = false;
    private int openAnim = R.anim.mn_browser_enter_anim;
    private int exitAnim = R.anim.mn_browser_exit_anim;
    private ImageEngine imageEngine = new GlideImageEngine();

    public ImageListAdapter(Context context, ArrayList<String> imageList) {
        this.context = context;
        this.mImageList = imageList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }
    public void replaceData(ArrayList<String> imageList){
        mImageList = imageList;
        notifyDataSetChanged();
    }
    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(inflater.inflate(R.layout.item_image,parent,false));
    }

    @Override
    public void onBindViewHolder(final ImageHolder holder, final int position) {
        Glide.with(context)
                .load(mImageList.get(position))
                .thumbnail(0.2f)
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MNImageBrowser.with(context)
                        //页面切换效果
                        .setTransformType(transformType)
                        //指示器效果
                        .setIndicatorType(indicatorType)
                        //设置隐藏指示器
                        .setIndicatorHide(false)
                        //设置自定义遮盖层，定制自己想要的效果，当设置遮盖层后，原本的指示器会被隐藏
//                    .setCustomShadeView(showCustomShadeView ? customView : null)
                        //自定义ProgressView，不设置默认默认没有
//                    .setCustomProgressViewLayoutID(showCustomProgressView ? R.layout.layout_custom_progress_view : 0)
                        //当前位置
                        .setCurrentPosition(position)
                        //图片引擎
                        .setImageEngine(imageEngine)
                        //图片集合
                        .setImageList(mImageList)
                        //方向设置
                        .setScreenOrientationType(screenOrientationType)
                        //点击监听
                        .setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(FragmentActivity activity, ImageView view, int position, String url) {

                            }
                        })
                        //长按监听
                        .setOnLongClickListener(new OnLongClickListener() {
                            @Override
                            public void onLongClick(final FragmentActivity activity, final ImageView imageView, int position, String url) {
//                            showListDialog(activity, imageView);
                            }
                        })
                        //页面切换监听
                        .setOnPageChangeListener(new OnPageChangeListener() {
                            @Override
                            public void onPageSelected(int position) {
//                            Log.i(TAG, "onPageSelected:" + position);
////                                    if (tv_number_indicator != null) {
////                                        tv_number_indicator.setText((position + 1) + "/" + MNImageBrowser.getImageList().size());
////                                    }
                            }
                        })
                        //全屏模式
                        .setFullScreenMode(isFulScreenMode)
                        //打开动画
                        .setActivityOpenAnime(openAnim)
                        //关闭动画
                        .setActivityExitAnime(exitAnim)
                        //手势下拉缩小效果
                        .setOpenPullDownGestureEffect(isOpenPullDownGestureEffect)
                        //显示：传入当前View
                        .show(holder.image);
            }
        });
    }

//    public void setItemClickListener(ClickListener.OnImageItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }

    public static class ImageHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private ClickListener.OnImageItemClickListener itemClickListener;

        public ImageHolder(View itemView, ClickListener.OnImageItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        public ImageHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
