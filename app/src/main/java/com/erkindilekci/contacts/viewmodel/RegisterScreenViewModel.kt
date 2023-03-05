package com.erkindilekci.contacts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.erkindilekci.contacts.repo.ContactsDaoRepository

class RegisterScreenViewModel(application: Application): AndroidViewModel(application) {
    var repo = ContactsDaoRepository(application)

    fun register(personName: String, personNumber: String){
        repo.registerPerson(personName, personNumber)
    }
}