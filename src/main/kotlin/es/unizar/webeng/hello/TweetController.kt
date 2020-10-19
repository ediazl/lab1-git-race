package es.unizar.webeng.hello

import com.fasterxml.jackson.databind.JsonNode
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable


/**
 * Controller for Twitter Timeline.
 * It uses WebClient to make synchronous requests to the Twitter API.
 *
 * @see [Flux vs Mono, Spring y Programacion Reactiva](https://www.arquitecturajava.com/flux-vs-mono-spring-y-la-programacion-reactiva)
 *
 * @see [Reactive REST APIs](https://www.callicoder.com/reactive-rest-apis-spring-webflux-reactive-mongo/)
 */
@Controller
@Api(value = "Twitter Timeline")
class TweetController {
    @Value("\${app.twitter_api_key}")
    private val twitter_api_key: String? = null

    @Value("\${app.twitter_api_secret_key}")
    private val twitter_api_secret_key: String? = null

    @Value("\${app.twitter_bearer}")
    private val twitter_bearer: String? = null

    /**
     * This method is called when requested by GET petition to
     * the twitter endpoint of this webpage (/twitter/{user}).
     * It prints the last 5 tweets of the user if Twitter API
     * credentials are correct and user exists. If not, it displays
     * the error.
     *
     * @param model Interface for holding attributes and
     * accessing them from Thymeleaf templates
     * @param user  twitter user whose tweets will be retrieved
     * @return Thymeleaf name view corresponding to this controller
     */
    @GetMapping("/twitter/{user}")
    @ApiOperation(value = "Shows last 5 tweets of a user", response = String::class)
    fun tweet(model: Model, @PathVariable user: String): String {
        val client = WebClient
                .builder()
                .baseUrl("https://api.twitter.com/1.1/statuses/user_timeline.json")
                .build()
        try {
            val res = client.get()
                    .uri("?screen_name=$user&count=5")
                    .header("authorization", "Bearer $twitter_bearer")
                    .retrieve()
                    .bodyToFlux(Tweet::class.java)
            model.addAttribute("user", user)
            model.addAttribute("tweets", ReactiveDataDriverContextVariable(res, 5))
        } catch (wcre: WebClientResponseException) {
            if (wcre.rawStatusCode == 404) {
                model.addAttribute("error", "Could not find the user: $user")
            } else if (wcre.rawStatusCode == 401) {
                model.addAttribute("error", "Unauthorized access to the Twitter API. Check the credentials")
            } else {
                model.addAttribute("error", "wcre.getStatusText()")
            }
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }
        return "tweet"
    }

    /**
     * Representation of a tweet.
     * It contains only the text of a tweet.
     *
     *
     * It could be improved with annotations from [Project Lombok](https://projectlombok.org/) not to have to
     * write constructors, getters and setters.
     */
    class Tweet {
        var created_at: String? = null
        var id: String? = null
        var id_str: String? = null
        var text: String? = null
        var truncated: Boolean? = null
        var entities: JsonNode? = null
        var source: String? = null
        var in_reply_to_status_id: String? = null
        var in_reply_to_status_id_str: String? = null
        var in_reply_to_user_id: String? = null
        var in_reply_to_user_id_str: String? = null
        var in_reply_to_screen_name: String? = null
        var user: JsonNode? = null
        var geo: String? = null
        var coordinates: String? = null
        var place: String? = null
        var contributors: String? = null
        var is_quote_status: Boolean? = null
        var quoted_status_id: String? = null
        var quoted_status_id_str: String? = null
        var quoted_status: JsonNode? = null
        var retweet_count: String? = null
        var favorite_count: String? = null
        var favorited: String? = null
        var retweeted: String? = null
        var possibly_sensitive: String? = null
        var lang: String? = null

        constructor() {}
        constructor(created_at: String?, id: String?, id_str: String?, text: String?, truncated: Boolean?, entities: JsonNode?,
                    source: String?, in_reply_to_status_id: String?, in_reply_to_status_id_str: String?,
                    in_reply_to_user_id: String?, in_reply_to_user_id_str: String?, in_reply_to_screen_name: String?,
                    user: JsonNode?, geo: String?, coordinates: String?, place: String?, contributors: String?,
                    is_quote_status: Boolean?, quoted_status_id: String?, quoted_status_id_str: String?,
                    quoted_status: JsonNode?, retweet_count: String?, favorite_count: String?, favorited: String?,
                    retweeted: String?, possibly_sensitive: String?, lang: String?) {
            this.created_at = created_at
            this.id = id
            this.id_str = id_str
            this.text = text
            this.truncated = truncated
            this.entities = entities
            this.source = source
            this.in_reply_to_status_id = in_reply_to_status_id
            this.in_reply_to_status_id_str = in_reply_to_status_id_str
            this.in_reply_to_user_id = in_reply_to_user_id
            this.in_reply_to_user_id_str = in_reply_to_user_id_str
            this.in_reply_to_screen_name = in_reply_to_screen_name
            this.user = user
            this.geo = geo
            this.coordinates = coordinates
            this.place = place
            this.contributors = contributors
            this.is_quote_status = is_quote_status
            this.quoted_status_id = quoted_status_id
            this.quoted_status_id_str = quoted_status_id_str
            this.quoted_status = quoted_status
            this.retweet_count = retweet_count
            this.favorite_count = favorite_count
            this.favorited = favorited
            this.retweeted = retweeted
            this.possibly_sensitive = possibly_sensitive
            this.lang = lang
        }
    }
}

