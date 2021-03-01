package com.example.clientgqlapp.util

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloClientAwarenessInterceptor
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.example.clientgqlapp.AddUserMutation
import com.example.clientgqlapp.BuildConfig
import com.example.clientgqlapp.UserQuery
import com.example.clientgqlapp.UsersQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

/**
 * @author vkaja
 */
private const val BASE_URL: String = "https://gql-android-apollo.herokuapp.com/graphql"
private const val TAG = "AplClient"
object AplClient {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(ApolloClientAwarenessInterceptor(BuildConfig.APPLICATION_ID, BuildConfig.VERSION_NAME))
                .build()
    }

    // First, create an `ApolloClient`
    private val apolloClient: ApolloClient by lazy {
        ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build()
    }

    fun executeUsersQuery(): MutableLiveData<List<UsersQuery.User>> {
        val usersLiveData : MutableLiveData<List<UsersQuery.User>> = MutableLiveData<List<UsersQuery.User>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = try {
                apolloClient.query(UsersQuery()).await()
            } catch (e: ApolloException) {
                // handle protocol errors
                return@launch
            }
            val users = response.data?.users
            if (users == null || response.hasErrors()) {
                // handle application errors
                return@launch
            }
            withContext(Dispatchers.Main) {
                usersLiveData.value = when(users.isNotEmpty() && users.filterNotNull().isNotEmpty()){
                    true -> users.filterNotNull()
                    else -> null
                }
            }
            return@launch
        }
        return usersLiveData
    }

    fun executeUserQuery(id: String): MutableLiveData<UserQuery.User> {
        val userLiveData : MutableLiveData<UserQuery.User> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val response = try {
                apolloClient.query(UserQuery(id)).await()
            } catch (e: ApolloException) {
                // handle protocol errors
                return@launch
            }
            val user = response.data?.user
            if (user == null || response.hasErrors()) {
                // handle application errors
                return@launch
            }
            withContext(Dispatchers.Main) {
                userLiveData.value = user
            }
            return@launch
        }
        return userLiveData
    }

    fun executeAddUserMutation(name: String, age: Int, profession: String, salary: String) : MutableLiveData<AddUserMutation.CreateUser> {
        val userLiveData: MutableLiveData<AddUserMutation.CreateUser> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val response = try {
                apolloClient.mutate(AddUserMutation(name, age, profession, salary)).await()
            } catch (e : ApolloException) {
                return@launch
            }
            val user = response.data?.createUser
            if (user == null || response.hasErrors()) {
                // handle application errors
                return@launch
            }
            withContext(Dispatchers.Main) {
                userLiveData.value = user
            }
            return@launch
        }
        return userLiveData
    }
}