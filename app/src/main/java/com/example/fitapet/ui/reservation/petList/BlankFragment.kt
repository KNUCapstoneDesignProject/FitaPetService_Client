package com.example.fitapet.ui.reservation.petList

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fitapet.PetsitterList.TogetherServiceDetail.ChooseFriendFragment
import com.example.fitapet.R
import com.example.fitapet.databinding.FragmentBlankBinding
import com.example.fitapet.databinding.FragmentPetListRecyclerBinding
import com.example.fitapet.retrofit.RetrofitClient
import com.example.fitapet.retrofit.dto.getPets
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BlankFragment : Fragment() {
    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!
    val bundle:Bundle?=arguments
    val pets = mutableListOf<Pets>()
    val setbundle = Bundle()
    var mode:Int = 0
    var fsize:Int = 0
    var friendId= mutableListOf<Long>()

    lateinit var responseGetPets: Call<getPets>
    val petListAdapter=PetListAdapter2(pets)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG11","start BlankFrag"+ChooseFriendFragment.friendIdCom1)
        Log.d("TAG11","start BlankFrag2"+ChooseFriendFragment.friendIdCom2)
        // Inflate the layout for this fragment
        _binding = FragmentBlankBinding.inflate(inflater,container,false)
        //Log.d("TAG11","현재 friend Id= "+friendId.get(c))
        Log.d("TAG11","받은 :"+ChooseFriendFragment.friendName1)
        Log.d("TAG11","받은 :"+ChooseFriendFragment.friendImageUrl1)
        responseGetPets= RetrofitClient.apiServer.getPets(ChooseFriendFragment.friendIdCom1)

        binding.textView8.text=ChooseFriendFragment.friendName1.toString()+"의 반려동물 목록"
        Glide.with(this@BlankFragment).load(ChooseFriendFragment.friendImageUrl1).into(binding.FriendImage)
//        Glide.with(holder.itemView).load(strimg).into(binding.petsitterImage)
        responseGetPets.enqueue(object : Callback<getPets> {
            override fun onResponse(
                call: Call<getPets>,
                response: Response<getPets>
            ) {
                val targetPets=response.body()!!.result
                for (pet in targetPets) {
                    pets.add(Pets(pet.petName, pet.petSpecies, pet.petBirth, pet.petSize,pet.petType))
                    Log.d("TAG11","pet="+Pets(pet.petName, pet.petSpecies, pet.petBirth, pet.petSize,pet.petType))
                }

                binding.petListRecyclerView2.adapter=petListAdapter
                // binding.petListRecyclerView.addItemDecoration(MyDecoration(requireContext()))
            }

            override fun onFailure(call: Call<getPets>, t: Throwable) {
                Log.d(ContentValues.TAG, "실패 : $t")
            }
        })
        binding.petListRecyclerView2.layoutManager= LinearLayoutManager(requireContext())
        binding.reservation00NextBtn.setOnClickListener {
            if(ChooseFriendFragment.cnt==2){
                loadFragment(BlankFragment2())
            }
            else{
                loadFragment(Reservation01Fragment())
            }
        }
        //loadFragment(PetListRecyclerFragment())
        petListAdapter.setItemClickListener(object :PetListAdapter2.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
            }

        })
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