package es.unizar.webeng.hello

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner


/**
 * Performs unit test to the PasswordController
 *
 * @author Alvaro Garcia Diaz (760704)
 *
 */


@RunWith(SpringRunner::class)
@WebMvcTest(PasswordController::class)
class PasswordControllerUnitTest {
    @Autowired
    private val controller: PasswordController? = null

    /**
     * Performs a test with an empty string
     */
    @Test
    fun testPasswordEmpty() {
        val map = HashMap<String, Any?>()
        val view = controller!!.passGen("", map)
        Assert.assertThat(view, CoreMatchers.`is`("pass"))
        Assert.assertThat(map.containsKey("password"), CoreMatchers.`is`(false))
    }

    /**
     * Performs a test with a valid string
     */
    @Test
    fun testPasswordCorrect() {
        val map = HashMap<String, Any?>()
        val view = controller!!.passGen("prove", map)
        Assert.assertThat(view, CoreMatchers.`is`("showpass"))
        Assert.assertThat(map.containsKey("password"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("len"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("upp"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("low"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("spe"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("num"), CoreMatchers.`is`(true))
        val aux = map["password"] as String?
        assert(aux!!.length > 0)
    }

    /**
     * Performs a test with strange characters
     */
    @Test
    fun testPasswordStrange() {
        val map = HashMap<String, Any?>()
        val view = controller!!.passGen("キターȓ̷͓͉͈̺̿e̷͖̔̂̀̚", map)
        Assert.assertThat(view, CoreMatchers.`is`("showpass"))
        Assert.assertThat(map.containsKey("password"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("len"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("upp"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("low"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("spe"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("num"), CoreMatchers.`is`(true))
        val aux = map["password"] as String?
        assert(aux!!.length > 0)
    }

    /**
     * Performs a test with negative numbers
     */
    @Test
    fun testModifyPasswordNegative() {
        val map = HashMap<String, Any?>()
        val view = controller!!.modifyPass("0", "-5", "4", "3", "0", "82466", map)
        Assert.assertThat(view, CoreMatchers.`is`("showpass"))
        Assert.assertThat(map.containsKey("password"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("len"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("upp"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("low"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("spe"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("num"), CoreMatchers.`is`(true))
        val aux = map["password"] as String?
        val len = aux!!.length
        var low = 4
        var upp = 3
        var spe = 0
        var num = 5
        assert(len > 0)
        for (i in 0 until len) {
            if (Character.isUpperCase(aux[i])) --upp else if (Character.isLowerCase(aux[i])) --low else if (Character.isDigit(aux[i])) --num else --spe
        }
        assert(low == 0)
        assert(upp == 0)
        assert(spe == 0)
        assert(num == 0)
    }

    /**
     * Performs a test with a valid case
     */
    @Test
    fun testModifyPasswordCorrect() {
        val map = HashMap<String, Any?>()
        val view = controller!!.modifyPass("0", "2", "2", "2", "2", "123456", map)
        Assert.assertThat(view, CoreMatchers.`is`("showpass"))
        Assert.assertThat(map.containsKey("password"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("len"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("upp"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("low"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("spe"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("num"), CoreMatchers.`is`(true))
        val aux = map["password"] as String?
        val len = aux!!.length
        var low = 2
        var upp = 2
        var spe = 2
        var num = 8
        assert(len > 0)
        for (i in 0 until len) {
            if (Character.isUpperCase(aux[i])) --upp else if (Character.isLowerCase(aux[i])) --low else if (Character.isDigit(aux[i])) --num else --spe
        }
        assert(low == 0)
        assert(upp == 0)
        assert(spe == 0)
        assert(num == 0)
    }

    /**
     * Performs a test adding random characters
     */
    @Test
    fun testModifyPasswordRandom() {
        val map = HashMap<String, Any?>()
        val view = controller!!.modifyPass("5", "0", "0", "0", "0", "123456", map)
        Assert.assertThat(view, CoreMatchers.`is`("showpass"))
        Assert.assertThat(map.containsKey("password"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("len"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("upp"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("low"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("spe"), CoreMatchers.`is`(true))
        Assert.assertThat(map.containsKey("num"), CoreMatchers.`is`(true))
        val aux = map["password"] as String?
        val len = aux!!.length
        var low = 0
        var upp = 0
        var spe = 0
        var num = 6
        assert(len > 0)
        for (i in 0 until len) {
            if (Character.isUpperCase(aux[i])) --upp else if (Character.isLowerCase(aux[i])) --low else if (Character.isDigit(aux[i])) --num else --spe
        }
        assert(low <= 0)
        assert(upp <= 0)
        assert(spe <= 0)
        assert(num <= 0)
    }
}
