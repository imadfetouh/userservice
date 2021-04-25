package com.imadelfetouh.userservice.endpoint;

import com.imadelfetouh.userservice.dalinterface.RegisterDal;
import com.imadelfetouh.userservice.model.dto.RegisterDTO;
import com.imadelfetouh.userservice.model.dto.UserData;
import com.imadelfetouh.userservice.model.response.ResponseModel;
import com.imadelfetouh.userservice.model.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserResource {

    @Autowired
    private RegisterDal registerDal;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserData> register(@RequestBody RegisterDTO registerDTO) {
        if(registerDTO.getUsername().trim().equals("") || registerDTO.getPassword().trim().equals("") || registerDTO.getRepeatPassword().trim().equals("") || registerDTO.getPhoto().trim().equals("") || registerDTO.getBio().equals("") || registerDTO.getLocation().trim().equals("") || registerDTO.getWebsite().trim().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else if(!registerDTO.getPassword().equals(registerDTO.getRepeatPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ResponseModel<UserData> responseModel = registerDal.register(registerDTO);

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().body(responseModel.getData());
        }
        else if(responseModel.getResponseType().equals(ResponseType.USERNAMEALREADYINUSE)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(500).build();
    }
}
