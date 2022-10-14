package org.sopt.sample

import androidx.lifecycle.ViewModel
import org.sopt.sample.data.RecyclerViewItem
import org.sopt.sample.data.Repo
import org.sopt.sample.data.RepoHeader

class HomeViewModel: ViewModel() {
    val mockRepoList = listOf<RecyclerViewItem>(
        RepoHeader("[Skylar] Github - Repositories"),
        Repo(R.drawable.basicprofile, "San Francisco", "Apple"),
        Repo(R.drawable.basicprofile, "Las Vegas", "Banana"),
        Repo(R.drawable.basicprofile, "Chicago", "Candy"),
        Repo(R.drawable.basicprofile, "Washington D.C.", "Dream"),
        Repo(R.drawable.basicprofile, "New York City", "Eagle"),

        RepoHeader("[Suhyeon] Github - Repositories"),
        Repo(R.drawable.basicprofile, "Seattle", "Fish"),
        Repo(R.drawable.basicprofile, "Yosemite National Park", "Google"),
        Repo(R.drawable.basicprofile, "Boston", "Horse"),
        Repo(R.drawable.basicprofile, "Los Angeles", "Idle"),
        Repo(R.drawable.basicprofile, "San Diego", "Joy"),
        Repo(R.drawable.basicprofile, "Portland", "Korea"),
        Repo(R.drawable.basicprofile, "New Jersey", "Lord")
    )
}