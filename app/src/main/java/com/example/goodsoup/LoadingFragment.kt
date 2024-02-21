package com.example.goodsoup

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.goodsoup.databinding.FragmentLoadingBinding
import com.example.goodsoup.databinding.FragmentMainBinding
import com.example.goodsoup.navigation.navigator
import kotlin.properties.Delegates

class LoadingFragment:Fragment() {
    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!
    private lateinit var randomImgURL: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)

        randomImgURL = ImageList[1]
        Glide.with(this).load(randomImgURL).centerCrop().into(binding.randomImgView)

        binding.progressBar.max = 1000
        val currentProgress = 1000

        ObjectAnimator.ofInt(binding.progressBar,"progress",currentProgress)
            .setDuration(6000)
            .start()

        Handler(Looper.getMainLooper()).postDelayed({
            navigator().showScreen(DoneFragment())
        }, 6000)


        return binding.root
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            // Фрагмент стає видимим на екрані, блокуємо тикання тут
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            // Фрагмент стає невидимим, знімаємо блокування тикання
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        val ImageList = listOf(
            "https://static.wikia.nocookie.net/gensin-impact/images/d/de/Item_Delicious_Radish_Veggie_Soup.png/revision/latest?cb=20210415141037",
            "https://media.tenor.com/g2fsqKXeHG8AAAAC/paimon-emergency.gif")
    }
}