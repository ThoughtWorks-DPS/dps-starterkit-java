package io.twdps.starter.example.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
import scala.concurrent.duration._


class ExampleAccountApiSimulation extends Simulation {

  val httpProtocol = http
    .warmUp("http://localhost:8081/actuator/health")
    .baseUrl("http://localhost:8080") // Here is the root for all relative URLs
    .acceptHeader("application/json") // Here are the common headers

  val scn = scenario("API Endpoint Simulation")
    .exec(http("Create Account")
      .post("/v1/example/accounts")
      .body(StringBody("""{"userName":"mary.q.contrary", "pii": "987-65-4321", "firstName": "Mary", "lastName":"Contrary"}"""))
      .asJson
      .check(jsonPath("$.id").saveAs("userId")))
    .exec(http("Get Account")
      .get("/v1/example/accounts/${userId}")) // 92534752-a39c-499c-aa13-528cd0143f7c
    .exec(http("Create SubAccount")
      .post("/v1/example/accounts/${userId}/subaccounts")
      .body(StringBody("""{"userName":"jack.sprat", "firstName": "Jack", "lastName":"Sprat"}"""))
      .asJson
      .check(jsonPath("$.id").saveAs("subUserId")))
    .exec(http("Get SubAccount")
      .get("/v1/example/accounts/${userId}/subaccounts/${subUserId}")) // 92534752-a39c-499c-aa13-528cd0143f7c

  setUp(scn
    .inject(
      atOnceUsers(10),
      rampUsersPerSec(10) to 100 during (2.minutes))
    .protocols(httpProtocol))
}
