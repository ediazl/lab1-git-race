package es.unizar.webeng.hello

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 *
 * @author Luis García Garcés
 * @nia 739202
 */
@Controller
class RingsQuote {
    /*Classes to contact with the quotes provider API */
    private val cliente = HttpClient.newHttpClient()
    private var request: HttpRequest? = null
    private var response: HttpResponse<String>? = null

    //URI
    private val API_URI = "https://the-one-api.dev/v2/quote"

    //API Key
    @Value("\${app.api_key}")
    private val api_key: String? = null
    private var quoteChosen = ""

    /***
     *
     * @param This method does not need parameters
     * @return "lordrings"
     */
    @GetMapping("/rings-quote")
    fun findLOTRquote(model: MutableMap<String?, Any?>): String {
        request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", api_key)
                .uri(URI.create(API_URI))
                .build()
        try {
            response = cliente.send(request,
                    HttpResponse.BodyHandlers.ofString())

            //json parse
            val text = response!!.body()
            val requiredString = text.substring(text.indexOf("[") + 1, text.indexOf("]"))
            val phrases = requiredString.split("\\{").toTypedArray()
            val random = (Math.random() * (phrases.size - 1)).toInt()
            phrases[random] = phrases[random].substring(phrases[random].indexOf("dialog") + 1, phrases[random].indexOf("movie"))
            phrases[random] = phrases[random].substring(phrases[random].indexOf(":") + 1, phrases[random].indexOf(","))
            phrases[random] = phrases[random].replace("\"".toRegex(), "")
            quoteChosen = phrases[random]
            model["prhaseoftherings"] = quoteChosen
        } //In case it's not possible to establish a connection with the API
        // A default value is set
        catch (error: Exception) {
            quoteChosen = "The API is not working anymore!"
        }
        model["prhaseoftherings"] = quoteChosen
        return "lordrings"
    }
}