package io.twdps.starter.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Starter Kit service",
            description = "Starter Kit service providing dummy Account info",
            contact =
                @Contact(
                    name = "starter",
                    url = "https://starter.twdps.io/",
                    email = "FIXME@twdps.io"),
            license =
                @License(
                    name = "MIT Licence",
                    url =
                        "https://github.com/thoughtworks-dps/dps-multi-module-starterkit-javablob/master/LICENSE")),
    servers = @Server(url = "http://localhost:8080/"))
@SecurityScheme(
    name = "oauth2",
    scheme = "OAuth2",
    type = SecuritySchemeType.OAUTH2,
    in = SecuritySchemeIn.HEADER)
@Configuration
public class OpenApiConfiguration {}
