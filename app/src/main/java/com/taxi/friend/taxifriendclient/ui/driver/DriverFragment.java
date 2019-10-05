package com.taxi.friend.taxifriendclient.ui.driver;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxi.friend.taxifriendclient.R;

public class DriverFragment extends Fragment {

    private DriverViewModel mViewModel;
    private ViewPager viewPager;

    public static DriverFragment newInstance() {
        return new DriverFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.driver_fragment, container, false);


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
