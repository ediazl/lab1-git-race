package es.unizar.webeng.hello

import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * Performs static content unit test to the
 * Calculator JSP template
 *
 * @author Daniel Huici Meseguer (758635)
 * @version 1.0
 * @since 09/17/2020
 *
 */


@RunWith(SpringRunner::class)
@WebMvcTest(HelloController::class)
class StaticContentUnitTest {
    @Autowired
    private val wac: WebApplicationContext? = null

    @Value("\${app.message:Press F5 to roll the dice}")
    private val message: String? = null
    private var mockMvc: MockMvc? = null
    @Before
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    @Test
    @Throws(Exception::class)
    fun testMessage() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.model().attribute("message", CoreMatchers.`is`(message)))
    }
}
