package com.erkindilekci.contacts.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erkindilekci.contacts.model.Person

@Dao
interface PersonDao {
    @Insert
    suspend fun addPerson(person: Person)

    @Query("SELECT * FROM contacts")
    suspend fun getAllPerson(): List<Person>

    @Query("SELECT * FROM contacts WHERE person_name like '%' || :searched_word || '%'")
    suspend fun searchPerson(searched_word:String):List<Person>

    @Query("UPDATE contacts SET person_name=:new_name, person_number=:new_number WHERE person_id=:person_id")
    suspend fun updatePerson(person_id: Int, new_name: String, new_number: String)

    @Query("DELETE FROM contacts WHERE person_id=:person_id")
    suspend fun deletePerson(person_id: Int)
}

/*
@Query("SELECT * FROM contacts WHERE person_name like '%' || :searched_word || '%'")
    suspend fun searchPerson(searched_word:String):List<Person>
 */