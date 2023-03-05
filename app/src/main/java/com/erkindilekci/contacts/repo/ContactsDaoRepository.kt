package com.erkindilekci.contacts.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.erkindilekci.contacts.model.Person
import com.erkindilekci.contacts.room.PersonDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ContactsDaoRepository(var application: Application) {
    var personList = MutableLiveData<List<Person>>()
    var database: PersonDatabase

    init {
        database = PersonDatabase.getInstance(application)
        personList = MutableLiveData()
    }

    fun bringContacts(): MutableLiveData<List<Person>>{
        return personList
    }

    fun getAllPersons(){
        val job: Job = CoroutineScope(Dispatchers.Main).launch {
            personList.value = database.personDao().getAllPerson()
        }
    }

    fun searchPerson(searched_word: String){
        val job: Job = CoroutineScope(Dispatchers.Main).launch {
            database.personDao().searchPerson(searched_word)
        }
    }

    fun registerPerson(personName: String, personNumber: String){
        val job: Job = CoroutineScope(Dispatchers.Main).launch {
            val newPerson = Person(personName, personNumber)
            database.personDao().addPerson(newPerson)
        }
    }

    fun updatePerson(personId: Int, personName: String, personNumber: String){
        val job: Job = CoroutineScope(Dispatchers.Main).launch {
            database.personDao().updatePerson(personId, personName, personNumber)
        }
    }

    fun deletePerson(personId: Int){
        val job: Job = CoroutineScope(Dispatchers.Main).launch {
            database.personDao().deletePerson(personId)
            //Refresh
            getAllPersons()
        }
    }
}
