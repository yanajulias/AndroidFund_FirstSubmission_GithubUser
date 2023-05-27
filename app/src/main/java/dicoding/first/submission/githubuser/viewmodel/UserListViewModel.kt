package dicoding.first.submission.githubuser.viewmodel

import android.content.ClipData.Item
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dicoding.first.submission.githubuser.MainActivity
import dicoding.first.submission.githubuser.data.response.ItemsItem
import dicoding.first.submission.githubuser.data.response.ListResponse
import dicoding.first.submission.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val _userList = MutableLiveData<MutableList<ItemsItem>>()
    val userList: LiveData<MutableList<ItemsItem>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        listUsername()
    }

    private fun listUsername() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUser()
        client.enqueue(object : Callback<MutableList<ItemsItem>> {
            override fun onResponse(call: Call<MutableList<ItemsItem>>, response: Response<MutableList<ItemsItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userList.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MutableList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}