1，用户、角色、菜单 等模块组成的一个简单的权限德莫。

2，本项目运行流程。

（1）先校验是否登录。
校验token是否合法，如果合法，解析出userId，将userId放入request中。

（2）通过（1）中的userId、结合切面，校验是否有权限访问接口。


mybatis-plus 一些简单用法。
![img.png](img.png)

![img_1.png](img_1.png)