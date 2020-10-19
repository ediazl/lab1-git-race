package es.unizar.webeng.hello

import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner


/**
 * Performs unit test to the Wellcome JSP template
 *
 */
@RunWith(SpringRunner::class)
@WebMvcTest(HelloController::class)
class HelloControllerUnitTest {
    @Value("\${app.message:Press F5 to roll the dice}")
    private val message: String? = null

    @Autowired
    private val controller: HelloController? = null
    /**
     * Testing doesn't make much sense in this web service, but we'll do a dumb test
     */
    /**
     * Performs static unit content test to the
     * main JSP template wellcome (message)
     *
     *
     * This function tests whether the welcome function sets the message info and the message is correct
     */
    @Test
    @Throws(Exception::class)
    fun testMessage() {
        val map = HashMap<String?, Any?>()
        controller!!.rollTheDice(map)
        Assert.assertTrue(map.containsKey("dice"))
        Assert.assertTrue(map.containsKey("message"))
        Assert.assertTrue(map.containsKey("host"))
        Assert.assertTrue(map.containsKey("ip"))
        Assert.assertTrue(map.containsKey("java"))
        Assert.assertEquals(map["message"], message)
    }

    /**
     * Test for the message returned when calling the welcomeName function.
     *
     *
     * This function tests whether the welcomeName function sets the message info
     * and the message corresponds with the name sent to the function
     */
    @Test
    @Throws(Exception::class)
    fun testName() {
        val name = "user"
        val map = HashMap<String?, Any?>()
        val view = controller!!.welcomeName(map, name)
        Assert.assertThat(view, Is.`is`("wellcome"))
        Assert.assertThat(map.containsKey("message"), Is.`is`(true))
        Assert.assertThat(map["message"], Is.`is`("Hola $name"))
    }
}