package com.gmzucolo.guests.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmzucolo.guests.R
import com.gmzucolo.guests.databinding.FragmentPresentBinding
import com.gmzucolo.guests.service.constants.GuestConstants
import com.gmzucolo.guests.view.adapter.GuestAdapter
import com.gmzucolo.guests.view.listener.GuestListener
import com.gmzucolo.guests.viewmodel.GuestsViewModel
import kotlinx.coroutines.InternalCoroutinesApi

class PresentFragment : Fragment() {

    private lateinit var mViewModel: GuestsViewModel
    private val mAdapter: GuestAdapter = GuestAdapter()
    private lateinit var mListener: GuestListener

    @InternalCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_present, container, false)

        //RecyclerView
        //1. Obtain a recycler
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_presents)

        //2. Define a layout
        recycler.layoutManager = LinearLayoutManager(context)

        //3. Define an adapter (paste data and layout)
        recycler.adapter = mAdapter

        observe()

        mListener = object : GuestListener {
            override fun onClick(id: Int) {

                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle: Bundle = Bundle()
                bundle.putInt(GuestConstants.GUESTID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(GuestConstants.FILTER.PRESENT)
            }

        }

        mAdapter.attachListener(mListener)

        return root
    }

    @InternalCoroutinesApi
    override fun onResume() {
        super.onResume()
        mViewModel.load(GuestConstants.FILTER.PRESENT)
    }

    private fun observe() {
        mViewModel.guestlist.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)
        })

    }

}