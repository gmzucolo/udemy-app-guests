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
import com.gmzucolo.guests.databinding.FragmentAllBinding
import com.gmzucolo.guests.service.constants.GuestConstants
import com.gmzucolo.guests.view.adapter.GuestAdapter
import com.gmzucolo.guests.view.listener.GuestListener
import com.gmzucolo.guests.viewmodel.GuestsViewModel

class AllGuestsFragment : Fragment() {

    private lateinit var mVIewModel: GuestsViewModel
    private val mAdapter: GuestAdapter = GuestAdapter()
    private lateinit var mListener: GuestListener

    private var _binding: FragmentAllBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        mVIewModel =
            ViewModelProvider(this).get(GuestsViewModel::class.java)

        _binding = FragmentAllBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //RecyclerView
        //1. Obtain a recycler
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_all_guests)

        //2. Define a layout
        recycler.layoutManager = LinearLayoutManager(context)

        //3. Define an adapter (paste data and layout)
        recycler.adapter = mAdapter

        mListener = object : GuestListener {
            override fun onClick(id: Int) {

                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle: Bundle = Bundle()
                bundle.putInt(GuestConstants.GUESTID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mVIewModel.delete(id)
                mVIewModel.load(GuestConstants.FILTER.EMPTY)
            }

        }

        mAdapter.attachListener(mListener)
        observe()

        return root
    }

    override fun onResume() {
        super.onResume()
        mVIewModel.load(GuestConstants.FILTER.EMPTY)
    }

    private fun observe() {
        mVIewModel.guestlist.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)
        })

    }
}