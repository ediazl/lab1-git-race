package es.unizar.webeng.hello

import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
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
 */
@RunWith(SpringRunner::class)
@WebMvcTest(CalculatorController::class)
class CalculatorStaticContentUnitTest {
    @Autowired
    private val wac: WebApplicationContext? = null
    private var mockMvc: MockMvc? = null

    /**
     * Setup Mockito test with web context
     */
    @Before
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    /**
     * Performs static content test for default
     * values (0)
     */
    @Test
    @Throws(Exception::class)
    fun testCalculator() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/ultra-calculator"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.model().attribute("values", CoreMatchers.`is`("0")))
                .andExpect(MockMvcResultMatchers.model().attribute("message", CoreMatchers.`is`("0")))
    }
}