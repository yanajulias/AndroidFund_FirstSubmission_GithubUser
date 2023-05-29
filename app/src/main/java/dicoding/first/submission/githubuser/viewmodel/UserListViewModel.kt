package dicoding.first.submission.githubuser.viewmodel

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    //    init {
    //        listUsername(query = "android ")
    //    }

    fun listUsername(query: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getListUser(query)
        client.enqueue(object : Callback<ListResponse> {
            override fun onResponse(call: Call<ListResponse>, response: Response<ListResponse>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _userList.postValue(response.body()?.items)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}