package es.unizar.webeng.hello

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.net.InetAddress
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

/**
 * The HelloControler class contains the handler functions for the different requests.
 *
 *
 * The program implements an application that
 * simply displays current time and a static welcome message
 * when requested.
 *
 */
@Controller
@Api(value = "Ingeniería Web", description = "API creada para el funcionamento de la web para la práctica 1 de IW")
class HelloController {
    @Value("\${app.message:Press F5 to roll the dice}")
    private val message: String? = null

    @Value("\${app.joke_const:Hello World}")
    private val joke_const: String? = null
    private val link = "Abre este enlace"
    private fun luckyColor(): String {

        /* Current day in integer form */
        val num = currentDay.toInt()
        if (num % 5 == 0) {
            return "black"
        }
        if (num % 2 == 0) {
            return "blue"
        }
        return if (num % 3 == 0) {
            "white"
        } else "red"
    }

    /* Current day in string form */
    var sdf = SimpleDateFormat("dd/MM/yyyy")
    private val currentDay = sdf.format(Date()).substring(0, 2)

    /**
     * A controller method which is called when the root endpoint is ordered by a client.
     *
     * It modifies the model, setting into the key "time" the actual date and into the key "message" the
     * hardcoded value assigned to the attribute "message". Also includes a rolling dice result.
     *
     * @param model the MVC model
     * @return "wellcome", hardcoded
     */
    @GetMapping("/")
    @ApiOperation(value = "Operacion que muestra la hora actual y dos mensajes por pantalla", response = String::class)
    fun rollTheDice(model: MutableMap<String?, Any?>): String {

        /* Current day in integer form */
        val num = currentDay.toInt()
        if (num % 5 == 0) {
            model["luckyColor"] = "black"
        } else if (num % 2 == 0) {
            model["luckyColor"] = "blue"
        } else if (num % 3 == 0) {
            model["luckyColor"] = "white"
        } else {
            model["luckyColor"] = "red"
        }

        // random is needed to generate the seed
        val random = Random()

        // Dice result is calculated between 1 and 6 and storeged in dice
        val dice = random.nextInt(6) + 1
        model["luckyColor"] = luckyColor()
        model["time"] = Date()
        model["message"] = message
        model["joke_const"] = joke_const
        // Getting jokes with external api
        val joke = GetRequest()
        if (joke.isSuccess) {
            model["joke_plus"] = joke.plus()
            model["joke_minus"] = joke.minus()
        } else {
            model["joke_plus"] = "ERROR LOADING JOKES"
            model["joke_minus"] = "ERROR LOADING JOKES"
        }
        model["link"] = link
        model["extra_message"] = "This is an extra message. Im original enough to not make a new funtionality and just add a new message to the typical HELLO WORLD, come on guys amp it up!"
        model["dice"] = dice
        model["java"] = System.getProperty("java.version")
        try {
            val ip = InetAddress.getLocalHost()
            model["host"] = ip.hostName
            model["ip"] = ip.hostAddress
        } catch (e: UnknownHostException) {
            model["host"] = "Cannot get the host name"
            model["ip"] = "Cannot get the host address or IP"
        }
        return "wellcome"
    }

    /**
     * Handler for GET requests to "/{name}" path, where "name" can be any string.
     *
     *
     * The "name" parameter is taken directly from the path
     * This function sets the time and message to be displayed on the web
     *
     * @param model  The MVC model
     * @param name  String that contains the name used to build the message
     * @return the name of the template used
     */
    @GetMapping("/{name}")
    fun welcomeName(model: MutableMap<String?, Any?>, @PathVariable name: String): String {
        model["luckyColor"] = luckyColor()
        model["time"] = Date()
        model["message"] = "Hola $name"
        return "wellcome"
    }
}