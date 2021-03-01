package com.example.clientgqlapp.ui

import androidx.lifecycle.ViewModel
import com.example.clientgqlapp.util.AplClient

/**
 * @author vkaja
 */
class FirstFragmentViewModel: ViewModel() {

    val users = AplClient.executeUsersQuery()
}