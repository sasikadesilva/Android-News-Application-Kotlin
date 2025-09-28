package com.example.newsapp.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserModel {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo("first_name")
    var firstName: String = ""

    @ColumnInfo("last_name")
    var lastName: String = ""

    @ColumnInfo("email")
    var email: String = ""

    @ColumnInfo("password")
    var password: String = ""

    @ColumnInfo("password_salt")
    var passwordSalt: String = ""



}