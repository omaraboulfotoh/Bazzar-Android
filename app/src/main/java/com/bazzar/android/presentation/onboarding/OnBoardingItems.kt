package com.bazzar.android.presentation.onboarding

import com.bazzar.android.R

class OnBoardingItems(
    val image: Int,
    val title: Int = R.string.onboarding_title,
    val desc: Int
) {
    companion object{
        fun getData(): List<OnBoardingItems>{
            return listOf(
                OnBoardingItems(image = R.drawable.onboarding1,desc= R.string.onboarding_desc1),
                OnBoardingItems(image = R.drawable.onboarding2, desc = R.string.onboarding_desc2),
                OnBoardingItems(image = R.drawable.onboarding2, desc = R.string.onboarding_desc3)
            )
        }
    }
}