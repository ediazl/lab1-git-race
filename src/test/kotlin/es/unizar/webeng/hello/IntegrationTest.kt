package es.unizar.webeng.hello

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner


/**
 * Performs integration test for webpage
 *
 */


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
class IntegrationTest() {
    @LocalServerPort
    private val port = 0

    /**
     * Performs integration test for main webpage
     *
     *
     * This function checks that the request gives an OK response and
     * if the response is correct(Contains the "<title>Hello" that is part of the template)
    </title> */
    @Test
    fun testHome() {
        val entity = TestRestTemplate().getForEntity(
                "http://localhost:" + port, String::class.java)
        Assert.assertEquals(HttpStatus.OK, entity.statusCode)
        Assert.assertTrue("Wrong body (title doesn't match):\n" + entity.body, entity
                .body.contains("<title>Hello"))
    }

    /**
     * Performs integration test for CSS
     *
     *
     * This function checks that the response status is Ok and
     * there is a body whose media type equals "text/css"
     */
    @Test
    fun testCss() {
        val entity = TestRestTemplate().getForEntity(
                "http://localhost:" + port
                        + "/webjars/bootstrap/3.3.5/css/bootstrap.min.css", String::class.java)
        Assert.assertEquals(HttpStatus.OK, entity.statusCode)
        Assert.assertTrue("Wrong body:\n" + entity.body, entity.body.contains("body"))
        Assert.assertEquals("Wrong content type:\n" + entity.headers.contentType,
                MediaType.valueOf("text/css"), entity.headers
                .contentType)
    }
}
