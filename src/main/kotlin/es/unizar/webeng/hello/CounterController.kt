package es.unizar.webeng.hello

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam


/**
 * CounterController class implements the functionality of init, increase and decrease a counter.
 *
 * @author Eduardo Ruiz Cordón (764539)
 * @version 1.0
 * @since 09/24/2020
 */


@Controller
class CounterController {
    /**
     * initCounter is a method who is requested by a GET petition from the wellcome page. The method return the
     * counter started to the INITIAL_VALUE.
     * @param model The Map where the method introduce the initial value of the counter
     * @return JSP to the page viewCounter.html
     */
    @GetMapping("/init-counter")
    fun initCounter(model: MutableMap<String?, Any?>): String {
        model["counter"] = INITIAL_VALUE
        return "counter"
    }

    /**
     * increaseCounter is a method that increase the counter by 1 and put the value in the map pass by parameter.
     * @param counter current value of the counter
     * @param model The Map where is saved the value of the counter
     */
    @Throws(NumberFormatException::class)
    private fun increaseCounter(counter: Int, model: MutableMap<String?, Any?>) {
        var counter = counter
        counter++
        model["counter"] = counter
    }

    /**
     * decreaseCounter is a method that decrease the counter by 1 and put the value in the map pass by parameter.
     * If the value of the parameter counter is equal or less than INITIAL_VALUE return put the counter
     * to the INITIAL_VALUE
     * @param counter current value of the counter
     * @param model the Map where is saved the value of the counter
     */
    @Throws(NumberFormatException::class)
    private fun decreaseCounter(counter: Int, model: MutableMap<String?, Any?>) {
        var counter = counter
        if (counter > INITIAL_VALUE) {
            counter--
        } else {
            counter = INITIAL_VALUE
        }
        model["counter"] = counter
    }

    /**
     * actionCounter is a method which is invoked by a POST petition. This method change the value of the counter
     * based on the button pushed by the user and saved the value in a Map.
     * @param counter current value of the counter
     * @param button show the action which is selected by the user
     * @param model the Map where is saved the value of the counter
     * @return JSP to the page viewCounter.html
     */
    @RequestMapping(value = ["/action-counter"], method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun actionCounter(@RequestParam("counter") counter: String, @RequestParam("button") button: String?,
                      model: MutableMap<String?, Any?>): String {
        try {
            when (button) {
                INITIAL -> {
                    val counter1 = counter.toInt()
                    initCounter(model)
                }
                INCREASE -> increaseCounter(counter.toInt(), model)
                DECREASE -> decreaseCounter(counter.toInt(), model)
                else -> model["counter"] = ERROR
            }
        } catch (e: NumberFormatException) {
            model["counter"] = ERROR
        }
        return "counter"
    }

    companion object {
        private const val INITIAL_VALUE = 0
        private const val ERROR = "ERROR"
        private const val INITIAL = "init"
        private const val INCREASE = "increase"
        private const val DECREASE = "decrease"
    }
}
