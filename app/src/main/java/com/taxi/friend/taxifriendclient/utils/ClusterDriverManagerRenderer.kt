package com.taxi.friend.taxifriendclient.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import com.taxi.friend.taxifriendclient.R
import com.taxi.friend.taxifriendclient.models.ClusterDriver

class ClusterDriverManagerRenderer(context: Context, map: GoogleMap, clusterManager: ClusterManager<ClusterDriver>) : DefaultClusterRenderer<ClusterDriver>(context, map, clusterManager) {

    private val iconGenerator: IconGenerator
    private val imageView: ImageView


    init {

        this.iconGenerator = IconGenerator(context.applicationContext)
        imageView = ImageView(context.applicationContext)
        imageView.setImageResource(R.mipmap.ic_taxi_free)
        imageView.setBackgroundColor(Color.TRANSPARENT)
        iconGenerator.setBackground(ColorDrawable(Color.TRANSPARENT))
        iconGenerator.setContentPadding(0, 0, 0, 0)
        iconGenerator.setContentView(imageView)
    }

    override fun onBeforeClusterItemRendered(item: ClusterDriver, markerOptions: MarkerOptions) {
        imageView.setImageResource(item.iconPicture)
        val icon = iconGenerator.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.title)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<ClusterDriver>): Boolean {
        return false
    }
}
