package com.gokhanagis.anrodidbootcampturkey.repository

import androidx.lifecycle.LiveData
import com.gokhanagis.anrodidbootcampturkey.data.UserDao
import com.gokhanagis.anrodidbootcampturkey.model.User

class UserRepository (private val userDao: UserDao) {

    val readAllData : LiveData<List<User>> = userDao.readALLData()

    suspend fun  addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user:User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

    fun getUser(id: Int): LiveData<User> {
        return  userDao.getUser(id)
    }
}