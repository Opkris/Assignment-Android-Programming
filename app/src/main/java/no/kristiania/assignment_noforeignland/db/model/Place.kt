package no.kristiania.assignment_noforeignland.db.model

class Place{
    var id : String? = null
    var WebId : String? = null
    var name: String? = null
    var comment: String? = null
    var lat: String? = null
    var lon: String? = null
    var bannerUrl: String? = null

    constructor(){}

    constructor(WebId: String, name: String?) {
        this.id = id
        this.WebId = WebId
        this.name = name
    }

    constructor(
        id: String?,
        WebId: String?,
        name: String?,
        comment: String?,
        lat: String?,
        lon: String?,
        bannerUrl: String?
    ) {
        this.id = id
        this.WebId = WebId
        this.name = name
        this.comment = comment
        this.lat = lat
        this.lon = lon
        this.bannerUrl = bannerUrl
    }


}