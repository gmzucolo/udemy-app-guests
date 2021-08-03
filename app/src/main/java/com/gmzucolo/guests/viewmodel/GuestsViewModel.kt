package com.gmzucolo.guests.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmzucolo.guests.service.constants.GuestConstants
import com.gmzucolo.guests.service.model.GuestModel
import com.gmzucolo.guests.service.repository.GuestRepository
import kotlinx.coroutines.InternalCoroutinesApi

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    @InternalCoroutinesApi
    private val mGuestRepository = GuestRepository(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestlist: LiveData<List<GuestModel>> = mGuestList

    @InternalCoroutinesApi
    fun load(filter: Int) {

        if (filter == GuestConstants.FILTER.EMPTY) {
            mGuestList.value = mGuestRepository.getAll()
        } else if (filter == GuestConstants.FILTER.PRESENT) {
            mGuestList.value = mGuestRepository.getPresent()
        } else {
            mGuestList.value = mGuestRepository.getAbsent()
        }
    }

    @InternalCoroutinesApi
    fun delete(id: Int) {
        val guest = mGuestRepository.get(id)
        mGuestRepository.delete(guest)
    }
}