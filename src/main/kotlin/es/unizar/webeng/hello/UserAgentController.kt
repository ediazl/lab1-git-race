package es.unizar.webeng.hello

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import java.util.*


/**
 * The UserAgentController program implements an application that
 * simply returns current time and the user's browser name with its
 * version
 *
 * @author Daniel Huici Meseguer (758635)
 * @version 1.0
 * @since 09/17/2020
 *
 */


@Controller
class UserAgentController {
    /**
     * This method is called when requested by GET petition to
     * the user-agent webpage (/user-agent). It simply puts
     * current time and user browser to a Map so iit can be read
     * by view (JSP).
     *
     * @param model This is the map where we put the data
     * @return JSP name view.
     */
    @GetMapping("/user-agent")
    fun welcome(@RequestHeader(value = "User-Agent") userAgent: String?, model: MutableMap<String?, Any?>): String {
        model["time"] = Date()
        model["message"] = userAgent
        return "wellcome"
    }
}
