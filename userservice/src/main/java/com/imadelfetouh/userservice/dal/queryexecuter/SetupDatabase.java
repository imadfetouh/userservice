package com.imadelfetouh.userservice.dal.queryexecuter;

import com.imadelfetouh.userservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.userservice.model.response.ResponseModel;
import org.hibernate.Session;

public class SetupDatabase implements QueryExecuter<Void> {

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        return new ResponseModel<>();
    }
}
