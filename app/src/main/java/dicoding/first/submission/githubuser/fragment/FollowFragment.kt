package dicoding.first.submission.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dicoding.first.submission.githubuser.adapter.UserListAdapter
import dicoding.first.submission.githubuser.data.response.ItemsItem
import dicoding.first.submission.githubuser.databinding.FragmentFollowBinding
import dicoding.first.submission.githubuser.viewmodel.DetailUserViewModel

class FollowFragment : Fragment() {

    private var followBinding: FragmentFollowBinding? = null
    private val adapter by lazy { UserListAdapter{} }
    private var position : Int = 0

    private val detailUserViewModel by activityViewModels<DetailUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followBinding = FragmentFollowBinding.inflate(layoutInflater)
        return followBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }
        if (position == 1){
            detailUserViewModel.userFollowers.observe(viewLifecycleOwner){
                setFollowersUser(it)
            }
        } else {
            detailUserViewModel.userFollowing.observe(viewLifecycleOwner){
                setFollowingUser(it)
            }
        }

        followBinding?.rvFollow?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }

        detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
           followBinding?.progressBar?.visibility = View.VISIBLE
        } else {
            followBinding?.progressBar?.visibility = View.GONE
        }
    }

    private fun setFollowersUser(listFollowers: MutableList<ItemsItem>) {
        val adapter = adapter
        adapter.setData(listFollowers)
        followBinding?.rvFollow?.adapter = adapter
    }

    private fun setFollowingUser(listFollowing: MutableList<ItemsItem>) {
        val adapter = adapter
        adapter.setData(listFollowing)
        followBinding?.rvFollow?.adapter = adapter
    }

    companion object{
        const val ARG_POSITION = "position"
    }

}
