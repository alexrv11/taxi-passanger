package com.taxi.friend.taxifriendclient.ui.driver;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxi.friend.taxifriendclient.R;
import com.taxi.friend.taxifriendclient.models.DriverData;

import java.util.ArrayList;
import java.util.List;

public class DriverFragment extends Fragment {

    private DriverViewModel mViewModel;
    private ViewPager viewPager;
    private DriverPagerAdapter adapter;

    public static DriverFragment newInstance() {
        return new DriverFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.driver_fragment, container, false);


        viewPager = view.findViewById(R.id.driver_pager);

        List<DriverPicture> images = new ArrayList<>();
        images.add(new DriverPicture("test 1"));
        images.add(new DriverPicture("test 2"));
        images.add(new DriverPicture("test 3"));

        adapter = new DriverPagerAdapter(inflater.getContext(), images);
        viewPager.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DriverViewModel.class);
        mViewModel.getDriver().observe(this, driver ->{

        });
    }

}
