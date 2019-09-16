package com.taxi.friend.taxifriendclient.ui.driver;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taxi.friend.taxifriendclient.R;

import java.util.List;

public class DriverPagerAdapter extends PagerAdapter {

    private List<DriverPicture> images;
    private Context mContext;
    public DriverPagerAdapter(Context context, List<DriverPicture> images) {
        this.images = images;
        mContext = context;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.swipe_layout, container, false);
        //HomeViewPagerBannerBinding bannerBinding = DataBindingUtil.bind(inflater.inflate(R.layout.home_view_pager_banner, view, false));//HomeViewPagerBannerBinding.bind(view);
        //bannerBinding.setData(images.get(position));
        //bannerBinding.executePendingBindings();
        TextView text = itemView.findViewById(R.id.description);
        text.setText(images.get(position).getDescription());

        container.addView(text);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
