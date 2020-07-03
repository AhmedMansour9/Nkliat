package com.chicchicken.Model

import com.google.gson.annotations.SerializedName

class parameters {
    @SerializedName("id")
    private var id: Int? = null
    @SerializedName("value")
    private var value: Int? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getTitle(): Int? {
        return value
    }

    fun setTitle(value: Int?) {
        this.value = value
    }


}