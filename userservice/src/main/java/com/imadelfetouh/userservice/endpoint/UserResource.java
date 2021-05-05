package com.imadelfetouh.userservice.endpoint;

import com.google.gson.Gson;
import com.imadelfetouh.userservice.dalinterface.RegisterDal;
import com.imadelfetouh.userservice.jwt.CreateJWTToken;
import com.imadelfetouh.userservice.model.dto.RegisterDTO;
import com.imadelfetouh.userservice.model.dto.UserData;
import com.imadelfetouh.userservice.model.response.ResponseModel;
import com.imadelfetouh.userservice.model.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("user")
public class UserResource {

    private static final Logger logger = Logger.getLogger(UserResource.class.getName());

    @Autowired
    private RegisterDal registerDal;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserData> register(@RequestParam("user") String user, @RequestParam("photo") MultipartFile multipartFile, HttpServletResponse response) {
        Gson gson = new Gson();

        RegisterDTO registerDTO = gson.fromJson(user, RegisterDTO.class);

        if(registerDTO.getUsername().trim().equals("") || registerDTO.getPassword().trim().equals("") || registerDTO.getRepeatPassword().trim().equals("") || registerDTO.getPhoto().trim().equals("") || registerDTO.getBio().equals("") || registerDTO.getLocation().trim().equals("") || registerDTO.getWebsite().trim().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else if(!registerDTO.getPassword().equals(registerDTO.getRepeatPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ResponseModel<UserData> responseModel = registerDal.register(registerDTO);

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            uploadPhoto(multipartFile);

            Map<String, String> claims = new HashMap<>();
            claims.put("userdata", gson.toJson(responseModel.getData()));
            String token = CreateJWTToken.getInstance().create(claims);

            Cookie cookie = new Cookie("jwt-token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setComment("");
            cookie.setPath("/");
            cookie.setDomain("20.80.120.180");

            response.addCookie(cookie);

            return ResponseEntity.ok().body(responseModel.getData());
        }
        else if(responseModel.getResponseType().equals(ResponseType.USERNAMEALREADYINUSE)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(500).build();
    }

    private void uploadPhoto(MultipartFile multipartFile) {
        try {
            String folder = "D:/imageskwetter/";
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(folder + multipartFile.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
}
