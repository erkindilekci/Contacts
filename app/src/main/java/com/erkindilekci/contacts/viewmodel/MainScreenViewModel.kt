package com.erkindilekci.contacts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.erkindilekci.contacts.model.Person
import com.erkindilekci.contacts.repo.ContactsDaoRepository

class MainScreenViewModel(application: Application): AndroidViewModel(application) {
    var repo = ContactsDaoRepository(application)
    var personList = MutableLiveData<List<Person>>()

    init {
        loadContacts()
        personList = repo.bringContacts()
    }

    fun loadContacts(){
        repo.getAllPersons()
    }

    fun search(searched_word: String){
        repo.searchPerson(searched_word)
    }

    fun delete(personId: Int){
        repo.deletePerson(personId)
    }
}