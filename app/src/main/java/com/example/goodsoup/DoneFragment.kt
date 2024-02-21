package com.example.goodsoup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.goodsoup.databinding.FragmentDoneBinding
import com.example.goodsoup.navigation.navigator
import kotlin.properties.Delegates

class DoneFragment : Fragment() {
    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!
    private var randomIndex by Delegates.notNull<Int>()
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.data.observe(viewLifecycleOwner) { data ->
            Glide.with(this).load(ImageList[data]).centerCrop().into(binding.randomImgView)

        }
        binding.goBackBtn.setOnClickListener {
            navigator().goBack()
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        @JvmStatic private val KEY_IMAGE = "KEY_IMAGE"

        val ImageList = listOf(
            "https://static.wikia.nocookie.net/gensin-impact/images/2/2f/Item_Delicious_Jade_Fruit_Soup.png/revision/latest?cb=20230412092830",
            "https://static.wikia.nocookie.net/gensin-impact/images/d/de/Item_Delicious_Radish_Veggie_Soup.png/revision/latest?cb=20210415141037",
            "https://static.wikia.nocookie.net/gensin-impact/images/2/28/Item_Delicious_Minty_Bean_Soup.png/revision/latest?cb=20220916014548"
        )
    }
}