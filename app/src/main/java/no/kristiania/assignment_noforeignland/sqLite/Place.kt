package no.kristiania.assignment_noforeignland.sqLite

class Place{
    var id : Long = 0
    var name: String? = null

    constructor(){}

    constructor(id: Long, name: String?) {
        this.id = id
        this.name = name
    }
}