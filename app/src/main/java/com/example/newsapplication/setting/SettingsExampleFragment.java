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

package com.example.newsapplication.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.leanback.preference.LeanbackPreferenceFragment;
import androidx.leanback.preference.LeanbackSettingsFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceScreen;

import com.example.newsapplication.R;
import com.example.newsapplication.home.HomePageActivity;

import static com.example.newsapplication.home.AbsNewsRepository.getmSource;
import static com.example.newsapplication.home.AbsNewsRepository.setSource;

public class SettingsExampleFragment extends LeanbackSettingsFragment {

    @Override
    public void onPreferenceStartInitialScreen() {
        startPreferenceFragment(buildPreferenceFragment(R.xml.prefs, null));
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragment preferenceFragment,
                                             Preference preference) {
        return false;
    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragment preferenceFragment,
                                           PreferenceScreen preferenceScreen) {
        PreferenceFragment frag = buildPreferenceFragment(R.xml.prefs, preferenceScreen.getKey());
        startPreferenceFragment(frag);
        return true;
    }

    private PreferenceFragment buildPreferenceFragment(int preferenceResId, String root) {
        PreferenceFragment fragment = new PrefFragment();
        Bundle args = new Bundle();
        args.putInt("preferenceResource", preferenceResId);
        args.putString("root", root);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static class PrefFragment extends LeanbackPreferenceFragment {

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            String root = getArguments().getString("root", null);
            int prefResId = getArguments().getInt("preferenceResource");
            if (root == null) {
                addPreferencesFromResource(prefResId);
            } else {
                setPreferencesFromResource(prefResId, root);
            }
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }

        /**
         * refresh and change source
         * @param preference
         * @return
         */
        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            final String[] keys = {"haitian_key","jiuwei_key","jiaodu_key","refresh_key"};
            if(preference.getKey().equals(keys[0])&&getmSource()!=1){
                setSource(1);
            }else if(preference.getKey().equals(keys[1])&&getmSource()!=2){
                setSource(2);
            }else if(preference.getKey().equals(keys[2])&&getmSource()!=3){
                setSource(3);
            }
            getActivity().finish();
            Intent intent = new Intent(getActivity(),
                    HomePageActivity.class);
            startActivity(intent);
            return true;
        }

    }
}
