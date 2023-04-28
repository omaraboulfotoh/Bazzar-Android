package com.android.local;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\u0006J\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rJ\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\rJ\u000e\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\rJ\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u0016\u0010\u0016\u001a\u00020\u00172\u000e\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rJ\u0016\u0010\u0019\u001a\u00020\u00172\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\rJ\u0016\u0010\u001b\u001a\u00020\u00172\u000e\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\rJ\u0010\u0010\u001d\u001a\u00020\u00172\b\u0010\u001e\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u0015J\u000e\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u0006R\u0014\u0010\u0005\u001a\u00020\u0006X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0006X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/android/local/SharedPrefersManager;", "", "sharedPreferences", "Landroid/content/SharedPreferences;", "(Landroid/content/SharedPreferences;)V", "LANGUAGE_AR", "", "getLANGUAGE_AR", "()Ljava/lang/String;", "LANGUAGE_EN", "getLANGUAGE_EN", "getAppLanguage", "getBrandList", "", "Lcom/android/model/home/Brand;", "getCategoryList", "Lcom/android/model/home/Category;", "getProductList", "Lcom/android/model/home/Product;", "getToken", "getUserData", "Lcom/android/model/home/UserData;", "saveBrandList", "", "brandList", "saveCategoryList", "categoryList", "saveProductList", "productList", "saveToken", "token", "saveUserData", "userData", "setAppLanguage", "newLanguage", "local_debug"})
public final class SharedPrefersManager {
    private final android.content.SharedPreferences sharedPreferences = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String LANGUAGE_AR = "ar";
    @org.jetbrains.annotations.NotNull
    private final java.lang.String LANGUAGE_EN = "en";
    
    @javax.inject.Inject
    public SharedPrefersManager(@org.jetbrains.annotations.NotNull
    android.content.SharedPreferences sharedPreferences) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLANGUAGE_AR() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLANGUAGE_EN() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAppLanguage() {
        return null;
    }
    
    public final void setAppLanguage(@org.jetbrains.annotations.NotNull
    java.lang.String newLanguage) {
    }
    
    public final void saveToken(@org.jetbrains.annotations.Nullable
    java.lang.String token) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getToken() {
        return null;
    }
    
    public final void saveBrandList(@org.jetbrains.annotations.Nullable
    java.util.List<com.android.model.home.Brand> brandList) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.android.model.home.Brand> getBrandList() {
        return null;
    }
    
    public final void saveProductList(@org.jetbrains.annotations.Nullable
    java.util.List<com.android.model.home.Product> productList) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.android.model.home.Product> getProductList() {
        return null;
    }
    
    public final void saveCategoryList(@org.jetbrains.annotations.Nullable
    java.util.List<com.android.model.home.Category> categoryList) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.android.model.home.Category> getCategoryList() {
        return null;
    }
    
    public final void saveUserData(@org.jetbrains.annotations.NotNull
    com.android.model.home.UserData userData) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.android.model.home.UserData getUserData() {
        return null;
    }
}