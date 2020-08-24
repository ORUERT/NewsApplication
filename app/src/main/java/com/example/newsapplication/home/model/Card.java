/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.example.newsapplication.home.model;

import com.google.gson.annotations.SerializedName;

/**
 * This is a generic example of a custom data object, containing info we might want to keep with
 * each card on the home screen
 */
public class Card {

    @SerializedName("title") private String mTitle = "";
    @SerializedName("card") private String mImageUrl;
    @SerializedName("localImageResource") private String mLocalImageResource = null;
    @SerializedName("type") private Card.Type mType;
    @SerializedName("id") private int mId;
    public  Card(){

    }
    public Card(String title, String url, Card.Type type, String local,int id){
        mTitle = title;
        mImageUrl = url;
        mType = type;
        mLocalImageResource = local;
        mId = id;
    }
    public String getTitle() {
        return mTitle;
    }

    public int getId() {
        return mId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public enum Type {

        MOVIE_COMPLETE,
        MOVIE,
        MOVIE_BASE,
        ICON,
        SQUARE_BIG,
        SINGLE_LINE,
        GAME,
        SQUARE_SMALL,
        DEFAULT,
        SIDE_INFO,
        SIDE_INFO_TEST_1,
        TEXT,
        CHARACTER,
        GRID_SQUARE,
        VIDEO_GRID

    }

}
