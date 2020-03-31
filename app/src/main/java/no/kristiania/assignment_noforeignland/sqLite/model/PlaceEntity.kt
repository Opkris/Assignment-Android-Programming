package no.kristiania.assignment_noforeignland.sqLite.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["WebId","PlaceName"], unique = true)])
class PlaceEntity {

    @PrimaryKey (autoGenerate = true)
    var placeId: Int =0

    @ColumnInfo(name = "WebId")
    var placeWebId: String =""

    @ColumnInfo (name ="PlaceName")
    var placeName:  String =""

}