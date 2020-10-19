package es.unizar.webeng.hello


import org.springframework.boot.WebApplicationType

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
open class Application : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(Application::class.java)
    }
}

fun main(args: Array<String>) {
    // Sets the WebApplicationType to REACTIVE to enable reactive functionalities to Thymeleaf
    SpringApplicationBuilder(Application::class.java).web(WebApplicationType.REACTIVE).run(*args)
}
