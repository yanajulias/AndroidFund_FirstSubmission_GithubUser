package dicoding.first.submission.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dicoding.first.submission.githubuser.adapter.UserListAdapter
import dicoding.first.submission.githubuser.data.response.ItemsItem
import dicoding.first.submission.githubuser.databinding.ActivityMainBinding
import dicoding.first.submission.githubuser.ui.DetailUserActivity
import dicoding.first.submission.githubuser.viewmodel.UserListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel by viewModels<UserListViewModel>()
    private val adapter by lazy {
        UserListAdapter {
            Intent(this, DetailUserActivity::class.java).apply {
                putExtra("username", it.login)
                startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.hide()

        activityMainBinding.apply {
            rvUser.layoutManager = LinearLayoutManager(applicationContext)
            rvUser.setHasFixedSize(true)
        }

        with(activityMainBinding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, i, keyEvent ->
                    val searchText = searchView.text.toString()
                    searchBar.text = searchView.text
                    searchView.hide()
                    viewModel.listUsername(query = searchText)
                    false
                }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.userList.observe(this) {
            setListUser(it)
        }

        viewModel.listUsername(query = "android")
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            activityMainBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityMainBinding.progressBar.visibility = View.GONE
        }
    }

    private fun setListUser(listUser: MutableList<ItemsItem>) {
        val adapter = adapter
        adapter.setData(listUser)
        activityMainBinding.rvUser.adapter = adapter
    }
}