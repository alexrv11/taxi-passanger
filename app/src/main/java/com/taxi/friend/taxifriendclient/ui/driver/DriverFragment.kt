package com.taxi.friend.taxifriendclient.ui.driver

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.taxi.friend.taxifriendclient.R

class DriverFragment : Fragment() {

    private var mViewModel: DriverViewModel? = null
    private val viewPager: ViewPager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.driver_fragment, container, false)
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
