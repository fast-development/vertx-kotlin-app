package io.abner.kotlin.vertx

import kotlinx.coroutines.delay

class Service {

  suspend fun doSomething(delayMilliSeconds: Long): String {
    delay(delayMilliSeconds)
    return "something done after $delayMilliSeconds ms"
  }
}
