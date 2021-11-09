{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" -%}
package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class {{cookiecutter.SERVICE_NAME}}{{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}ApiSimulation extends Simulation {

  val httpProtocol = http
    .warmUp("http://localhost:8081/actuator/health")
    .baseUrl("http://localhost:8080") // Here is the root for all relative URLs
    .acceptHeader("application/json") // Here are the common headers

  val scn = scenario("API Endpoint Simulation")
    .exec(http("Create {{cookiecutter.RESOURCE_NAME}}")
      .post("/v1/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}")
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
      .body(StringBody("""{"userName":"mary.q.contrary", "pii": "987-65-4321", "firstName": "Mary", "lastName":"Contrary", "{{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id": "uuid-parent"}"""))
{%- else %}
      .body(StringBody("""{"userName":"mary.q.contrary", "pii": "987-65-4321", "firstName": "Mary", "lastName":"Contrary"}"""))
{%- endif %}
      .asJson
      .check(jsonPath("$.id").saveAs("userId")))
    .exec(http("Get {{cookiecutter.RESOURCE_NAME}}")
      .get("/v1/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}/${userId}")) // 92534752-a39c-499c-aa13-528cd0143f7c
    .exec(http("Create {{cookiecutter.RESOURCE_NAME}} {{cookiecutter.SUB_RESOURCE_NAME}}")
      .post("/v1/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}/${userId}/{{cookiecutter.SUB_RESOURCE_URL}}")
      .body(StringBody("""{"userName":"jack.sprat", "firstName": "Jack", "lastName":"Sprat", "{{cookiecutter.RESOURCE_VAR_NAME}}Id": "uuid-parent"}"""))
      .asJson
      .check(jsonPath("$.id").saveAs("subUserId")))
    .exec(http("Get {{cookiecutter.RESOURCE_NAME}} {{cookiecutter.SUB_RESOURCE_NAME}}")
      .get("/v1/{{cookiecutter.SERVICE_URL}}/{{cookiecutter.RESOURCE_URL}}/${userId}/{{cookiecutter.SUB_RESOURCE_URL}}/${subUserId}")) // 92534752-a39c-499c-aa13-528cd0143f7c

  setUp(scn
    .inject(
      atOnceUsers(10),
      rampUsersPerSec(10) to 100 during (2.minutes))
    .protocols(httpProtocol))
}
{%- endif %}
