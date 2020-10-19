package es.unizar.webeng.hello

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * PasswordController is the program that returns a safe password from one or
 * more words entered by the user. The secret key is the actual date. After
 * the password is created, you can modify it adding characters at random
 * positions
 *
 * @author Alvaro Garcia Diaz (760704)
 *
 */


@Controller
class PasswordController {
    /**
     * Calculates the number of occurrences of special characters, numbers and
     * uppercase and lowercase letters of the password passed by parameter
     *
     * @param model The map with the data
     * @param pass The password
     * @param len Length of the password
     */
    private fun statistics(model: MutableMap<String, Any?>, pass: String?, len: Int) {
        var num = 0
        var upp = 0
        var spe = 0
        var low = 0
        for (i in 0 until len) {
            if (Character.isUpperCase(pass!![i])) ++upp else if (Character.isLowerCase(pass[i])) ++low else if (Character.isDigit(pass[i])) ++num else ++spe
        }
        model["len"] = len
        model["upp"] = upp
        model["low"] = low
        model["spe"] = spe
        model["num"] = num
    }

    /**
     * Returns the name of the JSP when requested by a GET petition to /password
     *
     * @return JSP name
     */
    @GetMapping("/pass/password")
    fun passGen(): String {
        return "pass"
    }

    /**
     * Generates a password from the words entered by the user and a secret key
     * which is the actual date. It uses AES as the encryption method
     *
     * @param enc The word or words entered by the user
     * @param model The map with the data
     * @return JSP name (pass if incorrect, otherwise showpass)
     */
    @RequestMapping(value = ["/pass/showpassword"], method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun passGen(@RequestParam("word") enc: String, model: MutableMap<String, Any?>): String {
        /**
         * If the word entered is empty, the password can't be generated
         */
        if (enc.trim { it <= ' ' }.length == 0) {
            return "pass"
        }
        var secretKey: SecretKeySpec? = null
        /**
         * The first step is to define the secret key to encrypt the words
         */
        try {
            val sha = MessageDigest.getInstance("SHA-1")
            var key = sha.digest(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()).toByteArray(charset("UTF-8")))
            key = Arrays.copyOf(key, 32)
            secretKey = SecretKeySpec(key, "AES")
        } catch (e: NoSuchAlgorithmException) {
            return "pass"
        } catch (e: UnsupportedEncodingException) {
            return "pass"
        }
        /**
         * The last step is to encrypt the words entered by the user with the
         * secret key from the step before and return the encrypted password
         * using AES as the encryption method
         */
        var pass: String? = null
        try {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            pass = Base64.getEncoder().encodeToString(cipher.doFinal(enc.toByteArray(charset("UTF-8"))))
            model["password"] = pass
        } catch (e: Exception) {
            return "pass"
        }
        statistics(model, pass, pass.length)
        return "showpass"
    }

    /**
     * Modify the password adding at random positions a number of characters
     * of different types (uppercase, lowercase, digits, special)
     *
     * @param add Number of random characters
     * @param spe Number of special characters
     * @param low Number of lowercase letters
     * @param upp Number of uppercase letters
     * @param num Number of digits
     * @param pass The password to modify
     * @param model The map with the data
     * @return JSP name
     */
    @RequestMapping(value = ["/modPass"], method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun modifyPass(@RequestParam("add") add: String,
                   @RequestParam("special") spe: String, @RequestParam("lower") low: String,
                   @RequestParam("upper") upp: String, @RequestParam("numb") num: String,
                   @RequestParam("password") pass: String, model: MutableMap<String, Any?>): String {
        var pass = pass
        var ran = 0
        var str = 0
        var min = 0
        var may = 0
        var dig = 0
        var len = pass.length
        try {
            ran = add.toInt()
            str = spe.toInt()
            min = low.toInt()
            may = upp.toInt()
            dig = num.toInt()
        } catch (e: NumberFormatException) {
            model["password"] = pass
            statistics(model, pass, len)
            return "showpass"
        }
        if (ran < 0) ran = 0
        if (str < 0) str = 0
        if (min < 0) min = 0
        if (may < 0) may = 0
        if (dig < 0) dig = 0
        var tot = ran + str + min + may + dig
        while (tot > 0) {
            var pos = ThreadLocalRandom.current().nextInt(0, len)
            if (str > 0) {
                var choose = ThreadLocalRandom.current().nextInt(0, 4)
                choose = if (pos == 0) ThreadLocalRandom.current().nextInt(32, 48) else if (pos == 1) ThreadLocalRandom.current().nextInt(58, 65) else if (pos == 2) ThreadLocalRandom.current().nextInt(91, 97) else ThreadLocalRandom.current().nextInt(123, 127)
                pass = pass.substring(0, pos) + choose.toChar() + pass.substring(pos)
                --str
            } else if (min > 0) {
                pass = pass.substring(0, pos) + ThreadLocalRandom.current().nextInt(97, 123).toChar() + pass.substring(pos)
                --min
            } else if (may > 0) {
                pass = pass.substring(0, pos) + ThreadLocalRandom.current().nextInt(65, 91).toChar() + pass.substring(pos)
                --may
            } else if (dig > 0) {
                pass = pass.substring(0, pos) + ThreadLocalRandom.current().nextInt(48, 58).toChar() + pass.substring(pos)
                --dig
            } else {
                pos = pos % 4
                if (pos == 0) ++str else if (pos == 1) ++min else if (pos == 2) ++may else ++dig
                --ran
                continue
            }
            ++len
            --tot
        }
        model["password"] = pass
        statistics(model, pass, len)
        return "showpass"
    }
}
