package com.exam.devops.application.controller;

import com.exam.devops.application.entity.Device;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@ActiveProfiles(profiles = "test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceControllerTest {

    @LocalServerPort
    private int port = 0;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = "/devices";
    }

    @Test
    public void testCreateAndGet() {
        Device device = new Device();
        device.setModelName("GeigerCounter XTREME 2000");
        device.setSerialNumber("GCX12345");

        given().get().then().statusCode(200).body("size()", equalTo(0));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(device)
                .post()
                .then()
                .statusCode(201);

        given().get().then().statusCode(200).body("size()", equalTo(1));
    }
}
