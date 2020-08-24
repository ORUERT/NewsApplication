/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.newsapplication.home;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.leanback.app.BrowseFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.DividerRow;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.SectionRow;


import com.example.newsapplication.home.model.Card;
import com.example.newsapplication.home.model.CardListRow;
import com.example.newsapplication.home.model.CardRow;
import com.example.newsapplication.R;
import com.jelly.mango.Mango;
import com.jelly.mango.MultiplexImage;

import java.util.ArrayList;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * This fragment will be shown when the "Card Examples" card is selected at the home menu. It will
 * display multiple card types.
 * actually only default card type is used
 */
public class HomePageFragment extends BrowseFragment {

    private ArrayObjectAdapter mRowsAdapter;
    private HomePagePresenter mNewsPresenter;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupUi();
        setupRowAdapter();
    }

    /**
     * set ui and itemclick
     */
    private void setupUi() {
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setOnItemViewClickedListener((viewHolder, item, viewHolder1, row) -> {
            if (!(item instanceof Card)) return;
            if (!(viewHolder.view instanceof ImageCardView)) return;
            CardListRow cardListRow = (CardListRow)getSelectedRowViewHolder().getRowObject();
            openDetialImagePage(cardListRow,item);
        });
        prepareEntranceTransition();
    }

    public void openDetialImagePage(CardListRow cardListRow,Object item){
        Mango.setImages(cardListRow.getCardRow().getmImage());
        Mango.setPosition(((Card) item).getId());
        Mango.setImageSelectListener(index -> Log.d("Mango", "select: " + index));
        try {
            Mango.open(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HomePageFragment newInstance(){return new HomePageFragment();}
    public void setPresenter(@NonNull HomePagePresenter presenter){
        mNewsPresenter = checkNotNull(presenter);
    }
    private void setupRowAdapter() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setAdapter(mRowsAdapter);
        mNewsPresenter.createRows();
        startEntranceTransition();
    }

    /**
     * choose row type
     * default is only choose
     * @param cardRow
     * @return
     */
    Row createCardRow(final CardRow cardRow) {
        List<MultiplexImage> images = new ArrayList<>();
        switch (cardRow.getType()) {
            case CardRow.TYPE_SECTION_HEADER:
                return new SectionRow(new HeaderItem(cardRow.getTitle()));
            case CardRow.TYPE_DIVIDER:
                return new DividerRow();
            case CardRow.TYPE_DEFAULT:
            default:
                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter( new ImageCardViewPresenter(getActivity(), R.style.MovieCardSimpleTheme));
                for (Card card : cardRow.getCards()) {
                    listRowAdapter.add(card);
                    images.add(new MultiplexImage(card.getImageUrl(),card.getImageUrl(),MultiplexImage.ImageType.NORMAL));
                }
                cardRow.setmImage(images);
//                return ListRow(new HeaderItem(cardRow.getTitle()),listRowAdapter);
                return new CardListRow(new HeaderItem(cardRow.getTitle()), listRowAdapter, cardRow);
        }
    }

    ArrayObjectAdapter getmRowsAdapter(){
        return mRowsAdapter;
    }
    @Override
    public void onDestroy() {
        mNewsPresenter.getmyneicun();
        mNewsPresenter = null;
        super.onDestroy();
    }
}
