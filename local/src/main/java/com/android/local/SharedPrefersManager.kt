package com.android.local

import android.content.SharedPreferences
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
import com.android.model.home.UserData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class SharedPrefersManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun getAppLanguage(): String {
        return sharedPreferences.getString(Constants.sharedPreference_language, null) ?: LANGUAGE_AR
    }

    fun setAppLanguage(newLanguage: String) {
        sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_language, newLanguage)
        }.commit()
    }

    fun saveToken(token: String?) {
        sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_token, token)
        }.commit()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(Constants.sharedPreference_token, null)
    }


    fun saveBrandList(brandList: List<Brand>?) {
        sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_brand_list, Gson().toJson(brandList))
        }.commit()
    }

    fun getBrandList(): List<Brand>? {
        return Gson().fromJson(
            sharedPreferences.getString(
                Constants.sharedPreference_brand_list, null
            ), object : TypeToken<List<Brand>>() {}.type
        )
    }

    fun saveCategoryList(categoryList: List<Category>?) {
        sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_category_list, Gson().toJson(categoryList))
        }.commit()
    }

    fun getCategoryList(): List<Category>? {
        return Gson().fromJson(
            sharedPreferences.getString(
                Constants.sharedPreference_category_list, null
            ), object : TypeToken<List<Category>>() {}.type
        )
    }

    fun saveUserData(userData: UserData) {
        sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_user_data, Gson().toJson(userData))
        }.commit()
    }

    fun getUserData(): UserData? {
        return Gson().fromJson(
            sharedPreferences.getString(
                Constants.sharedPreference_user_data, null
            ), object : TypeToken<UserData>() {}.type
        )
    }

    fun logout() {
        sharedPreferences.edit().remove(Constants.sharedPreference_user_data).commit()
    }

    fun isUserLongedIn(): Boolean {
        return sharedPreferences.contains(Constants.sharedPreference_user_data)
    }

    fun showAccountLogin(): Boolean {
        val user = getUserData()
        return user?.let {
            it.phone.isNullOrEmpty().not()
        } ?: kotlin.run {
            false
        }
    }

    fun isFirstTimeOpened(): Boolean {
        return sharedPreferences.contains(Constants.first_time_opened).not()
    }

    fun setFirstTimeOpened() {
        sharedPreferences.edit().apply {
            putString(Constants.first_time_opened, Constants.first_time_opened)
        }.commit()
    }

    fun saveRecentSearchList(recentSearchList: List<String>) {
        sharedPreferences.edit().apply {
            putString(Constants.recent_search_list, Gson().toJson(recentSearchList))
        }.commit()
    }

    fun getRecentSearchList(): List<String>? {
        return Gson().fromJson(
            sharedPreferences.getString(Constants.recent_search_list, null),
            object : TypeToken<List<String>>() {}.type
        )
    }

    fun saveFcmToken(fcmToken: String) {
        sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_fcm_token, fcmToken)
        }.commit()
    }

    fun getFcmToken(): String? {
        return sharedPreferences.getString(Constants.sharedPreference_fcm_token, null)
    }

    companion object {
        const val LANGUAGE_AR = "ar"
        const val LANGUAGE_EN = "en"
    }

}