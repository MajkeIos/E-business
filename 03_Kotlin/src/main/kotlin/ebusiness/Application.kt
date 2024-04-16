package ebusiness

import ebusiness.plugins.*
import ebusiness.utils.DiscordBot
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    coroutineScope {
        launch {
            embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = false)
        }

        launch {
            DiscordBot(System.getenv("DISCORD_BOT_TOKEN")).start()
        }
    }
}

fun Application.module() {
    configureHTTP()
    configureRouting()
}
