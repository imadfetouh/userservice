package com.imadelfetouh.userservice.dal.db;

import com.imadelfetouh.userservice.dal.configuration.Executer;
import com.imadelfetouh.userservice.dal.configuration.SessionType;
import com.imadelfetouh.userservice.dal.queryexecuter.CheckUsernameExecuter;
import com.imadelfetouh.userservice.dal.queryexecuter.RegisterExecuter;
import com.imadelfetouh.userservice.dalinterface.RegisterDal;
import com.imadelfetouh.userservice.model.dto.NewUserDTO;
import com.imadelfetouh.userservice.model.dto.ProfileDTO;
import com.imadelfetouh.userservice.model.dto.RegisterDTO;
import com.imadelfetouh.userservice.model.response.ResponseModel;
import com.imadelfetouh.userservice.model.response.ResponseType;
import com.imadelfetouh.userservice.rabbit.RabbitProducer;
import com.imadelfetouh.userservice.rabbit.producer.AddUserProducer;
import com.imadelfetouh.userservice.security.PasswordHash;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RegisterDalDB implements RegisterDal {

    @Override
    public ResponseModel<Void> register(RegisterDTO registerDTO) {
        Executer<Void> executer = new Executer<>(SessionType.READ);
        ResponseModel<Void> responseModel = executer.execute(new CheckUsernameExecuter(registerDTO.getUsername()));

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            Executer<Void> executerRegister = new Executer<>(SessionType.WRITE);
            String userId = UUID.randomUUID().toString();
            responseModel = executerRegister.execute(new RegisterExecuter(userId, registerDTO.getUsername()));

            if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
                String profileId = UUID.randomUUID().toString();
                String password = PasswordHash.getInstance().hash(registerDTO.getPassword());
                NewUserDTO newUserDTO = new NewUserDTO(userId, registerDTO.getUsername(), password, "USER", registerDTO.getPhoto(), new ProfileDTO(profileId, registerDTO.getBio(), registerDTO.getLocation(), registerDTO.getWebsite()));

                RabbitProducer rabbitProducer = new RabbitProducer();
                rabbitProducer.produce(new AddUserProducer(newUserDTO));
            }
        }

        return responseModel;
    }
}
