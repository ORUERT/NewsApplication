package com.example.newsapplication.home;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.newsapplication.util.ActivityUtils;
import com.example.newsapplication.util.HomeListen;
import com.example.newsapplication.R;
import com.example.newsapplication.setting.SettingsExampleActivity;


public class HomePageActivity extends Activity {
    private HomeListen mHomeListen = null;
    HomePagePresenter mNewsPresenter;
    HomePageFragment mNewsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_example);
        initReceiver();
        mNewsFragment = (HomePageFragment)getFragmentManager() .findFragmentById(R.id.cardsFragment);
        if (mNewsFragment == null) {
            // Create the fragment
            mNewsFragment = HomePageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getFragmentManager(), mNewsFragment, R.id.cardsFragment);
        }
        mNewsPresenter = new HomePagePresenter(new HomePageRepository(),mNewsFragment);
    }

    /**
     * handle menu and start setting page
     * @param event
     * @return super.dispatchKeyEvent(event)
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP&&event.getKeyCode() == KeyEvent.KEYCODE_MENU){
            Intent intent = new Intent(getBaseContext(),
                    SettingsExampleActivity.class);
            startActivity(intent);
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 注册广播
     */
    private void initReceiver() {
        mHomeListen = new HomeListen( this );
        mHomeListen.setOnHomeBtnPressListener( new HomeListen.OnHomeBtnPressLitener( ) {
            @Override
            public void onHomeBtnPress() {
                finish();//可能出现问题
                showToast( "按下Home按键！" );
            }
            @Override
            public void onHomeBtnLongPress() {
                showToast( "长按Home按键！" );
            }
        });

    }
    private void showToast( String toastInfoStr ){
        Toast.makeText( this, toastInfoStr, Toast.LENGTH_LONG ).show( );
    }

    @Override
    protected void onResume(){
        super.onResume();
        mHomeListen.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mHomeListen.stop();
    }
}
