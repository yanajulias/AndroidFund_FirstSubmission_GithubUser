package dicoding.first.submission.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dicoding.first.submission.githubuser.R
import dicoding.first.submission.githubuser.adapter.TabSectionAdapter
import dicoding.first.submission.githubuser.data.response.DetailUserResponse
import dicoding.first.submission.githubuser.databinding.ActivityDetailUserBinding
import dicoding.first.submission.githubuser.viewmodel.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)

        val sectionsPagerAdapter = TabSectionAdapter(this)
        val viewPager: ViewPager2 = detailUserBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = detailUserBinding.tabLayout
        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra("username") ?: ""
        detailUserViewModel.apply {
            getDetailUser(username)
            getFollowers(username)
            getFollowing(username)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.userDetail.observe(this) {
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

    private fun setDetailUser(detailUser: DetailUserResponse) {
        detailUserBinding.imgDetailUser.load(detailUser.avatarUrl) {
            transformations(CircleCropTransformation())
        }
        detailUserBinding.apply {
            txtFullname.text = detailUser.name
            txtUsername.text = getString(R.string.username, detailUser.login)
            txtFollowersCount.text = getString(R.string.count_followers, detailUser.followers)
            txtFollowingCount.text = getString(R.string.count_following, detailUser.following)
        }
    }

}