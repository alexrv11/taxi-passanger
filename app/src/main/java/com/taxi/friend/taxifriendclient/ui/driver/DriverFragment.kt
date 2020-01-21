package com.taxi.friend.taxifriendclient.ui.driver

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper

import com.taxi.friend.taxifriendclient.R
import com.taxi.friend.taxifriendclient.models.Driver
import com.taxi.friend.taxifriendclient.models.DriverData
import com.taxi.friend.taxifriendclient.models.ResponseWrapper
import com.taxi.friend.taxifriendclient.services.DriverService
import kotlinx.android.synthetic.main.driver_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class DriverFragment : Fragment() {
    val driverId: String = "070f886c-ae24-4a2d-a252-9f9c3df7c399"
    private var mViewModel: DriverViewModel = DriverViewModel(driverId)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.driver_fragment, container, false)
        mViewModel.loadDriver(driverId)

        view.driverRecycler.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

        //val model = ViewModelProviders.of(this)[MyViewModel::class.java]
        mViewModel.getDriver().observe(this, Observer<DriverData>{ driverModel ->
            view.driverRecycler.adapter = RecyclerDriverAdapter(driverModel.pictures)
            view.name.text = driverModel.name
            view.identityCar.text = driverModel.identityCar
        })



        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(view.driverRecycler)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    companion object {

        fun newInstance(): DriverFragment {
            return DriverFragment()
        }
    }
}
