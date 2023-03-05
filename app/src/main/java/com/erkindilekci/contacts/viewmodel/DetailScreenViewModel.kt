package com.erkindilekci.contacts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.erkindilekci.contacts.repo.ContactsDaoRepository

class DetailScreenViewModel(application: Application): AndroidViewModel(application) {
    var repo = ContactsDaoRepository(application)

    fun update(personId: Int, personName: String, personNumber: String){
        repo.updatePerson(personId, personName, personNumber)
    }
}