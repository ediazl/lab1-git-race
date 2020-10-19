package es.unizar.webeng.hello


/**
 * DTO JokeResponse with standard getters and setters
 */
class JokeResponse {
    var id = 0
    var type: String? = null
    var setup: String? = null
    var punchline: String? = null

    constructor() {}
    constructor(id: Int, type: String?, setup: String?, punchline: String?) {
        this.id = id
        this.type = type
        this.setup = setup
        this.punchline = punchline
    }

    override fun toString(): String {
        return "[$id]:(+ $setup, - $punchline)"
    }
}