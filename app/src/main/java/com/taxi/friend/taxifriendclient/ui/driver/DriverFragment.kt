package com.taxi.friend.taxifriendclient.ui.driver

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper

import com.taxi.friend.taxifriendclient.R
import kotlinx.android.synthetic.main.driver_fragment.view.*

class DriverFragment : Fragment() {

    private var mViewModel: DriverViewModel = DriverViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.driver_fragment, container, false)

        view.driverRecycler.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        view.driverRecycler.adapter = RecyclerDriverAdapter(mViewModel.getDriver()!!.value!!.pictures)

        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(view.driverRecycler)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(DriverViewModel::class.java)


    }

    companion object {

        fun newInstance(): DriverFragment {
            return DriverFragment()
        }
    }

}
