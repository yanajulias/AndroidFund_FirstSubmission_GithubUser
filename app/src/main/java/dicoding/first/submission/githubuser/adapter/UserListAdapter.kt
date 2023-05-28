package dicoding.first.submission.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import dicoding.first.submission.githubuser.data.response.ItemsItem
import dicoding.first.submission.githubuser.databinding.ItemUserBinding

class UserListAdapter(private val data: MutableList<ItemsItem> = mutableListOf(),
private val listener: (ItemsItem) -> Unit) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    fun setData(data: MutableList<ItemsItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem) {
            binding.imgUser.load(item.avatarUrl) {
                transformations(CircleCropTransformation())
            }

            binding.tvUser.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

}