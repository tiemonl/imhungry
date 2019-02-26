package io.imhungry.model


class RootObject {
    var results:Array<Result>?=null
}

class Result {
    var geometry:Geometry?=null
    var name:String?=null
    var opening_hours:OpeningHours?=null
    var price_level:Int=0
    var rating:Double=0.0
    var types:Array<String>?=null
    var user_ratings_total:Int=0
    var vicinity:String?=null
}


class Geometry {
    var location: io.imhungry.model.Location?=null
}

class Location {
    var lat:Double=0.0
    var lng:Double=0.0
}


class OpeningHours {
    var open_now:Boolean?=null
}