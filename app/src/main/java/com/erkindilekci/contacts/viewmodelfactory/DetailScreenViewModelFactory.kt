package com.erkindilekci.contacts.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erkindilekci.contacts.viewmodel.DetailScreenViewModel

class DetailScreenViewModelFactory(var application: Application): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailScreenViewModel(application) as T
    }
}