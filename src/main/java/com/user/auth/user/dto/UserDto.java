package com.user.auth.user.dto;

import com.user.auth.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserDto extends User implements Serializable {

    @ApiModelProperty(value = "token")
    private String token;


}
