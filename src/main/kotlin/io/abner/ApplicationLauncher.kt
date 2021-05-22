package io.abner.kotlin.vertx

import io.vertx.core.VertxOptions
import io.vertx.core.Vertx
import io.vertx.core.Launcher
import io.vertx.micrometer.MicrometerMetricsOptions
import io.vertx.micrometer.VertxPrometheusOptions
import io.vertx.micrometer.MetricsDomain
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.json.JsonObject
import io.vertx.core.impl.launcher.VertxLifecycleHooks
import io.vertx.core.impl.launcher.VertxCommandLauncher
import io.vertx.core.DeploymentOptions
import io.vertx.tracing.opentelemetry.OpenTelemetryOptions

open class ApplicationLauncher :  VertxCommandLauncher(), VertxLifecycleHooks {
  init {
    println("______________________________ AQUI ___________________________________")
  }

  companion object {
    @JvmStatic
    fun main(vararg args: String) {
      println("-------------- AQUI Ó -----------------------------")
      ApplicationLauncher().dispatch(args);
    }
  }

  init {
    // REF: https://github.com/KisukeYang/Sugar-Judge/blob/6991e8e43854cf767dd4af7d83bf1d2282041ef6/judge-server/src/main/kotlin/com/sugar/judge/Launcher.kt#L45
     // -Dsun.net.inetaddr.ttl=0
     java.security.Security.setProperty("networkaddress.cache.ttl" , "0")
     //日志处理方式
     System.setProperty(
         "vertx.logger-delegate-factory-class-name",
         "io.vertx.core.logging.SLF4JLogDelegateFactory"
     )
     // log4j2.xml
     System.setProperty("log4j.configuration", "classpath*:log4j2.xml")
     // 禁用默认 DNS
     System.getProperties().setProperty("vertx.disableDnsResolver", "true")
  }

  var prometheusPort: Int = 9090
  var prometheusRoute: String = "/metrics"

    override fun afterConfigParsed(config: JsonObject) {
        prometheusPort = config.getInteger("prometheusPort", prometheusPort)
        prometheusRoute = config.getString("prometheusRoute", prometheusRoute)
    }

    // override fun afterStartingVertx() {

    // }

    override fun beforeDeployingVerticle(deploymentOptions: DeploymentOptions) {

    }

    override fun beforeStartingVertx(options: VertxOptions) {
      // TODO: setup export tracing to jaeger
      // https://github.com/Ultranium/vertx-4-0-3-jaeger-reproducer/blob/ee429a8273fb05ac57fe1bb9903f6f54aa737a22/src/main/java/com/example/vertx_4_0_3_jaeger/Application.java
      // https://github.com/fabienpomerol/vertx-opentelemetry/tree/0c4c5939724346452b2d77538ae83cabb0075aa1
        options.metricsOptions = MicrometerMetricsOptions()
                .setEnabled(true)
                // .addDisabledMetricsCategory(MetricsDomain.EVENT_BUS)
                // .addDisabledMetricsCategory(MetricsDomain.NET_CLIENT)
                // .addDisabledMetricsCategory(MetricsDomain.NET_SERVER)
                // .addDisabledMetricsCategory(MetricsDomain.HTTP_SERVER)
                // .addDisabledMetricsCategory(MetricsDomain.HTTP_CLIENT)
                .setPrometheusOptions(
                        VertxPrometheusOptions()
                                .setEnabled(true)
                                .setStartEmbeddedServer(true)
                                .setEmbeddedServerOptions(
                                        HttpServerOptions()
                                                .setPort(prometheusPort)
                                )
                                .setEmbeddedServerEndpoint(prometheusRoute)
                )
                println("BLA BLA BLA -----------------------")
    options.setTracingOptions(
      // could pass an opentelemetry instance configured with the exporter
      //  OpenTelemetryOptions(openTelemetry)
      // https://github.com/eclipse-vertx/vertx-tracing/blob/master/vertx-opentelemetry/src/main/java/io/vertx/tracing/opentelemetry/OpenTelemetryOptions.java
      OpenTelemetryOptions()
    )

    }

    override fun afterStartingVertx(vertx: Vertx) {}
    override fun afterStoppingVertx() {
      System.exit(1)
    }
    override fun beforeStoppingVertx(vertx: Vertx) {}
    override fun handleDeployFailed(vertx: Vertx, message: String, deploymentOptions: DeploymentOptions, ex: Throwable) {
      vertx.close()
    }

}
