package org.sopt.sample

import androidx.lifecycle.ViewModel
import org.sopt.sample.data.Repo

class HomeViewModel: ViewModel() {
    val mockRepoList = listOf<Repo>(
        Repo(
            image = R.drawable.basicprofile,
            name = "San Francisco",
            author = "Apple"
        ),
        Repo(
            image = R.drawable.basicprofile,
            name = "Las Vegas",
            author = "Banana"
        ),
        Repo(
            image = R.drawable.basicprofile,
            name = "Chicago",
            author = "Candy"
        ),
        Repo(
            image = R.drawable.basicprofile,
            name = "Washington D.C.",
            author = "Dream"
        ),
        Repo(
            image = R.drawable.basicprofile,
            name = "New York City",
            author = "Eagle"
        )
    )
}