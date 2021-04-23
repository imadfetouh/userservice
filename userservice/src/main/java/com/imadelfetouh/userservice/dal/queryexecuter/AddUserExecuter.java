package com.imadelfetouh.userservice.dal.queryexecuter;

import com.imadelfetouh.userservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.userservice.dal.ormmodel.User;
import com.imadelfetouh.userservice.model.dto.NewUserDTO;
import com.imadelfetouh.userservice.model.response.ResponseModel;
import com.imadelfetouh.userservice.model.response.ResponseType;
import org.hibernate.Session;

public class AddUserExecuter implements QueryExecuter<Void> {

    private NewUserDTO newUserDTO;

    public AddUserExecuter(NewUserDTO newUserDTO) {
        this.newUserDTO = newUserDTO;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        User user = new User(newUserDTO.getUserId(), newUserDTO.getUsername());

        session.persist(user);

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
