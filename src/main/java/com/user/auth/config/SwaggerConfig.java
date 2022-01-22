package com.user.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 注解	使用的地方	用途
 * @Api 类/接口	描述类/接口主要用途
 * @ApiOperation 方法    描述方法的用途
 * @ApiImplicitParam 方法    用于描述接口的非对象参数
 * @ApiImplicitParams 方法    用于描述接口的非对象参数集
 * @ApiIgnore 类/方法/参数	Swagger 文档不会显示拥有该注解的接口
 * @ApiModel 参数实体类    可设置接口相关实体的描述
 * ApiModelProperty	参数实体类属性	可设置实体属性的相关描述
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket getUserDocket(){
        ApiInfo apiInfo=new ApiInfoBuilder()
                .title("用户管理")//api标题
                .description("用户管理相关接口描述")//api描述
                .version("1.0.0")//版本号
                .contact(new Contact("jack", "", "jackdream@163.com"))
                .build();
        return new Docket(DocumentationType.SWAGGER_2)//文档类型（swagger2）
                .apiInfo(apiInfo)//设置包含在json ResourceListing响应中的api元信息
                .select()//启动用于api选择的构建器
                .apis(RequestHandlerSelectors.basePackage("com.user.auth.user.controller"))//扫描接口的包
                .paths(PathSelectors.any())//路径过滤器（扫描所有路径）
                .build();
    }
}
