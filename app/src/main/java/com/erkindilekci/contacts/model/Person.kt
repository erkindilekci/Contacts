package com.erkindilekci.contacts.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("contacts")
data class Person(
    @ColumnInfo("person_name")
    var person_name: String,
    @ColumnInfo("person_number")
    var person_number: String)
{
    @PrimaryKey(autoGenerate = true)
    var person_id = 0
}