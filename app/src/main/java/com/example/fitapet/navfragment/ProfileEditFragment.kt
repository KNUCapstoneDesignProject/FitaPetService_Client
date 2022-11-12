package com.example.fitapet.PetsitterList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitapet.Cookie
import com.example.fitapet.PetsitterList.TogetherServiceDetail.TogetherServiceFragment
import com.example.fitapet.R
import com.example.fitapet.databinding.FragmentDogServiceBinding
import com.example.fitapet.databinding.FragmentProfileEditBinding
import com.example.fitapet.navfragment.MypageFragment
import com.example.fitapet.navfragment.UserProfile
import com.example.fitapet.retrofit.RetrofitClient
import com.example.fitapet.retrofit.dto.getDetail
import retrofit2.Callback
import com.example.fitapet.ui.reservation.petList.PetListRecyclerFragment
import retrofit2.Call
import retrofit2.Response

class ProfileEditFragment : Fragment() {
    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!
    val userProfile= mutableListOf<UserProfile>()
//    val petsitterListAdapter=PetsitterListAdapter(petsittercards)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileEditBinding.inflate(inflater,container,false)
//        binding.petListRecyclerView.layoutManager=LinearLayoutManager(requireContext())
//        binding.petListRecyclerView.adapter= PetListAdapter(pets)

        //userProfile.add(UserProfile("R.drawable.example1","김도선","01012345678","대구광역시 북구 대현동 24-9","남자","25"))
        RetrofitClient.apiServer.getDetail(Cookie.userId).enqueue(object: Callback<getDetail>{
            override fun onResponse(call: Call<getDetail>, response: Response<getDetail>) {
                val responseResult = response.body()!!.result
                userProfile.add(
                    UserProfile(responseResult[0].profileImgUrl, responseResult[0].customerName, responseResult[0].tel,
                responseResult[0].address, responseResult[0].sex, responseResult[0].age.toString())
                )
                //binding.myName.text = responseResult[0].customerName
            }

            override fun onFailure(call: Call<getDetail>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

        })

        //RetrofitClient.apiServer.editInfo(4, binding.)

        binding.myName.text = userProfile[0].userName
        if (userProfile[0].userGender == "f"){
            binding.userSex.text = "여자"
        }else{
            binding.userSex.text = "남자"
        }
//        binding.recylcerView.layoutManager=LinearLayoutManager(requireContext())
//        binding.recylcerView.adapter=petsitterListAdapter
//        petsitterListAdapter.setItemClickListener(object : PetsitterListAdapter.OnItemClickListener{
//            override fun onClick(v: View, position: Int) {
//                loadFragment(PetListRecyclerFragment())
//            }
//
//        })

        binding.done.setOnClickListener {
            loadFragment(MypageFragment())
        }

        return binding.root
    }
    private fun loadFragment(fragment: Fragment){
        Log.d("clickTest","click!->"+fragment.tag)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}