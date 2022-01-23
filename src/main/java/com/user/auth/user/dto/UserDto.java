package com.user.auth.user.dto;

import com.user.auth.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto extends User implements Serializable {

    @ApiModelProperty(value = "token")
    private String token;

    /**
     * menuId list
     */
    @ApiModelProperty(value = "菜单id列表")
    List<String> menuIdList;

    @ApiModelProperty(value = "菜单code列表")
    List<String> menuCodeList;

    @ApiModelProperty(value = "菜单列表")
    List<MenuDto> menuList;
}
