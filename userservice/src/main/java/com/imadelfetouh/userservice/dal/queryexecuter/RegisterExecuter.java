package com.imadelfetouh.userservice.dal.queryexecuter;

import com.imadelfetouh.userservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.userservice.dal.ormmodel.User;
import com.imadelfetouh.userservice.model.dto.UserData;
import com.imadelfetouh.userservice.model.response.ResponseModel;
import com.imadelfetouh.userservice.model.response.ResponseType;
import org.hibernate.Session;

public class RegisterExecuter implements QueryExecuter<UserData> {

    private String userId;
    private String username;

    public RegisterExecuter(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Override
    public ResponseModel<UserData> executeQuery(Session session) {
        ResponseModel<UserData> responseModel = new ResponseModel<>();

        User user = new User(userId, username);
        session.persist(user);

        UserData userData = new UserData(userId, username, "USER");
        responseModel.setData(userData);

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
