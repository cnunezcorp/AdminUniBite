package com.adminunibite.app.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails(): Serializable {
    var userUid: String? = null
    var userName: String? = null
    var foodNames: MutableList<String>? = null
    var foodImages: MutableList<String>? = null
    var foodPrices: MutableList<String>? = null
    var foodQuantites: MutableList<Int>? = null
    var totalPrice: String? = null
    var phoneNumber: String? = null
    var orderAccepted: Boolean = false
    var paymentReceived: Boolean = false
    var itemPushKey: String? = null
    var currentTime: Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        foodNames = parcel.createStringArrayList()
        foodImages = parcel.createStringArrayList()
        foodPrices = parcel.createStringArrayList()
        foodQuantites = parcel.createIntArray()?.toMutableList()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeStringList(foodNames)
        parcel.writeStringList(foodImages)
        parcel.writeStringList(foodPrices)
        parcel.writeIntArray(foodQuantites?.toIntArray())
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

    fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails = OrderDetails(parcel)
        override fun newArray(size: Int): Array<OrderDetails?> = arrayOfNulls(size)
    }
}