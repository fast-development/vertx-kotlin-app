
Criar

```bash
curl -G https://start.vertx.io/starter.zip -d "groupId=io.abner.kotlin.vertx" -d "artifactId=vertxapp" -d "packageName=io.abner.kotlin.vertx" -d "vertxVersion=4.0.3" -d "vertxDependencies=vertx-web,vertx-web-client,vertx-web-validation,vertx-pg-client,vertx-redis-client,vertx-lang-kotlin-coroutines,vertx-circuit-breaker,vertx-config,vertx-auth-jwt,vertx-micrometer-metrics,vertx-health-check,vertx-junit5,vertx-shell,vertx-json-schema" -d "language=kotlin" -d "jdkVersion=11" -d "buildTool=gradle" --output vertxapp.zip && \
unzip vertxapp.zip && \
rm vertxapp.zip
```



# References

* Custom Launcher for Kotlin: https://github.com/vert-x3/vertx-lang-kotlin/issues/80#issuecomment-480434460

https://github.com/lfmunoz/vertx-kt-rocket/blob/eabdaaaf5aacb0a6967fa24a4fb64171fd981762/src/main/kotlin/com/lfmunoz/App.kt

https://github.com/lfmunoz/vertx-kt-rocket

- Launcher dnsResolver disable: https://github.com/KisukeYang/Sugar-Judge/blob/6991e8e43854cf767dd4af7d83bf1d2282041ef6/judge-server/src/main/kotlin/com/sugar/judge/Launcher.kt#L45

- https://github.com/lfmunoz/vertx-kt-rocket Simple TCP Client / Server to test millions of concurrent persistent connections across servers.

- Pg Result Transformer: https://github.com/KisukeYang/Sugar-Judge/blob/6991e8e43854cf767dd4af7d83bf1d2282041ef6/judge-server/src/main/kotlin/com/sugar/judge/utils/PgResultTransformer.kt


## Tracing Jaeger

https://github.com/Ultranium/vertx-4-0-3-jaeger-reproducer/blob/ee429a8273fb05ac57fe1bb9903f6f54aa737a22/src/main/java/com/example/vertx_4_0_3_jaeger/Application.java

# Krakend

https://github.com/devopsfaith/krakendesigner/issues/94
https://www.krakend.io/docs/endpoints/json-schema/


# GraphQL

* Group Expedia shared this library: https://github.com/ExpediaGroup/graphql-kotlin

# kotlin + Coroutines

https://vertx.io/docs/vertx-lang-kotlin-coroutines/kotlin/


# Kotlin DSL Tutorial

https://napperley.medium.com/gradle-kotlin-dsl-tutorial-223370af9cd8

https://medium.com/@gabrielshanahan/using-gradle-w-kotlin-dsl-to-create-a-kotlin-project-25eea39c27f8


# Generator

KotlinPoet


#  Graal VM +  kotlin
https://github.com/graalvm/graalvm-demos/blob/master/java-kotlin-aot/build.sh
