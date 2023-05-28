package dicoding.first.submission.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dicoding.first.submission.githubuser.data.response.DetailUserResponse
import dicoding.first.submission.githubuser.data.response.ItemsItem
import dicoding.first.submission.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    companion object {
        private const val TAG = "DetailUserActivity"
    }

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userFollowers = MutableLiveData<MutableList<ItemsItem>>()
    val userFollowers: LiveData<MutableList<ItemsItem>> = _userFollowers

    private val _userFollowing = MutableLiveData<MutableList<ItemsItem>>()
    val userFollowing: LiveData<MutableList<ItemsItem>> = _userFollowing

    fun getDetailUser(username: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _userDetail.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getFollowers(username: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getFollowersUser(username)
        client.enqueue(object : Callback<MutableList<ItemsItem>> {
            override fun onResponse(
                call: Call<MutableList<ItemsItem>>,
                response: Response<MutableList<ItemsItem>>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _userFollowers.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure:${response.message()}")
                }

            }

            override fun onFailure(call: Call<MutableList<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure:${t.message}")
            }

        })
    }

    fun getFollowing(username: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getFollowingUser(username)
        client.enqueue(object : Callback<MutableList<ItemsItem>> {
            override fun onResponse(
                call: Call<MutableList<ItemsItem>>,
                response: Response<MutableList<ItemsItem>>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _userFollowing.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure:${response.message()}")
                }

            }

            override fun onFailure(call: Call<MutableList<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure:${t.message}")
            }

        })
    }
}