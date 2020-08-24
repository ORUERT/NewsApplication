package com.jelly.mango;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
//import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.jelly.mango.adapter.ViewPageAdapter;
import com.jelly.mango.presenter.ImageBrowsePresenter;
import com.jelly.mango.util.StatusBarUtils;
import com.jelly.mango.view.ImageBrowseView;

import java.util.List;

/**
 * Mango main Activity
 * Created by Jelly on 2016/9/3.
 */
public class ImageBrowseActivity extends Activity implements ViewPager.OnPageChangeListener,View.OnClickListener,ImageBrowseView {
    private static float DEFAULT_MAX_SCALE = 3.4f;
//    private static float DEFAULT_MAX_SCALE = 3.0f;
    private static float DEFAULT_MID_SCALE = 1.75f;
    private static float DEFAULT_MIN_SCALE = 1.0f;
    private float mMinScale = DEFAULT_MIN_SCALE;
    private float mMidScale = DEFAULT_MID_SCALE;
    private float mMaxScale = DEFAULT_MAX_SCALE;
    private int mHeightBound;
    private int mWidthBound;
    float scale = 1f;

    private static final String TAG = ImageBrowseActivity.class.getName();
    private ViewPager vp;
    private TextView hint;
    private TextView save;
    private TextView origin;
    private ViewPageAdapter adapter;
    ImageBrowsePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_image_browse);
        initView();
//        hideToolBar();
        setStatusBar();
        loadPresenter();
        presenter.loadImage();

    }

    /**
     * listen keyevent except homekey
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction()!=KeyEvent.ACTION_UP){
            float y;float x;
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    y = vp.getTranslationY();
                    if(y-100>=-mHeightBound)vp.setTranslationY(y-100);
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    y = vp.getTranslationY();
                    if(y+100<=mHeightBound)vp.setTranslationY(y+100);
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT://只在最大化中进行X轴移动,不进行翻页
                    x = vp.getTranslationX();
                    if(scale == getMaximumScale()){
                        if((x+100)<=mWidthBound)vp.setTranslationX(x+100);
                        return true;
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    x = vp.getTranslationX();
                    if(scale == getMaximumScale()){
                        if((x-100)>=-mWidthBound)vp.setTranslationX(x-100);
                        return true;
                    }
                    break;
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    break;
                case KeyEvent.KEYCODE_ENTER:     //确定键enter 对边界进行硬编码
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    try {
                        if (scale < getMediumScale()) {
                            scale = getMediumScale();
                            mHeightBound = 400;
                        } else if (scale >= getMediumScale() && scale < getMaximumScale()) {
                            scale = getMaximumScale();
                            mHeightBound = (int) ((vp.getHeight()+20)*1.26);
                            mWidthBound = 250;
                        } else {
                            vp.setTranslationX(0);
                            vp.setTranslationY(0);
                            scale = getMinimumScale();
                        }
                        ViewCompat.animate(vp)
                                .scaleX(scale)
                                .scaleY(scale)
                                .start();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // Can sometimes happen when getX() and getY() is called
                    }
                    break;
            }
            return super.dispatchKeyEvent(event);
        }
        return true;
    }

    public float getMinimumScale() {
        return mMinScale;
    }

    public float getMediumScale() {
        return mMidScale;
    }

    public float getMaximumScale() {
        return mMaxScale;
    }
    /**
     * init view
     */
    private void initView(){
        vp = (ViewPager) this.findViewById(R.id.viewPager);
        hint = (TextView) this.findViewById(R.id.hint);
        save = (TextView) this.findViewById(R.id.save);
        origin = (TextView) this.findViewById(R.id.origin);
        save.setOnClickListener(this);
        origin.setOnClickListener(this);
    }

//    /**
//     * hide toolbar
//     */
//    private void hideToolBar(){
//        if(getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
//    }

    /**
     * set status bar color
     */
    private void setStatusBar(){
        StatusBarUtils.setStatusBar(this,Color.BLACK);
    }

    public void loadPresenter(){
        presenter = new ImageBrowsePresenter(this);
    }

    @Override
    public Intent getDataIntent() {
        return getIntent();
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public int getPosition() {
        return adapter.getPosition();
    }

    @Override
    public void setImageBrowse(List<MultiplexImage> images,int position) {
        if(adapter == null && images != null && images.size() != 0){
            adapter = new ViewPageAdapter(this,images);
            hiddenOriginalButton(position);
            vp.setAdapter(adapter);
            vp.setCurrentItem(position);
            vp.addOnPageChangeListener(this);
            adapter.setPosition(position);
            hint.setText(position + 1 + "/" + images.size());

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void hiddenOriginalButton(int position){
        //if the image is load original,hidden show original button
        if(TextUtils.isEmpty(presenter.getImages().get(position).getOPath()) || presenter.getImages().get(position).isLoading()){
            origin.setVisibility(View.GONE);
        }else{
            origin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageSelected(int position) {
        adapter.setPosition(position);
        hint.setText(position + 1 + "/" + presenter.getImages().size());
        if(Mango.imageSelectListener != null){
            Mango.imageSelectListener.select(position);
        }
        hiddenOriginalButton(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state == 0 && adapter.getPrePosition() != vp.getCurrentItem()){
            adapter.updatePhotoView(adapter.getPrePosition());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.save){
            presenter.saveImage();
        }else if(id == R.id.origin){
            adapter.loadOriginalPicture();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Mango.imageSelectListener = null;
    }
}
