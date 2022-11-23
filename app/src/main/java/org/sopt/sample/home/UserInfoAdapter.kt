package org.sopt.sample.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.databinding.ItemHomeUserBinding
import org.sopt.sample.retrofit.ReqresResponse

// 어댑터 객체를 생성해주는 클래스
// 이 클래스는 RecyclerView.Adapter<이하에서 만들 뷰홀더 클래스>를 상속한다
// 뷰홀더 패턴: 각 뷰 객체를 뷰 홀더에 보관해 반복적인 메서드 호출을 줄여 속도를 개선하는 패턴
class UserInfoAdapter(
    private val userList : List<ReqresResponse.Data>
) : RecyclerView.Adapter<UserInfoAdapter.MyViewHolder>() {

    // 0) MyViewHolder 클래스를 생성한다
    inner class MyViewHolder(
        private val binding: ItemHomeUserBinding
    ) : RecyclerView.ViewHolder(binding.root) { // RecyclerView.ViewHolder 클래스를 상속받는다
        fun onBind(data: ReqresResponse.Data) {
            binding.tvUserName.text = data.first_name + " " + data.last_name
            binding.tvUserEmail.text = data.email
        }
    }

    // 1) 위 inner class 정의 부분에서 만든 뷰홀더 객체를 생성해준다
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
    MyViewHolder {
        // item_home_user.xml 뷰 바인딩 객체 생성
        val binding: ItemHomeUserBinding =
            ItemHomeUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // 2) 어댑터 클래스의 인자로 들어온 userList를, 위에서 생성된 뷰홀더 객체에 어떻게 넣어줄지 결정해준다
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.onBind(userList[position])
        }
    }

    // 3) 데이터가 몇 개인지 변환해준다
    override fun getItemCount(): Int {
        return userList.size
    }
}