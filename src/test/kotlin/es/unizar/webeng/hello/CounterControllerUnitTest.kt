package es.unizar.webeng.hello

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner


/**
 * Performs unit test to the CounterController
 *
 * The private methods are tested using the
 * public methods because they are called by them.
 *
 * Test cases:
 * -------------------------------------------
 * | counter | button           | result     |
 * -------------------------------------------
 * | 0      | "increase"        | 1         |
 * | 1      | "increase"        | 2         |
 * | 0      | "decrease"        | 0         |
 * | -1     | "decrease"        | 0         |
 * | 2      | "decrease"        | 1         |
 * | 10     | "init"            | 0         |
 * | "a"    | "init"            | "ERROR"   |
 * | 0      | "another_option"  | "ERROR"   |
 * ------------------------------------------
 *
 * @author Eduardo Ruiz Cordón (764539)
 * @version 1.0
 * @since 09/24/2020
 *
 */


@RunWith(SpringRunner::class)
@WebMvcTest(CounterController::class)
class CounterControllerUnitTest {
    @Autowired
    private val controller: CounterController? = null

    /**
     * Performs test of increase the counter with the value 0
     */
    @Test
    fun testIncreaseValue0() {
        var expectCounter = 0
        val map = HashMap<String?, Any?>()
        val view = controller!!.actionCounter(expectCounter.toString(), INCREASE, map)
        expectCounter++
        Assert.assertThat(view, CoreMatchers.`is`("counter"))
        Assertions.assertTrue(map.containsKey("counter"))
        Assertions.assertEquals(map["counter"], expectCounter)
    }

    /**
     * Performs test of increase the counter with the value greater than 0
     */
    @Test
    fun testIncreaseValueGreaterThan0() {
        var expectCounter = 1
        val map = HashMap<String?, Any?>()
        val view = controller!!.actionCounter(expectCounter.toString(), INCREASE, map)
        expectCounter++
        Assert.assertThat(view, CoreMatchers.`is`("counter"))
        Assertions.assertTrue(map.containsKey("counter"))
        Assertions.assertEquals(map["counter"], expectCounter)
    }

    /**
     * Performs test of decrease the counter with the value 0
     */
    @Test
    fun testDecreaseValue0() {
        val expectCounter = 0
        val map = HashMap<String?, Any?>()
        val view = controller!!.actionCounter(expectCounter.toString(), DECREASE, map)
        Assert.assertThat(view, CoreMatchers.`is`("counter"))
        Assertions.assertTrue(map.containsKey("counter"))
        Assertions.assertEquals(map["counter"], expectCounter)
    }

    /**
     * Performs test of decrease the counter with the value less than 0
     */
    @Test
    fun testDecreaseValueLessThan0() {
        var expectCounter = -1
        val map = HashMap<String?, Any?>()
        val view = controller!!.actionCounter(expectCounter.toString(), INCREASE, map)
        expectCounter = 0
        Assert.assertThat(view, CoreMatchers.`is`("counter"))
        Assertions.assertTrue(map.containsKey("counter"))
        Assertions.assertEquals(map["counter"], expectCounter)
    }

    /**
     * Performs test of decrease the counter with the value greater than 0
     */
    @Test
    fun testDecreaseValueGreaterThan0() {
        var expectCounter = 2
        val map = HashMap<String?, Any?>()
        val view = controller!!.actionCounter(expectCounter.toString(), DECREASE, map)
        expectCounter--
        Assert.assertThat(view, CoreMatchers.`is`("counter"))
        Assertions.assertTrue(map.containsKey("counter"))
        Assertions.assertEquals(map["counter"], expectCounter)
    }

    /**
     * Performs test of init the counter
     */
    @Test
    fun testInitCounter() {
        var expectCounter = 10
        val map = HashMap<String?, Any?>()
        val view = controller!!.actionCounter(expectCounter.toString(), INIT, map)
        expectCounter = INIT_VALUE
        Assert.assertThat(view, CoreMatchers.`is`("counter"))
        Assertions.assertTrue(map.containsKey("counter"))
        Assertions.assertEquals(map["counter"], expectCounter)
    }

    /**
     * Performs test of realize one action on the counter with the type date of counter isn't Integer
     */
    @Test
    fun testCounterNotInteger() {
        val map = HashMap<String?, Any?>()
        val view = controller!!.actionCounter("a", INIT, map)
        Assert.assertThat(view, CoreMatchers.`is`("counter"))
        Assertions.assertTrue(map.containsKey("counter"))
        Assertions.assertEquals(map["counter"], ERROR)
    }

    /**
     * Performs test of try realized a option not register in CounterController
     */
    @Test
    fun testTryActionNotRegister() {
        val map = HashMap<String?, Any?>()
        val view = controller!!.actionCounter(0.toString(), "another_option", map)
        Assert.assertThat(view, CoreMatchers.`is`("counter"))
        Assertions.assertTrue(map.containsKey("counter"))
        Assertions.assertEquals(map["counter"], ERROR)
    }

    companion object {
        private const val ERROR = "ERROR"
        private const val INCREASE = "increase"
        private const val DECREASE = "decrease"
        private const val INIT = "init"
        private const val INIT_VALUE = 0
    }
}