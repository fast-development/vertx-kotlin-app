package io.abner.kotlin.vertx

import io.vertx.core.Promise
import io.vertx.core.http.HttpServerRequest
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import kotlinx.coroutines.launch

class MainVerticle : CoroutineVerticle() {

  override suspend fun start() {
    try {
      val httpServer = vertx
        .createHttpServer()
        .requestHandler { req -> handleRequest(req) }
        .listen(8888).await()

      println("Server started at port ${httpServer.actualPort()}")
    } catch (e: Exception) {
      println("Failed to start the Server at port ${8888}: $e")
      throw e
    }

  }

  private fun handleRequest(req: HttpServerRequest) {
    val service = Service()
    //req.response().end("OK")
    launch {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x! - ${service.doSomething(1200)}")
    }
  }
}
