package com.imadelfetouh.userservice;

import com.imadelfetouh.userservice.dal.configuration.Executer;
import com.imadelfetouh.userservice.dal.configuration.SessionType;
import com.imadelfetouh.userservice.dal.db.RegisterDalDB;
import com.imadelfetouh.userservice.dal.queryexecuter.SetupDatabase;
import com.imadelfetouh.userservice.model.dto.RegisterDTO;
import com.imadelfetouh.userservice.model.dto.UserData;
import com.imadelfetouh.userservice.model.response.ResponseModel;
import com.imadelfetouh.userservice.model.response.ResponseType;
import com.imadelfetouh.userservice.rabbit.RabbitConfiguration;
import com.rabbitmq.client.Connection;
import org.junit.jupiter.api.*;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegisterTests {

    @BeforeAll
    static void setupDatabase() {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        executer.execute(new SetupDatabase());
    }

    @Test
    @Order(1)
    void addUserCorrect() {
        RegisterDalDB registerDalDB = new RegisterDalDB();
        RegisterDTO registerDTO = new RegisterDTO("imad", "imad", "imad", "imad.jpg", "bio", "Helmond", "imad.nl");

        ResponseModel<UserData> responseModel = registerDalDB.register(registerDTO);

        Assertions.assertEquals(ResponseType.CORRECT, responseModel.getResponseType());
    }

    @Test
    @Order(2)
    void loginWithNewUser() throws InterruptedException {
        Thread.sleep(2000);

        String url = "http://localhost:8081/auth/signin";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", "imad");
        map.add("password", "imad");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    @Order(3)
    void addUserInCorrect() {
        RegisterDalDB registerDalDB = new RegisterDalDB();
        RegisterDTO registerDTO = new RegisterDTO("imad", "imad", "imad", "imad.jpg", "bio", "Helmond", "imad.nl");

        ResponseModel<UserData> responseModel = registerDalDB.register(registerDTO);

        Assertions.assertEquals(ResponseType.USERNAMEALREADYINUSE, responseModel.getResponseType());
    }

    @Test
    @Order(4)
    void addUserWhileRabbitDown() throws IOException {
        Runtime.getRuntime().exec("docker stop rabbit");
        Runtime.getRuntime().exec("docker rm rabbit");
//        Connection connection = RabbitConfiguration.getInstance().getConnection();
//        connection.close();


    }

}
