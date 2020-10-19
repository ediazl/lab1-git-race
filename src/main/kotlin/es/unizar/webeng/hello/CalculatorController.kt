package es.unizar.webeng.hello

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
/**
 * The CalculatorController program implements an application that
 * simply performs the sum of two integer values when requested
 *
 * @author Daniel Huici Meseguer (758635)
 * @version 1.0
 * @since 09/17/2020
 */
@Controller
class CalculatorController {
    /**
     * This method is called when requested by GET petition to
     * the calculator webpage (/ultra-calculator). It simply puts
     * default values to a Map so it can be read by view (JSP).
     *
     * @param model This is the map where we put the data
     * @return JSP name view.
     */
    @GetMapping("/ultra-calculator")
    fun calculator(model: MutableMap<String?, Any?>): String {
        model["values"] = 0.toString()
        model["message"] = 0.toString()
        return "calc"
    }

    /**
     * This method is called when requested by POST petition to
     * the calculator webpage (/ultra-calculator) with two values.
     * Performs a sum of the values provided if possible and returns
     * the result so it can be read by view (JSP).
     *
     * @param parameter1 First value of the sum
     * @param parameter2 Second value of the sum
     * @param model This is the map where we put the data
     * @return JSP name view with the result of the sum or
     * "ERROR" in case of non numerical characters
     */
    @RequestMapping(value = ["/ultra-calculator"], method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun calculator(@RequestParam("value1") parameter1: String, @RequestParam("value2") parameter2: String,
                   model: MutableMap<String?, Any?>): String {
        model["values"] = "$parameter1 + $parameter2"
        try {
            val first = parameter1.toInt()
            val second = parameter2.toInt()
            model["message"] = (first + second).toString()
        } catch (e: NumberFormatException) {
            model["message"] = "ERROR"
        }
        return "calc"
    }
}