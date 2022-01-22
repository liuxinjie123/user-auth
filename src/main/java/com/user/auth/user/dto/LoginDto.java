package com.user.auth.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class LoginDto implements Serializable {
    @NotBlank(message = "手机号不能为口")
    @ApiModelProperty(value = "手机号，用手机号登录")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

}
