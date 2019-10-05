package com.taxi.friend.taxifriendclient.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem


class ClusterDriver(private var position: LatLng?, private var title: String?, private var snippet: String?, var iconPicture: Int, var driver: DriverLocation?) : ClusterItem {

    override fun getPosition(): LatLng? {
        return position
    }

    fun setPosition(position: LatLng) {
        this.position = position
    }

    override fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    override fun getSnippet(): String? {
        return snippet
    }

    fun setSnippet(snippet: String) {
        this.snippet = snippet
    }
}


