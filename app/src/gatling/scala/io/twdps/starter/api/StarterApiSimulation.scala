package io.twdps.starter.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
import scala.concurrent.duration._


class StarterApiSimulation extends Simulation {

  val httpProtocol = http
    .warmUp("http://localhost:8080/health")
    .baseUrl("http://localhost:8080") // Here is the root for all relative URLs
    .acceptHeader("application/json") // Here are the common headers

  val scn = scenario("API Endpoint Simulation")
    .exec(http("Create apple")
      .post("/starter")
      .body(StringBody("""{"userName":"mary.q.contrary", "firstName": "Mary", "lastName":"Contrary"}"""))
      .asJson)
    .exec(http("Get account")
      .get("/starter/1"))

  setUp(scn
    .inject(
      atOnceUsers(10),
      rampUsersPerSec(10) to 100 during (2.minutes))
    .protocols(httpProtocol))
}
