package org.sopt.sample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.data.Repo
import org.sopt.sample.databinding.LayoutGithubRepoBinding

class RepoAdapter(context: Context): RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var repoList: List<Repo> = emptyList()

    class RepoViewHolder(private val binding: LayoutGithubRepoBinding)
    : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Repo) {
            binding.imgGithubRepo.setImageDrawable(binding.root.context.getDrawable(data.image))
            binding.txtGithubRepoName.text = data.name
            binding.txtGithubRepoAuthor.text = data.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = LayoutGithubRepoBinding.inflate(inflater, parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.onBind(repoList[position])
    }

    override fun getItemCount(): Int = repoList.size

    fun setRepoList(repoList: List<Repo>) {
        this.repoList = repoList.toList()
        notifyDataSetChanged()
    }


}