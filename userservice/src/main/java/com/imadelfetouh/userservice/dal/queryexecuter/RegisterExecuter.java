package com.imadelfetouh.userservice.dal.queryexecuter;

import com.imadelfetouh.userservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.userservice.dal.ormmodel.User;
import com.imadelfetouh.userservice.model.response.ResponseModel;
import com.imadelfetouh.userservice.model.response.ResponseType;
import org.hibernate.Session;

public class RegisterExecuter implements QueryExecuter<Void> {

    private String userId;
    private String username;

    public RegisterExecuter(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        User user = new User(userId, username);
        session.persist(user);
        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
