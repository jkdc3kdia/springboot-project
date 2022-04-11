package com.dego.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单列表
 */
@Data
@ApiModel("菜单列表")
public class MenuListVO {

    private Long id;

    @ApiModelProperty("菜单编码")
    private String code;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("跳转地址")
    private String url;

    @ApiModelProperty("子菜单列表")
    private List<MenuListVO> children;

}
