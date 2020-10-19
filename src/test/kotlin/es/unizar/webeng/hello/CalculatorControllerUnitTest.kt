package es.unizar.webeng.hello

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

/**
 * Performs unit test to the CalculatorController
 *
 * Test cases:
 * ----------------------------
 * | value1 | value2 | result |
 * ----------------------------
 * | "1"    | "3"   | "4"     |
 * | -1     | "-3"  | "-4"    |
 * | "a"    | "b"   | "ERROR" |
 * | ""     | ""    | "ERROR" |
 * | NULL   | NULL  | "ERROR" |
 * ----------------------------
 *
 * @author Daniel Huici Meseguer (758635)
 * @version 1.0
 * @since 09/17/2020
 */
@RunWith(SpringRunner::class)
@WebMvcTest(CalculatorController::class)
class CalculatorControllerUnitTest {
    @Value("\${app.error}")
    private val error: String? = null

    @Autowired
    private val controller: CalculatorController? = null

    /**
     * Performs test with integer positive values
     */
    @Test
    @Throws(Exception::class)
    fun testCalculatorPositiveValues() {
        val value1 = "1"
        val value2 = "3"
        val result = value1.toInt() + value2.toInt()
        val map = HashMap<String?, Any?>()
        val view = controller!!.calculator(value1, value2, map)
        Assert.assertThat(view, CoreMatchers.`is`("calc"))
        Assert.assertThat(map.containsKey("values"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("message"), CoreMatchers.`is`(true))
        Assert.assertThat(map["values"], CoreMatchers.`is`("$value1 + $value2"))
        Assert.assertThat(map["message"], CoreMatchers.`is`(result.toString()))
    }

    /**
     * Performs test with integer negative values
     */
    @Test
    @Throws(Exception::class)
    fun testCalculatorNegativeValues() {
        val value1 = "-1"
        val value2 = "-3"
        val result = value1.toInt() + value2.toInt()
        val map = HashMap<String?, Any?>()
        val view = controller!!.calculator(value1, value2, map)
        Assert.assertThat(view, CoreMatchers.`is`("calc"))
        Assert.assertThat(map.containsKey("values"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("message"), CoreMatchers.`is`(true))
        Assert.assertThat(map["values"], CoreMatchers.`is`("$value1 + $value2"))
        Assert.assertThat(map["message"], CoreMatchers.`is`(result.toString()))
    }

    /**
     * Performs test with decimal values
     */
    @Test
    @Throws(Exception::class)
    fun testCalculatorDecimalValues() {
        val value1 = "1.4"
        val value2 = "3.1"
        val map = HashMap<String?, Any?>()
        val view = controller!!.calculator(value1, value2, map)
        Assert.assertThat(view, CoreMatchers.`is`("calc"))
        Assert.assertThat(map.containsKey("values"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("message"), CoreMatchers.`is`(true))
        Assert.assertThat(map["values"], CoreMatchers.`is`("$value1 + $value2"))
        Assert.assertThat(map["message"], CoreMatchers.`is`(error))
    }

    /**
     * Performs test with non numerical values
     */
    @Test
    @Throws(Exception::class)
    fun testCalculatorNonNumericalValues() {
        val value1 = "a"
        val value2 = "b"
        val map = HashMap<String?, Any?>()
        val view = controller!!.calculator(value1, value2, map)
        Assert.assertThat(view, CoreMatchers.`is`("calc"))
        Assert.assertThat(map.containsKey("values"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("message"), CoreMatchers.`is`(true))
        Assert.assertThat(map["values"], CoreMatchers.`is`("$value1 + $value2"))
        Assert.assertThat(map["message"], CoreMatchers.`is`(error))
    }

    /**
     * Performs test with null values
     */
    @Test
    @Throws(Exception::class)
    fun testCalculatorNullValues() {
        val value1: String? = null
        val value2: String? = null
        val map = HashMap<String?, Any?>()
        val view = controller!!.calculator(value1!!, value2!!, map)
        Assert.assertThat(view, CoreMatchers.`is`("calc"))
        Assert.assertThat(map.containsKey("values"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("message"), CoreMatchers.`is`(true))
        Assert.assertThat(map["values"], CoreMatchers.`is`("$value1 + $value2"))
        Assert.assertThat(map["message"], CoreMatchers.`is`(error))
    }

    /**
     * Performs test with empty values
     */
    @Test
    @Throws(Exception::class)
    fun testCalculatorEmptyValues() {
        val value1 = ""
        val value2 = ""
        val map = HashMap<String?, Any?>()
        val view = controller!!.calculator(value1, value2, map)
        Assert.assertThat(view, CoreMatchers.`is`("calc"))
        Assert.assertThat(map.containsKey("values"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("message"), CoreMatchers.`is`(true))
        Assert.assertThat(map["values"], CoreMatchers.`is`("$value1 + $value2"))
        Assert.assertThat(map["message"], CoreMatchers.`is`(error))
    }
}