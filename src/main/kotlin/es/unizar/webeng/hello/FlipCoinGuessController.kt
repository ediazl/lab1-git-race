package es.unizar.webeng.hello

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.util.*


/**
 * FlipCoinGuess program implements an simple game that
 * allow the user to guess the result of flipping
 * a coin.
 *
 * @author Jose Ignacio Hernandez (740491)
 * @version 1.0
 * @since 09/25/2020
 */
@Controller
class FlipCoinGuessController {
    @GetMapping("/flipCoinGuess-init")
    fun initFlipCoin(model: MutableMap<String?, Any?>): String {
        model["result"] = DEFAULT_RESULT
        model["message"] = DEFAULT_MESSAGE
        return "flipCoinGuess"
    }

    /**
     * Method which is called by a POST petition and implements the logic
     * of the game.
     * @param guess user choice of guessing the result of tossing a coin
     * @param model the Map where the actual result and message are stored
     * @return JSP to the page flipCoinGuess.html
     */
    @RequestMapping(value = ["/flipAction"], method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun flipAndGuess(@RequestParam("guess") guess: String,
                     model: MutableMap<String?, Any?>): String {
        try {
            val userGuess = guess.toInt() // HEADS = 0 , TAILS = 1
            val actualResult = toss()
            if (actualResult == 0) {
                model["result"] = HEADS_RESULT
            } else if (actualResult == 1) {
                model["result"] = TAILS_RESULT
            }
            if (guessResult(userGuess, actualResult)) {
                model["message"] = WINNING_MESSAGE
            } else {
                model["message"] = LOSING_MESSAGE
            }
        } catch (e: NumberFormatException) {
            model["guess"] = ERROR
        }
        return "flipCoinGuess"
    }

    private fun toss(): Int {
        val random = Random()
        return random.nextInt(2)
    }

    private fun guessResult(guess: Int, sideUp: Int): Boolean {
        return guess == sideUp
    }

    companion object {
        private const val DEFAULT_RESULT = "NOT RESULT YET | "
        private const val DEFAULT_MESSAGE = "WAITING FOR RESULT"
        private const val HEADS_RESULT = "HEADS | "
        private const val TAILS_RESULT = "TAILS | "
        private const val WINNING_MESSAGE = "YOU GUESSED IT RIGHT!"
        private const val LOSING_MESSAGE = "SORRY BETTER LUCK NEXT TIME!"
        private const val ERROR = "ERROR"
    }
}
