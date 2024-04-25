package com.neverless.sendingservice.REST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

  @Value("${openapi.dev-url}")
  private String devUrl;

  @Value("${openapi.prod-url}")
  private String prodUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Server URL in Production environment");

    Contact contact = new Contact();
    contact.setEmail("sanjeev.sarda@gmail.com");
    contact.setName("Sanjeev");
    contact.setUrl("https://www.neverless.com");

    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("Money sending API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to withdraw monies and transfer between accounts.").termsOfService("https://www.neverless.com/terms")
        .license(mitLicense);

    OpenAPI res = new OpenAPI();
    res.info(info);
    ArrayList<Server> srvs=new ArrayList<Server>();
    res.servers(srvs);
    return res;
  }
}
