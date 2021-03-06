package com.imadelfetouh.userservice.dalinterface;

import com.imadelfetouh.userservice.model.dto.RegisterDTO;
import com.imadelfetouh.userservice.model.dto.UserData;
import com.imadelfetouh.userservice.model.response.ResponseModel;

public interface RegisterDal {

    ResponseModel<UserData> register(RegisterDTO registerDTO);
}
