package com.zzz.newsapplication.NewsSelect;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.cretin.www.cretinautoupdatelibrary.model.TypeConfig;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateConfig;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import com.zzz.newsapplication.Bean.UpdateModel;
import com.zzz.newsapplication.R;
import com.zzz.newsapplication.Utils.ActivityUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.zzz.newsapplication.Utils.SSLUtil.trustAllHosts;
import static com.zzz.newsapplication.Utils.SSLUtil.trustEveryone;

public class NewsActivity extends AppCompatActivity {
    NewsPresenter mNewsPresenter;
    NewsFactory mNewsFactory;
    NewsFragment mNewsFragment;
    private String updateUrl = "https://github.com/ORUERT/NewsApplication";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.newslist_act);
        mNewsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mNewsFragment == null) {
            // Create the fragment
            mNewsFragment = NewsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mNewsFragment, R.id.contentFrame);
        }
        trustAllHosts();
        mNewsFactory = new NewsFactory();
        mNewsPresenter = new NewsPresenter(mNewsFactory.getNewsRepository(1),mNewsFragment);
        initUpdata();

    }
    private void initUpdata(){
        UpdateConfig updateConfig = new UpdateConfig();
        updateConfig.setDataSourceType(TypeConfig.DATA_SOURCE_TYPE_JSON);
        updateConfig.setModelClass(new UpdateModel());
        updateConfig.setUiThemeType(TypeConfig.UI_THEME_CUSTOM);
        updateConfig.setCustomActivityClass(CustomActivity.class);
        AppUpdateUtils.init(getApplication(),updateConfig);
        //初始化
//        更新库配置
//        UpdateConfig updateConfig = new UpdateConfig()
//                .setDebug(true)//是否是Debug模式
//                .setBaseUrl("https://raw.githubusercontent.com/ORUERT/NewsApplication/master/readme.md")//当dataSourceType为DATA_SOURCE_TYPE_URL时，配置此接口用于获取更新信息
//                .setMethodType(TypeConfig.METHOD_GET)//当dataSourceType为DATA_SOURCE_TYPE_URL时，设置请求的方法
//                .setDataSourceType(TypeConfig.DATA_SOURCE_TYPE_URL)//设置获取更新信息的方式
//                .setShowNotification(true)//配置更新的过程中是否在通知栏显示进度
//                .setUiThemeType(TypeConfig.UI_THEME_AUTO)//配置UI的样式，一种有12种样式可供选择
//                .setRequestHeaders(null)//当dataSourceType为DATA_SOURCE_TYPE_URL时，设置请求的请求头
//                .setRequestParams(null)//当dataSourceType为DATA_SOURCE_TYPE_URL时，设置请求的请求参数
//                .setAutoDownloadBackground(false)//是否需要后台静默下载，如果设置为true，则调用checkUpdate方法之后会直接下载安装，不会弹出更新页面。当你选择UI样式为TypeConfig.UI_THEME_CUSTOM，静默安装失效，您需要在自定义的Activity中自主实现静默下载，使用这种方式的时候建议setShowNotification(false)，这样基本上用户就会对下载无感知了
//                .setCustomActivityClass(CustomActivity.class)//如果你选择的UI样式为TypeConfig.UI_THEME_CUSTOM，那么你需要自定义一个Activity继承自RootActivity，并参照demo实现功能，在此处填写自定义Activity的class
//                .setNeedFileMD5Check(false)//是否需要进行文件的MD5检验，如果开启需要提供文件本身正确的MD5校验码，DEMO中提供了获取文件MD5检验码的工具页面，也提供了加密工具类Md5Utils
////                .setCustomDownloadConnectionCreator(new OkHttp3Connection.Creator(builder))//如果你想使用okhttp作为下载的载体，可以使用如下代码创建一个OkHttpClient，并使用demo中提供的OkHttp3Connection构建一个ConnectionCreator传入，在这里可以配置信任所有的证书，可解决根证书不被信任导致无法下载apk的问题
//                .setModelClass(new UpdateModel());
//        AppUpdateUtils.init(getApplication(),updateConfig);
    }

    public void reLoadFragView(int source){
        AbsNewsRepository temp = mNewsFactory.getNewsRepository(source);
        temp.setSource(source);
        mNewsPresenter = new NewsPresenter(mNewsFactory.getNewsRepository(source),mNewsFragment);
    }

}
