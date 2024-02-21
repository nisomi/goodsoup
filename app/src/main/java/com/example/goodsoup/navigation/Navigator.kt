package com.example.goodsoup.navigation

import androidx.fragment.app.Fragment

interface Navigator {
    fun goBack()
    fun showScreen(fragment: Fragment)
}
fun Fragment.navigator(): Navigator = requireActivity() as Navigator