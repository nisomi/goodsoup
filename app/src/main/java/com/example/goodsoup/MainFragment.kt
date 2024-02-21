package com.example.goodsoup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.goodsoup.databinding.FragmentMainBinding
import com.example.goodsoup.navigation.navigator
import kotlin.properties.Delegates
import kotlin.random.Random

class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var randomImgURL: String
    private var randomIndex by Delegates.notNull<Int>()

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        binding.cookButton.setOnClickListener {
            navigator().showScreen(LoadingFragment())
        }

        binding.randomImgView.setOnClickListener {
            showSingleChoiceAlertDialog()
        }

        randomIndex = savedInstanceState?.getInt(KEY_IMAGE) ?:  getRandomIndexImg()

        updateUi()

        return binding.root
    }

    private fun sendDataToFragmentB(data: Int) {
        Log.d("index", randomIndex.toString())
        sharedViewModel.setData(data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_IMAGE, randomIndex)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRandomIndexImg(): Int {
        randomIndex = Random.nextInt(ImageList.lastIndex+1)
        return randomIndex
    }

    private fun showSingleChoiceAlertDialog() {
        val imageItems = listOf("Jade Fruit Soup", "Radish Veggie Soup", "Minty Bean Soup")
        val imageTextItems = imageItems.toTypedArray()

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Вибери супчик")
            .setSingleChoiceItems(imageTextItems, randomIndex) { dialog, which ->
                randomIndex = which
                updateUi()
                dialog.dismiss()
            }
            .create()
        dialog.show()

    }


    private fun getRandomImgURL(): String {
        randomImgURL = ImageList[randomIndex]
        return randomImgURL
    }

    private fun updateUi() {
        Glide.with(this).load(getRandomImgURL()).centerCrop().into(binding.randomImgView)
        sendDataToFragmentB(randomIndex)
    }

    companion object {

        @JvmStatic private val KEY_IMAGE = "KEY_IMAGE"

        val ImageList = listOf("https://static.wikia.nocookie.net/gensin-impact/images/2/2e/Item_Jade_Fruit_Soup.png/revision/latest?cb=20230412092834",
        "https://static.wikia.nocookie.net/gensin-impact/images/7/7e/Item_Radish_Veggie_Soup.png/revision/latest?cb=20210415140822",
        "https://static.wikia.nocookie.net/gensin-impact/images/1/16/Item_Minty_Bean_Soup.png/revision/latest?cb=20220824135810")
    }


}