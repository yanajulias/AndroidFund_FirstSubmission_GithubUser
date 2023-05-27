package dicoding.first.submission.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dicoding.first.submission.githubuser.R
import dicoding.first.submission.githubuser.adapter.SectionsPagerAdapter
import dicoding.first.submission.githubuser.adapter.UserListAdapter
import dicoding.first.submission.githubuser.data.response.DetailUserResponse
import dicoding.first.submission.githubuser.databinding.ActivityDetailUserBinding
import dicoding.first.submission.githubuser.viewmodel.DetailUserViewModel
import dicoding.first.submission.githubuser.viewmodel.UserListViewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra("username") ?: ""
        detailUserViewModel.getDetailUser(username)

        detailUserViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailUserViewModel.userDetail.observe(this){
            setDetailUser(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            detailUserBinding.progressBar.visibility = View.VISIBLE
        } else {
            detailUserBinding.progressBar.visibility = View.GONE
        }
    }

    private fun setDetailUser(detailUser: DetailUserResponse){
        detailUserBinding.imgDetailUser.load(detailUser.avatarUrl){
            transformations(CircleCropTransformation())
        }
        detailUserBinding.txtUsername.text = detailUser.login
    }

}