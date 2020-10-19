package es.unizar.webeng.hello

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author Martín Gascón - 764429
 *
 * @version 1.0 (9/2020) Martín Gascón
 * The service is able to convert form binary, hexadecimal, decimal and octal base to a binary, hexadecimal, decimal and octal base.
 *
 * BaseConverterController convert numbers between different bases
 */
@Controller
class BaseConverterController internal constructor() {
    var bases: MutableMap<*, *> = HashMap<String, Int>()

    /**
     * Receive the get petitions, in this case it attend
     * from the request of the main page (/base-calculator).
     * @param model The return data container.
     * @return  Name of the JSP
     */
    @GetMapping("/base-converter")
    fun calculator(model: MutableMap<String?, Any?>): String {
        model["values"] = ""
        model["error_message"] = ""
        return "converter"
    }

    /**
     * Receive the post petitions, in this case it attend
     * from the conversion requests.
     * Converts the value passed as parameter into a target base
     * @param originalBase Base of the number to transform.
     * @param targetBase Desired base of the number to transform.
     * @param originalNumber Number to transform
     * @param model The return data container.
     * @return Name of the JSP
     */
    @PostMapping(value = ["/base-converter"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun converter(@RequestParam("original_base") originalBase: String, @RequestParam("target_base") targetBase: String,
                  @RequestParam("original_number") originalNumber: String,
                  model: MutableMap<String?, Any?>): String {
        var retval: String? = null
        try {
            val decimalNum = originalNumber.toInt((bases[originalBase] as Int?)!!)
            retval = when (targetBase) {
                "hex" -> Integer.toHexString(decimalNum)
                "oct" -> Integer.toOctalString(decimalNum)
                "dec" -> decimalNum.toString()
                "bin" -> Integer.toBinaryString(decimalNum)
                else -> throw IllegalStateException("Unexpected value: $targetBase")
            }
            model["values"] = "$originalNumber in base $originalBase to  $targetBase is $retval"
        } catch (e: NumberFormatException) {
            (model as MutableMap)["error_message"] = "Error"
        }
        return "converter"
    }

    init {
        (bases as HashMap<String, Int>)["hex"] = 16
        (bases as HashMap<String, Int>)["dec"] = 10
        (bases as HashMap<String, Int>)["oct"] = 8
        (bases as HashMap<String, Int>)["bin"] = 2
    }
}