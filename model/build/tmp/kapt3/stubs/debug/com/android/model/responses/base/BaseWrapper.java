package com.android.model.responses.base;

import java.lang.System;

/**
 * Created by Mohamed Shalan on 6/12/20.
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0017\b\u0087\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B1\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u0016\u001a\u00028\u0000H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000fJ\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\fJB\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001bJ\u0013\u0010\u001c\u001a\u00020\u00072\b\u0010\u001d\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\tH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0005H\u00d6\u0001R\u0015\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0003\u001a\u00028\u0000\u00a2\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006 "}, d2 = {"Lcom/android/model/responses/base/BaseWrapper;", "T", "", "data", "message", "", "hasMoreData", "", "code", "", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Integer;)V", "getCode", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getData", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getHasMoreData", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getMessage", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "copy", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Integer;)Lcom/android/model/responses/base/BaseWrapper;", "equals", "other", "hashCode", "toString", "model_debug"})
@com.squareup.moshi.JsonClass(generateAdapter = true)
public final class BaseWrapper<T extends java.lang.Object> {
    private final T data = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String message = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean hasMoreData = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer code = null;
    
    /**
     * Created by Mohamed Shalan on 6/12/20.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.android.model.responses.base.BaseWrapper<T> copy(T data, @org.jetbrains.annotations.Nullable()
    java.lang.String message, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean hasMoreData, @org.jetbrains.annotations.Nullable()
    java.lang.Integer code) {
        return null;
    }
    
    /**
     * Created by Mohamed Shalan on 6/12/20.
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * Created by Mohamed Shalan on 6/12/20.
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * Created by Mohamed Shalan on 6/12/20.
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public BaseWrapper(T data, @org.jetbrains.annotations.Nullable()
    java.lang.String message, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean hasMoreData, @org.jetbrains.annotations.Nullable()
    java.lang.Integer code) {
        super();
    }
    
    public final T component1() {
        return null;
    }
    
    public final T getData() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getHasMoreData() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getCode() {
        return null;
    }
}