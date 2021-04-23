package com.imadelfetouh.userservice.dal.configuration;

import com.imadelfetouh.userservice.model.response.ResponseModel;
import org.hibernate.Session;

public interface QueryExecuter<T> {

    ResponseModel<T> executeQuery(Session session);
}
