package es.unizar.webeng.hello

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test


/**
 * FlipCoinGuess test program implements all the
 * possible unit test cases to check the right execution
 * of FlipCoinGuessController
 *
 * @author Jose Ignacio Hernandez (740491)
 * @version 1.0
 * @since 09/25/2020
 */
//@WebMvcTest(FlipCoinGuessController.class)
class FlipCoinGuessControllerTest {
    //@Autowired
    private val flipController = FlipCoinGuessController()

    /**
     * Checks that the default values of the controller are the ones expected
     */
    @Test
    fun initFlipCoin() {
        val model = HashMap<String?, Any?>()
        val view = flipController.initFlipCoin(model)
        Assert.assertThat(view, CoreMatchers.`is`("flipCoinGuess"))
        Assert.assertTrue(model.containsKey("result"))
        Assert.assertTrue(model.containsKey("message"))
        Assert.assertThat(model["result"], CoreMatchers.`is`(DEFAULT_RESULT))
        Assert.assertThat(model["message"], CoreMatchers.`is`(DEFAULT_MESSAGE))
    }

    /**
     * POSSIBLE TEST CASES:
     * User guesses: HEADS , actual result: HEADS
     * User guesses: HEADS , actual result: TAILS
     * User guesses: TAILS , actual result: HEADS
     * User guesses: TAILS , actual result: TAILS
     *
     * Checks if the messages are the winning or losing expected
     *
     */
    @Test
    fun flipAndGuessHEADS() {
        val candidate = "0" // HEADS = 0 , TAILS = 1
        val model = HashMap<String?, Any?>()
        flipController.flipAndGuess(candidate, model)
        if (model["result"] == HEADS_RESULT) {
            Assert.assertEquals(model["message"], WINNING_MESSAGE)
        } else {
            Assert.assertEquals(model["message"], LOSING_MESSAGE)
        }
    }

    @Test
    fun flipAndGuessTAILS() {
        val candidate = "1" // HEADS = 0 , TAILS = 1
        val model = HashMap<String?, Any?>()
        flipController.flipAndGuess(candidate, model)
        if (model["result"] == TAILS_RESULT) {
            Assert.assertEquals(model["message"], WINNING_MESSAGE)
        } else {
            Assert.assertEquals(model["message"], LOSING_MESSAGE)
        }
    }

    companion object {
        private const val DEFAULT_RESULT = "NOT RESULT YET | "
        private const val DEFAULT_MESSAGE = "WAITING FOR RESULT"
        private const val WINNING_MESSAGE = "YOU GUESSED IT RIGHT!"
        private const val LOSING_MESSAGE = "SORRY BETTER LUCK NEXT TIME!"
        private const val HEADS_RESULT = "HEADS | "
        private const val TAILS_RESULT = "TAILS | "
        private const val ERROR = "ERROR"
    }
}
