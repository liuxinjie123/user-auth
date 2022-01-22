package com.user.auth.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther jack
 * @create 2022-01-22 14:29:50
 * @describe 角色表实体类
 */
@Data
@NoArgsConstructor
@TableName("role")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Role对象", description = "角色表")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "是否删除 1：已删除；0：未删除")
    @TableField("del")
    private Integer del;

}