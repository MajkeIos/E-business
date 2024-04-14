package ebusiness.plugins

import ebusiness.routings.configureDiscordRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Routing) {
        get("/") {
            call.respondText("Hello World!")
        }
        configureDiscordRoutes()
    }
}
