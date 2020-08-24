package com.example.newsapplication.home;

import com.example.newsapplication.home.model.Card;
import com.example.newsapplication.home.model.CardRow;
import com.example.newsapplication.home.model.NewsLinkModel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.newsapplication.home.model.Card.Type.MOVIE;


public class HomePagePresenter {
    private HomePageRepository mNewsRepository;
    private HomePageFragment mNewsFragment;

    public HomePagePresenter(HomePageRepository newsRepository, HomePageFragment newsFragment){
        mNewsRepository = newsRepository;
        mNewsFragment = newsFragment;
        mNewsFragment.setPresenter(this);
        mNewsFragment.setTitle(AbsNewsRepository.getmSiteName());
    }

    /**
     * delete reference in view
     */
    public void getmyneicun(){
        mNewsFragment = null;
        mNewsRepository = null;
    }

    /**
     * get info from repository and create row in homepage
     */
    public void createRows() {
        mNewsFragment.getmRowsAdapter().clear();
        mNewsRepository.getNewsUrlList().doOnNext(newsLinkModels -> {
            Observable.fromArray(newsLinkModels)
                    .subscribeOn(Schedulers.io())
                    .flatMapIterable(it->it)
                    .observeOn(Schedulers.newThread())
                    .doOnNext(this::fillRow).subscribe();
        }).subscribe();
    }
    public void fillRow(NewsLinkModel newsLinkModel){
        final int[] count = {1};
        mNewsRepository.getImageUrlList(newsLinkModel.getNewsLink())
                .subscribeOn(Schedulers.io())
                .map(it -> new Card(MessageFormat.format("第{0}页", count[0]++), it,MOVIE,"card_image_movie_01", count[0] -2))
                .collectInto(new ArrayList<>(), List::add)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(cards -> mNewsFragment.getmRowsAdapter().add(mNewsFragment.createCardRow(new CardRow(newsLinkModel.getTitle(), (List)cards))))//Title may wrong
                .subscribe();
    }
}
