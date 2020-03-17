package no.kristiania.assignment_noforeignland


//{
//    "type": "FeatureCollection",
//    "features": [
//    {
//        "type": "Feature",
//        "properties": {
//            "name": "Sidi Bou Said Fuel",
//            "icon": "nfl_fuel",
//            "id": 4579557281628160
//    },
//        "geometry": {
//            "type": "Point",
//            "coordinates": [
//            10.350725577156027,
//            36.86592310537196
//        ]
//    }
//    }
//class Coordinates(val latitude: Long, val longitude: Long)
//
//class HomeFeed( val features: List<Feature>)
//
//class Feature(val type: String, val properties: Properties, val geometry: Geometry)
//
//class Properties( val name: String, val icon: String, val id : Long)
//
//class Geometry( val type: String, coordinates: Coordinates)
//

class HomeFeed( val videos: List<Video>)

// val name har cap sensitive, so make sure that the name matches the properties from the HomeFeed
class Video(val id: Int, val name: String, val link: String, val imageUrl: String,
            val numberOfViews: Int, val channel: Channel)

class Channel(val name: String, val profileImageUrl: String)

class CourseLesson(val name: String, val duration: String, val number: Int, var imageUrl: String, val link: String)