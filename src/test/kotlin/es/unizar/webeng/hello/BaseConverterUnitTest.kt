package es.unizar.webeng.hello


import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner


/**
 * Test cases:
 * Convert a String value from decimal to another base |   expected: Error
 * Convert a String value from binary to another base  |   expected: Error
 *
 * Convert a no hexadecimal value to another base      |   expected: Error
 * Convert a no octal value to another base            |   expected: Error
 * Convert a hexadecimal number to another base        |   expected: Number in target base
 */
@RunWith(SpringRunner::class)
@WebMvcTest(BaseConverterController::class)
class BaseConverterUnitTest  {
    @Autowired
    private val controller: BaseConverterController? = null

    @Value("\${app.messageError:Error}")
    private val messageError: String? = null
    enum class Result {
        OK, NOK
    }

    @Test
    fun illegalStringHexToDecTest() {
        defaultTest("hex", "", "dec", "", Result.NOK)
    }

    @Test
    fun illegalStringDecToOctTest() {
        defaultTest("dec", "z", "oct", "", Result.NOK)
    }

    @Test
    fun illegalStringOctToBinTest() {
        defaultTest("oct", "z", "bin", "", Result.NOK)
    }

    @Test
    fun illegalStringBinToHexTest() {
        defaultTest("bin", "z", "hex", "", Result.NOK)
    }

    @Test
    fun HexToDecTest() {
        defaultTest("hex", "A1", "dec", "161", Result.OK)
    }

    @Test
    fun hexToOctTest() {
        defaultTest("hex", "A1", "oct", "241", Result.OK)
    }

    @Test
    fun hexToBinTest() {
        defaultTest("hex", "A1", "bin", "10100001", Result.OK)
    }

    @Test
    fun decToOctTest() {
        defaultTest("dec", "17", "oct", "21", Result.OK)
    }

    @Test
    fun decToBinTest() {
        defaultTest("dec", "17", "bin", "10001", Result.OK)
    }

    @Test
    fun decToHexTest() {
        defaultTest("dec", "17", "hex", "11", Result.OK)
    }

    @Test
    fun octToDecTest() {
        defaultTest("oct", "11", "dec", "9", Result.OK)
    }

    @Test
    fun octToBinTest() {
        defaultTest("oct", "11", "bin", "1001", Result.OK)
    }

    @Test
    fun octToHexTest() {
        defaultTest("oct", "11", "hex", "9", Result.OK)
    }

    @Test
    fun binToDecTest() {
        defaultTest("bin", "10", "dec", "2", Result.OK)
    }

    @Test
    fun binToOctTest() {
        defaultTest("bin", "10", "oct", "2", Result.OK)
    }

    @Test
    fun binToHexTest() {
        defaultTest("bin", "10", "hex", "2", Result.OK)
    }

    fun defaultTest(originalBase: String, originalNumber: String, targetBase: String, resultNumber: String, result: Result) {
        val model: MutableMap<String?, Any?> = HashMap()
        var message = messageError
        var values: String? = null
        controller!!.converter(originalBase, targetBase, originalNumber, model)
        if (result == Result.OK) {
            message = null
            values = "$originalNumber in base $originalBase to  $targetBase is $resultNumber"
        }
        Assert.assertEquals(values, model["values"] as String?)
        Assert.assertEquals(message, model["error_message"])
    }
}