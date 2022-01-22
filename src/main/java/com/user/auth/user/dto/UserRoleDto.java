package com.user.auth.user.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Api(value = "用户、角色绑定关系，新增、编辑时使用")
@Data
@NoArgsConstructor
public class UserRoleDto implements Serializable {
    @NotBlank(message = "用户id 不能胃口")
    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "角色id list")
    List<String> roleIdList;

}
