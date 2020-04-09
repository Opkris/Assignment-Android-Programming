package no.kristiania.assignment_noforeignland.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["placeName"], unique = true)])
class PlaceEntity {


    @PrimaryKey (autoGenerate = false)
    var placeId: Long = 0

    @ColumnInfo (name ="placeName")
    var placeName:  String = ""

     @ColumnInfo (name ="Lon")
    var placeLon:  Double = 0.0

    @ColumnInfo (name ="Lat")
    var placeLat:  Double = 0.0

    @ColumnInfo (name ="Banner")
    var placeBanner:  String = ""

    @ColumnInfo (name ="Comment")
    var placeComment:  String = ""


}