package com.zzz.newsapplication.NewsSelect;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshHeaderLayout;
import com.zzz.newsapplication.R;

/**
 * Created by maning on 2017/4/10.
 * 天气下拉刷新
 */
public class WaveRefreshHeaderView extends SwipeRefreshHeaderLayout {

    private int mHeaderHeight;

//    private Animation rotateAnimation;

//    private boolean rotated = false;

    private RelativeLayout iv_sun;
    private TextView tvRefresh;


    public WaveRefreshHeaderView(Context context) {
        this(context, null);
    }

    public WaveRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeaderHeight = getResources().getDimensionPixelOffset(R.dimen.refresh_header_height_80);
//        rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_sun);
//        LinearInterpolator lir = new LinearInterpolator();
//        rotateAnimation.setInterpolator(lir);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvRefresh = (TextView) findViewById(R.id.tvRefresh);
        iv_sun = (RelativeLayout) findViewById(R.id.iv_sun);
    }

    @Override
    public void onRefresh() {
//        iv_sun.clearAnimation();
//        iv_sun.startAnimation(rotateAnimation);
        iv_sun.setVisibility(View.VISIBLE);
        tvRefresh.setText("正在刷新...");
    }

    @Override
    public void onPrepare() {
        Log.d("WeatherRefreshHeader", "onPrepare()");
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (y > mHeaderHeight) {
                tvRefresh.setText("释放刷新");
//                if (!rotated) {
////                    iv_sun.clearAnimation();
////                    iv_sun.startAnimation(rotateAnimation);
//                    rotated = true;
//                }
            } else if (y < mHeaderHeight) {
//                if (rotated) {
//                    rotated = false;
//                }
                tvRefresh.setText("下拉刷新");
            }
        }
    }

    @Override
    public void onRelease() {
        Log.d("WeatherRefreshHeader", "onRelease()");
    }

    @Override
    public void onComplete() {
//        rotated = false;
        tvRefresh.setText("刷新完成");
//        iv_sun.setVisibility(View.GONE);
//        iv_sun.clearAnimation();
    }

    @Override
    public void onReset() {
//        rotated = false;
        tvRefresh.setText("下拉刷新");
    }

}
