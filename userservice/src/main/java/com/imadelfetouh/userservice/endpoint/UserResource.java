package com.imadelfetouh.userservice.endpoint;

import com.imadelfetouh.userservice.dalinterface.RegisterDal;
import com.imadelfetouh.userservice.model.dto.NewUserDTO;
import com.imadelfetouh.userservice.model.dto.RegisterDTO;
import com.imadelfetouh.userservice.model.response.ResponseModel;
import com.imadelfetouh.userservice.model.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody RegisterDTO registerDTO) {

        ResponseModel<Void> responseModel = registerDal.register(registerDTO);

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().build();
        }
        else if(responseModel.getResponseType().equals(ResponseType.USERNAMEALREADYINUSE)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(500).build();
    }
}
