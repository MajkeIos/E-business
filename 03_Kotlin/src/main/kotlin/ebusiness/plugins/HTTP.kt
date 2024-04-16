package ebusiness.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.defaultheaders.*

fun Application.configureHTTP() {
    install(DefaultHeaders) {
        header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}
