package com.gmzucolo.guests.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmzucolo.guests.service.constants.GuestConstants
import com.gmzucolo.guests.service.model.GuestModel
import com.gmzucolo.guests.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository.getInstance(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestlist: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int) {

        if (filter == GuestConstants.FILTER.EMPTY) {
            mGuestList.value = mGuestRepository.getAll()
        } else if (filter == GuestConstants.FILTER.PRESENT) {
            mGuestList.value = mGuestRepository.getPresents()
        } else {
            mGuestList.value = mGuestRepository.getAbsents()
        }
    }

    fun delete(id: Int) {
        mGuestRepository.delete(id)
    }
}