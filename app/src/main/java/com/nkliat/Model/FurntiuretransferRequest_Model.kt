package com.nkliat.Model

import com.chicchicken.Model.parameters
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class FurntiuretransferRequest_Model {
    @SerializedName("order_type")
    var  order_type:String?= String()
    @SerializedName("date_work")
    var  date_work:String?= String()
    @SerializedName("parameters")
    var parameters: ArrayList<parameters>
    @SerializedName("type_of_house")
    var  type_of_house:String?= String()
    @SerializedName("number_of_rooms")
    var  number_of_rooms:String?= String()
    @SerializedName("floor")
    var  floor:String?= String()
    @SerializedName("elevator")
    var  elevator:String?= String()
    @SerializedName("notes")
    var  notes:String?= String()
    @SerializedName("from_lat")
    var  from_lat:String?= String()
    @SerializedName("from_lng")
    var  from_lng:String?= String()
    @SerializedName("to_lat")
    var  to_lat:String?= String()
    @SerializedName("requestType")
    var  requestType:String?= String()
    @SerializedName("to_lng")
    var  to_lng:String?= String()
//    @SerializedName("type_of_house_to")
//    var  type_of_house_to:String?= String()
//    @SerializedName("number_of_rooms_to")
//    var  number_of_rooms_to:String?= String()
//    @SerializedName("floor_to")
//    var  floor_to:String?= String()
//    @SerializedName("elevator_to")
//    var  elevator_to:String?= String()
    @SerializedName("car_type")
    var car_type:String?= String()

    constructor(
        order_type: String?,
        date_work: String?,
        parameters: ArrayList<parameters>?,
        type_of_house: String?,
        number_of_rooms: String?,
        floor: String?,
        elevator: String?,
        notes: String?,
        from_lat: String?,
        from_lng: String?,
        to_lat: String?,
        requestType: String?,
        to_lng: String?,
//        type_of_house_to: String?,
//        number_of_rooms_to: String?,
//        floor_to: String?,
//        elevator_to: String?,
        car_type: String?
    ) {
        this.order_type = order_type
        this.date_work = date_work
        this.parameters = parameters!!
        this.type_of_house = type_of_house
        this.number_of_rooms = number_of_rooms
        this.floor = floor
        this.elevator = elevator
        this.notes = notes
        this.from_lat = from_lat
        this.from_lng = from_lng
        this.to_lat = to_lat
        this.requestType = requestType
        this.to_lng = to_lng
//        this.type_of_house_to = type_of_house_to
//        this.number_of_rooms_to = number_of_rooms_to
//        this.floor_to = floor_to
//        this.elevator_to = elevator_to
        this.car_type = car_type
    }
}