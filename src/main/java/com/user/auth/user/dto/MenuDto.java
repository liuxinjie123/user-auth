package com.user.auth.user.dto;

import com.user.auth.user.entity.Menu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class MenuDto extends Menu implements Serializable {
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 子列表
     */
    private List<MenuDto> menuList;

}
