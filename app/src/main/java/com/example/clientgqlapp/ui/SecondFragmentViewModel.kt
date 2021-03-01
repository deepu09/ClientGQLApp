package com.example.clientgqlapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.clientgqlapp.UserQuery
import com.example.clientgqlapp.util.AplClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author vkaja
 */
class SecondFragmentViewModel : ViewModel() {

    lateinit var userId: String

    val userData : MutableLiveData<UserQuery.User>
    get() = AplClient.executeUserQuery(userId)

}