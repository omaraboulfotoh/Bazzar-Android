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
    val LANGUAGE_AR = "ar"
    val LANGUAGE_EN = "en"

    fun getAppLanguage(): String {
        return sharedPreferences.getString(Constants.sharedPreference_language, null)
            ?: LANGUAGE_AR
    }

    fun setAppLanguage(newLanguage: String) {
        var preferEdit = sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_language, newLanguage)
        }
        preferEdit.commit()
    }

    fun saveToken(token: String?) {
        var preferEdit = sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_token, token)
        }
        preferEdit.commit()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(Constants.sharedPreference_token, null)
    }


    fun saveBrandList(brandList: List<Brand>?) {
        var preferEdit = sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_brand_list, Gson().toJson(brandList))
        }
        preferEdit.commit()
    }

    fun getBrandList(): List<Brand>? {
        return Gson().fromJson(
            sharedPreferences.getString(
                Constants.sharedPreference_brand_list,
                null
            ),
            object : TypeToken<List<Brand>>() {}.type
        )
    }

    fun saveProductList(productList: List<Product>?) {
        var preferEdit = sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_product_list, Gson().toJson(productList))
        }
        preferEdit.commit()
    }

    fun getProductList(): List<Product>? {
        return Gson().fromJson(
            sharedPreferences.getString(
                Constants.sharedPreference_product_list,
                null
            ), object : TypeToken<List<Product>>() {}.type
        )
    }

    fun saveCategoryList(categoryList: List<Category>?) {
        var preferEdit = sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_category_list, Gson().toJson(categoryList))
        }
        preferEdit.commit()
    }

    fun getCategoryList(): List<Category>? {
        return Gson().fromJson(
            sharedPreferences.getString(
                Constants.sharedPreference_category_list,
                null
            ), object : TypeToken<List<Category>>() {}.type
        )
    }

    fun saveUserData(userData: UserData) {
        var preferEdit = sharedPreferences.edit().apply {
            putString(Constants.sharedPreference_user_data, Gson().toJson(userData))
        }
        preferEdit.commit()
    }

    fun getUserData(): UserData? {
        return Gson().fromJson(
            sharedPreferences.getString(
                Constants.sharedPreference_user_data,
                null
            ), object : TypeToken<UserData>() {}.type
        )
    }

}