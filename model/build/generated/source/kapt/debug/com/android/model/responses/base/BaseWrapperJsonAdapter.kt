// Code generated by moshi-kotlin-codegen. Do not edit.
@file:Suppress("DEPRECATION", "unused", "ClassName", "REDUNDANT_PROJECTION",
    "RedundantExplicitType", "LocalVariableName", "RedundantVisibilityModifier",
    "PLATFORM_CLASS_MAPPED_TO_KOTLIN", "IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")

package com.android.model.responses.base

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.`internal`.Util
import java.lang.NullPointerException
import java.lang.reflect.Constructor
import java.lang.reflect.Type
import kotlin.Any
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.emptySet
import kotlin.jvm.Volatile
import kotlin.text.buildString

public class BaseWrapperJsonAdapter<T>(
  moshi: Moshi,
  types: Array<Type>
) : JsonAdapter<BaseWrapper<T>>() {
  init {
    require(types.size == 1) {
      buildString { append("TypeVariable mismatch: Expecting ").append(1).append(" type for generic type variables [").append("T").append("], but received ").append(types.size) }
    }
  }

  private val options: JsonReader.Options = JsonReader.Options.of("data", "message", "hasMoreData",
      "code")

  private val tNullableAnyAdapter: JsonAdapter<T> = moshi.adapter(types[0], emptySet(), "data")

  private val nullableStringAdapter: JsonAdapter<String?> = moshi.adapter(String::class.java,
      emptySet(), "message")

  private val nullableBooleanAdapter: JsonAdapter<Boolean?> =
      moshi.adapter(Boolean::class.javaObjectType, emptySet(), "hasMoreData")

  private val nullableIntAdapter: JsonAdapter<Int?> = moshi.adapter(Int::class.javaObjectType,
      emptySet(), "code")

  @Volatile
  private var constructorRef: Constructor<BaseWrapper<T>>? = null

  public override fun toString(): String = buildString(33) {
      append("GeneratedJsonAdapter(").append("BaseWrapper").append(')') }

  public override fun fromJson(reader: JsonReader): BaseWrapper<T> {
    var data_: T? = null
    var message: String? = null
    var hasMoreData: Boolean? = null
    var code: Int? = null
    var mask0 = -1
    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(options)) {
        0 -> data_ = tNullableAnyAdapter.fromJson(reader) ?: throw Util.unexpectedNull("data_",
            "data", reader)
        1 -> {
          message = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 1).inv()
          mask0 = mask0 and 0xfffffffd.toInt()
        }
        2 -> {
          hasMoreData = nullableBooleanAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 2).inv()
          mask0 = mask0 and 0xfffffffb.toInt()
        }
        3 -> {
          code = nullableIntAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 3).inv()
          mask0 = mask0 and 0xfffffff7.toInt()
        }
        -1 -> {
          // Unknown name, skip it.
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()
    if (mask0 == 0xfffffff1.toInt()) {
      // All parameters with defaults are set, invoke the constructor directly
      return  BaseWrapper<T>(
          `data` = data_ ?: throw Util.missingProperty("data_", "data", reader),
          message = message,
          hasMoreData = hasMoreData,
          code = code
      )
    } else {
      // Reflectively invoke the synthetic defaults constructor
      @Suppress("UNCHECKED_CAST")
      val localConstructor: Constructor<BaseWrapper<T>> = this.constructorRef ?:
          (BaseWrapper::class.java.getDeclaredConstructor(Any::class.java, String::class.java,
          Boolean::class.javaObjectType, Int::class.javaObjectType, Int::class.javaPrimitiveType,
          Util.DEFAULT_CONSTRUCTOR_MARKER) as Constructor<BaseWrapper<T>>).also {
          this.constructorRef = it }
      return localConstructor.newInstance(
          data_ ?: throw Util.missingProperty("data_", "data", reader),
          message,
          hasMoreData,
          code,
          mask0,
          /* DefaultConstructorMarker */ null
      )
    }
  }

  public override fun toJson(writer: JsonWriter, value_: BaseWrapper<T>?): Unit {
    if (value_ == null) {
      throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
    }
    writer.beginObject()
    writer.name("data")
    tNullableAnyAdapter.toJson(writer, value_.`data`)
    writer.name("message")
    nullableStringAdapter.toJson(writer, value_.message)
    writer.name("hasMoreData")
    nullableBooleanAdapter.toJson(writer, value_.hasMoreData)
    writer.name("code")
    nullableIntAdapter.toJson(writer, value_.code)
    writer.endObject()
  }
}
