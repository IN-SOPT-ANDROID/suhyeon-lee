package org.sopt.sample.adapter

import android.content.Context
import android.preference.PreferenceActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import org.sopt.sample.R
import org.sopt.sample.data.RecyclerViewItem
import org.sopt.sample.data.Repo
import org.sopt.sample.data.RepoHeader
import org.sopt.sample.databinding.LayoutGithubRepoBinding
import org.sopt.sample.databinding.LayoutGithubRepoHeaderBinding

const val VIEW_TYPE_HEADER = 1
const val VIEW_TYPE_REPO = 2

class RepoAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    //private var repoList: List<Repo> = emptyList()
    private var dataList: List<RecyclerViewItem> = emptyList()

    override fun getItemViewType(position: Int): Int {
        if (dataList[position] is RepoHeader) {
            return VIEW_TYPE_HEADER
        }
        return VIEW_TYPE_REPO
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val binding = LayoutGithubRepoBinding.inflate(inflater, parent, false)
        //return RepoViewHolder(binding)
        if (viewType == VIEW_TYPE_HEADER) {
            return RepoHeaderViewHolder(
                LayoutGithubRepoHeaderBinding.inflate(inflater, parent, false)
            )
        }
        return RepoViewHolder(
            LayoutGithubRepoBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        if (holder is RepoHeaderViewHolder && item is RepoHeader) {
            holder.onBind(item)
        }
        if (holder is RepoViewHolder && item is Repo) {
            holder.onBind(item)
        }
    }

    fun setData(data: List<RecyclerViewItem>) {
        // 원래 함수 이름 - setRepoList
        // 원래 매개변수 - repoList: List<Repo>
        dataList = data.toList()
        notifyDataSetChanged()
    }

    class RepoHeaderViewHolder(private val binding: LayoutGithubRepoHeaderBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: RepoHeader) {
            binding.tvGithubHeader.text = data.title
        }
    }

    class RepoViewHolder(private val binding: LayoutGithubRepoBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Repo) {
            binding.imgGithubRepo.setImageDrawable(binding.root.context.getDrawable(data.image))
            binding.txtGithubRepoName.text = data.name
            binding.txtGithubRepoAuthor.text = data.author
        }
    }
}