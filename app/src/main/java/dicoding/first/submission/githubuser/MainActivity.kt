package dicoding.first.submission.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dicoding.first.submission.githubuser.adapter.UserListAdapter
import dicoding.first.submission.githubuser.data.response.ItemsItem
import dicoding.first.submission.githubuser.databinding.ActivityMainBinding
import dicoding.first.submission.githubuser.viewmodel.UserListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel by viewModels<UserListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.hide()

//        activityMainBinding.apply {
//            rvUser.layoutManager = LinearLayoutManager(applicationContext)
//            rvUser.setHasFixedSize(true)
//            rvUser.adapter = adapter
//
//        }
//        activityMainBinding.rvUser.layoutManager = LinearLayoutManager(this)
//        activityMainBinding.rvUser.setHasFixedSize(true)
//        activityMainBinding.rvUser.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvUser.layoutManager = layoutManager
        activityMainBinding.rvUser.setHasFixedSize(true)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.userList.observe(this) {
            setListUser(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            activityMainBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityMainBinding.progressBar.visibility = View.GONE
        }
    }

    private fun setListUser(listUser: MutableList<ItemsItem>) {
        val adapter = UserListAdapter()
        adapter.setData(listUser)
        activityMainBinding.rvUser.adapter = adapter
    }
}