package no.kristiania.assignment_noforeignland

import android.widget.ImageView
import java.util.*


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
class Coordinates(val latitude: Long, val longitude: Long)

class HomeFeed( val features: Array<Feature>)

class Feature(val type: String, val properties: Properties, val geometry: Geometry)

class Properties( val name: String, val icon: String, val id : Long)

class Geometry( val type: String, coordinates: Coordinates)



/************** Id site ****************/
class FromPlaceId(val place: Place)

class Place(val name: String, val comments: String, val icon: ImageView)


//an array of objects is populated in square brackets
//{
//    "snapshots": [],
//    "place": {
//    "id": 5039941851545600,
//    "type": "ANCHORAGE",
//    "name": "Uttoskavågen",
//    "lat": 60.655452310471574,
//    "lon": 4.941390971005966,
//    "countryCode": "NO",
//    "comments": "<p><br></p>",
//    "meta": {},
//    "blogCount": 0,
//    "externalLink1": "",
//    "externalLinkDescription1": "",
//    "externalLink2": "",
//    "externalLinkDescription2": "",
//    "dieselPrice": 0.0,
//    "dieselPriceUpdatedMs": 1572526264377,
//    "gasolinePrice": 0.0,
//    "gasolinePriceUpdatedMs": 1572526264377,
//    "maxLiftWeightTonnes": 0,
//    "allowsExternalContractors": false,
//    "canWorkOnOwnBoat": false,
//    "canStayOnOwnBoat": false,
//    "priceBandHighSeason": "Unknown",
//    "priceBandLowSeason": "Unknown",
//    "winterCommunity": false,
//    "protectionFrom": "N,S,E,W,NE,SE,NW,SW",
//    "addedMs": 1572526264532,
//    "addedBy": "Sebastian Westerhoff",
//    "addedById": 4697288777662464,
//    "updatedMs": 0,
//    "updatedBy": "",
//    "updatedById": null,
//    "col": "blue",
//    "icon": "anchorage-26.png",
//    "mapboxIcon": "nfl_anchorage",
//    "stars": 0,
//    "banner": "",
//    "images": [],
//    "reviews": []
//}
//}



/***************************************************************************************************/
/***************************************************************************************************/
/***************************************************************************************************/

//
//class HomeFeed( val videos: List<Video>)
//
//// val name har cap sensitive, so make sure that the name matches the properties from the HomeFeed
//class Video(val id: Int, val name: String, val link: String, val imageUrl: String,
//            val numberOfViews: Int, val channel: Channel)
//
//class Channel(val name: String, val profileImageUrl: String)
//
//class CourseLesson(val name: String, val duration: String, val number: Int, var imageUrl: String, val link: String)