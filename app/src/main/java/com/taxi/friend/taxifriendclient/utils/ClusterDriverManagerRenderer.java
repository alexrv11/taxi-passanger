package com.taxi.friend.taxifriendclient.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.taxi.friend.taxifriendclient.R;
import com.taxi.friend.taxifriendclient.models.ClusterDriver;

public class ClusterDriverManagerRenderer extends DefaultClusterRenderer<ClusterDriver> {

    private final IconGenerator iconGenerator;
    private final ImageView imageView;


    public ClusterDriverManagerRenderer(Context context, GoogleMap map, ClusterManager<ClusterDriver> clusterManager) {
        super(context, map, clusterManager);

        this.iconGenerator = new IconGenerator(context.getApplicationContext());
        imageView = new ImageView(context.getApplicationContext());
        imageView.setImageResource(R.mipmap.ic_taxi_free);
        imageView.setBackgroundColor(Color.TRANSPARENT);
        iconGenerator.setBackground(new ColorDrawable(Color.TRANSPARENT));
        iconGenerator.setContentPadding(0,0,0,0);
        iconGenerator.setContentView(imageView);
    }

    @Override
    protected void onBeforeClusterItemRendered(ClusterDriver item, MarkerOptions markerOptions) {
        imageView.setImageResource(item.getIconPicture());
        Bitmap icon = iconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle());
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<ClusterDriver> cluster) {
        return false;
    }
}
