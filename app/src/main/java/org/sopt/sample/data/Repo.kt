package org.sopt.sample.data

import androidx.annotation.DrawableRes

open class RecyclerViewItem

data class RepoHeader (
    val title: String
) : RecyclerViewItem()

data class Repo(
    @DrawableRes val image: Int,
    val name: String,
    val author: String
) : RecyclerViewItem()