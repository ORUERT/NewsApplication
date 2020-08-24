package com.example.newstvapplication.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import androidx.annotation.NonNull;

import static com.bumptech.glide.util.Preconditions.checkNotNull;
public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

}