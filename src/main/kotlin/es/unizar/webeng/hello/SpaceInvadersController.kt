package es.unizar.webeng.hello

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


/**
 * The HelloControler class contains the handler functions for the different requests.
 *
 *
 * The program implements an application that
 * simply displays current time and a static welcome message
 * when requested.
 *
 */
@Controller
class SpaceInvadersController {
    @GetMapping("/space_invaders")
    fun spaceInvaders(): String {
        return "space_invaders"
    }
}