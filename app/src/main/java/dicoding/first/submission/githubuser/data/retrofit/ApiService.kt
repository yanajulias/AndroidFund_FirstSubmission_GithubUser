package dicoding.first.submission.githubuser.data.retrofit

import dicoding.first.submission.githubuser.data.response.DetailUserResponse
import dicoding.first.submission.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

//    @GET("search/users")
//    fun getListUser(@QueryMap() map: Map<String, Any>): Call<ListResponse>

    @GET("users")
    fun getListUser(): Call<MutableList<ItemsItem>>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<MutableList<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<MutableList<ItemsItem>>
}