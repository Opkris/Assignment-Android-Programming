package no.kristiania.assignment_noforeignland.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["PlaceName"], unique = true)])
class PlaceEntity {

    @PrimaryKey (autoGenerate = false)
    @ColumnInfo(name = "Id")
    var placeId: Long = 0

    @ColumnInfo (name ="PlaceName")
    var placeName:  String = ""

     @ColumnInfo (name ="Lon")
    var placeLon:  Double = 0.0

    @ColumnInfo (name ="Lat")
    var placeLat:  Double = 0.0

}