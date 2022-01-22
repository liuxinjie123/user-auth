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
 * @create 2022-01-22 14:30:39
 * @describe 菜单表实体类
 */
@Data
@NoArgsConstructor
@TableName("menu")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Menu对象", description = "菜单表")
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "菜单编码")
    @TableField("menu_code")
    private String menuCode;

    @ApiModelProperty(value = "父节点")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "节点类型，1目录，2页面，3按钮")
    @TableField("node_type")
    private Integer nodeType;

    @ApiModelProperty(value = "图标地址")
    @TableField("icon_url")
    private String iconUrl;

    @ApiModelProperty(value = "排序号")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "页面对应的地址")
    @TableField("link_url")
    private String linkUrl;

    @ApiModelProperty(value = "层次")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "树id的路径 整个层次上的路径id，逗号分隔，想要找父节点特别快")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "是否删除 1：已删除；0：未删除")
    @TableField("del")
    private Integer del;


}