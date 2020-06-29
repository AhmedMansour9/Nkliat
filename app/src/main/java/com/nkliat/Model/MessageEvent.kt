package com.nkliat.Model

 class MessageEvent {
     var Message:String = ""
     var From_Lat:String = ""
     var From_Lng:String = ""
     var To_Lat:String = ""
     var To_Lng:String = ""
     var Address:String = ""

     constructor(
         Message: String,
         From_Lat: String,
         From_Lng: String,
         To_Lat: String,
         To_Lng: String,
         Address: String
     ) {
         this.Message = Message
         this.From_Lat = From_Lat
         this.From_Lng = From_Lng
         this.To_Lat = To_Lat
         this.To_Lng = To_Lng
         this.Address = Address
     }
 }