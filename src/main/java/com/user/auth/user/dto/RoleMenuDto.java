package com.user.auth.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 角色，菜单绑定关系， 新增、编辑时使用
 */
@Data
@NoArgsConstructor
public class RoleMenuDto implements Serializable {
    @NotBlank(message = "角色id 不能为空")
    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "菜单id list")
    private List<String> menuIdList;

}
