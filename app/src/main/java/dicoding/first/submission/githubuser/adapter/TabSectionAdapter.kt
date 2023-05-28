package dicoding.first.submission.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dicoding.first.submission.githubuser.fragment.FollowFragment

class TabSectionAdapter (activity: AppCompatActivity): FragmentStateAdapter(activity){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val followFragment = FollowFragment()
        followFragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_POSITION, position + 1)
        }
        return followFragment
    }

}