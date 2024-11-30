package com.example.midtermmobile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.midtermmobile.model.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserInfoViewModel: ViewModel() {
    private val _info = MutableStateFlow<UserInfo>(UserInfo())
    val info: StateFlow<UserInfo> = _info

    fun setUser(userInfo: UserInfo) {
        _info.value = userInfo
    }

    fun setFullName(fullName: String) {
        _info.value = _info.value.copy(fullName = fullName)
    }

    fun setPhone(phone: String) {
        _info.value = _info.value.copy(phone = phone)
    }

    fun setEmail(email: String) {
        _info.value = _info.value.copy(email = email)
    }

    fun setAddress(address: String) {
        _info.value = _info.value.copy(address = address)
    }
}