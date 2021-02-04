package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
import scala.concurrent.duration._


class {{cookiecutter.SERVICE_NAME}}{{cookiecutter.RESOURCE_NAME}}ApiSimulation extends Simulation {

  val httpProtocol = http
    .warmUp("http://localhost:8080/health")
    .baseUrl("http://localhost:8080") // Here is the root for all relative URLs
    .acceptHeader("application/json") // Here are the common headers

  val scn = scenario("API Endpoint Simulation")
    .exec(http("Create {{cookiecutter.RESOURCE_NAME}}")
      .post("/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}")
      .body(StringBody("""{"userName":"mary.q.contrary", "pii": "123-45-6789", "firstName": "Mary", "lastName":"Contrary"}"""))
      .asJson
      .check(jsonPath("$.id").saveAs("userId")))
    .exec(http("Get {{cookiecutter.RESOURCE_NAME}}")
      .get("/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}/${userId}")) // 92534752-a39c-499c-aa13-528cd0143f7c
    .exec(http("Create {{cookiecutter.SUB_RESOURCE_NAME}}")
      .post("/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}/${userId}/{{cookiecutter.SUB_RESOURCE_URL}}")
      .body(StringBody("""{"userName":"mary.q.contrary", "firstName": "Mary", "lastName":"Contrary"}"""))
      .asJson
      .check(jsonPath("$.id").saveAs("subUserId")))
    .exec(http("Get {{cookiecutter.SUB_RESOURCE_NAME}}")
      .get("/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}/${userId}/{{cookiecutter.SUB_RESOURCE_URL}}/${subUserId}")) // 92534752-a39c-499c-aa13-528cd0143f7c

  setUp(scn
    .inject(
      atOnceUsers(10),
      rampUsersPerSec(10) to 100 during (2.minutes))
    .protocols(httpProtocol))
}
