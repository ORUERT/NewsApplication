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

import android.content.Context;
import android.view.ContextThemeWrapper;

import androidx.leanback.widget.ImageCardView;

import com.bumptech.glide.Glide;
import com.example.newsapplication.home.model.Card;
import com.example.newsapplication.R;

/**
 * A very basic {@link ImageCardView} {@link androidx.leanback.widget.Presenter}.You can
 * pass a custom style for the ImageCardView in the constructor. Use the default constructor to
 * create a Presenter with a default ImageCardView style.
 */
public class ImageCardViewPresenter extends AbstractCardPresenter<ImageCardView> {
//    public ImageBrowserConfig.TransformType transformType = ImageBrowserConfig.TransformType.Transform_Default;
//    public ImageBrowserConfig.IndicatorType indicatorType = ImageBrowserConfig.IndicatorType.Indicator_Number;
//    public ImageBrowserConfig.ScreenOrientationType screenOrientationType = ImageBrowserConfig.ScreenOrientationType.Screenorientation_Default;

    public ImageCardViewPresenter(Context context, int cardThemeResId) {
        super(new ContextThemeWrapper(context, cardThemeResId));
//        context.
    }

    public ImageCardViewPresenter(Context context) {
        this(context, R.style.DefaultCardTheme);
    }

    @Override
    protected ImageCardView onCreateView() {
        ImageCardView imageCardView = new ImageCardView(getContext());

        return imageCardView;
    }

    @Override
    public void onBindViewHolder(Card card, final ImageCardView cardView) {
//        cardView.setTag(card);
        cardView.setTitleText(card.getTitle());
//        cardView.setContentText(card.getDescription());
        int zid = card.getId();
        Glide.with(getContext())
                .asBitmap()
                .load(card.getImageUrl())
//                .apply(options)
                .into(cardView.getMainImageView());
    }

}
