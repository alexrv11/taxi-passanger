package com.taxi.friend.taxifriendclient.ui.driver

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.taxi.friend.taxifriendclient.R
import kotlinx.android.synthetic.main.driver_item_picture.view.*
import kotlin.collections.ArrayList

class RecyclerDriverAdapter(val pictures: ArrayList<String>) : Adapter<RecyclerDriverAdapter.DriverViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.driver_item_picture, parent, false)

        return DriverViewHolder(view)
    }

    override fun getItemCount() = pictures.size

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {

        val imageBytes = Base64.decode(pictures[position], 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        holder.description.setImageBitmap(image)
    }


    class DriverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val description: ImageView = itemView.imagecar
    }
}
