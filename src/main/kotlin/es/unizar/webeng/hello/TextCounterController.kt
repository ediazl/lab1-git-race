package es.unizar.webeng.hello

import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.util.*


/**
 * The TextCounterController program implements an application that
 * simply counts the number of words of an input text.
 *
 * @author David Alloza Tejero (761754)
 * @version 1.0
 * @since 09/24/2020
 */
@Controller
class TextCounterController {
    /**
     * This method is called when requested by GET petition to
     * the "text word counter" webpage (/textCounter). It simply put
     * a default value to a Map so it can be read by view (the jsp file).
     *
     * @param model The MVC model
     * @return JSP name view.
     */
    @GetMapping("/textCounter")
    fun textCounter(model: MutableMap<String?, Any?>): String {
        model["numPalabras"] = 0.toString()
        return "textc"
    }

    /**
     * This method is called when requested by POST petition to
     * the "text word counter" webpage (/textCounter) with a String value.
     * Calculates the total words of the string provided if possible and
     * reutrns the result so it can be read by view (the jsp file).
     *
     * @param text The String that represents the input text
     * @param model The MVC model
     * @return JSP name view.
     */
    @ApiOperation(value = "Operation that calculates the total words of a string", response = String::class)
    @RequestMapping(value = ["/textCounter"], method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun textCounter(@RequestParam("text") text: String?, model: MutableMap<String?, Any?>): String {
        try {
            val s = StringTokenizer(text)
            val numPalabras = s.countTokens()
            model["numPalabras"] = numPalabras.toString()
        } catch (e: NumberFormatException) {
            model["numPalabras"] = "ERROR"
        }
        return "textc"
    }
}
