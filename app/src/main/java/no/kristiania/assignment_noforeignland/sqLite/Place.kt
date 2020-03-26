package no.kristiania.assignment_noforeignland.sqLite

class Place{
    var id : String? = null
    var WebId : String? = null
    var name: String? = null
    var comment: String? = null
    var lat: String? = null
    var lon: String? = null
    var bannerUrl: String? = null

    constructor(){}

    constructor(id: String, WebId: String, name: String?) {
        this.id = id
        this.WebId = WebId
        this.name = name
    }
}