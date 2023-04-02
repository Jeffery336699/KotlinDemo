package com.example.kotlindemo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    lateinit var loginRepository: LoginRepository
    var data: MutableLiveData<String?> = MutableLiveData()
    fun login() {
        viewModelScope.launch {
            val result = try {
                loginRepository.makeLoginRequest()
            } catch (e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result.Success<String> -> {
                    Log.i("TTTT", "login 成功: " + result.data)
                    data.value = result.data
                }
                is Result.Error -> {
                    Log.i("TTTT", "login 失败: " + result.exception.toString())
                }
            }
        }
    }
}