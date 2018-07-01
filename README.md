# Eaglet Spring Boot Starter Security Oauth2

## 项目结构

``` lua
eaglet-security
├── eaglet-security-browser -- 基于spring security 版本集成非前后端分离web项目的模块（目前可以先不用管这个项目）
├── eaglet-security-core -- 核心源代码模块
├── eaglet-spring-boot-starter-security-oauth2 -- 基于springboot做的starter，用于快速集成spring security oauth2
├── eaglet-security-starter-spring -- 基于spring做的starter，用于非springboot项目快速集成spring security oauth2
├── eaglet-security-web -- 统一权限管理系统，前后端分离项目可参考这个
```

## SPRING-BOOT版文档 | [SPRING版文档](http://gitlab.develop.feedel.net/modules/eaglet-security/README_SPRING.md)  
Eaglet Spring Boot Starter Security Oauth2 用于帮助你在Spring Boot项目中轻松集 Spring Security Oauth2 用于权限认证

## 如何使用
1. 在 Spring Boot 项目中加入```druid-spring-boot-starter```依赖

    ```xml
    <dependency>
        <groupId>com.eaglet</groupId>
        <artifactId>eaglet-spring-boot-starter-security-oauth2</artifactId>
        <version>1.0.0</version>
    </dependency>

    <distributionManagement>
        <repository>
          <id>eaglet-deploy-release</id>
          <url>http://192.168.3.131:8081/repository/maven-releases/</url>
        </repository>

        <snapshotRepository>
          <id>eaglet-deploy-snapshot</id>
          <url>http://192.168.3.131:8081/repository/maven-snapshots/</url>
        </snapshotRepository>
    </distributionManagement>
    ```
2. 添加常用配置
    ```yml
    eaglet:
      security:
        oauth2:
          clients:
            - clientId: eaglet
              clientSecret: 123456
              scope: all
              accessTokenValidateSeconds: 7200
              refreshTokenValiditySeconds: 2592000
              grantType:
                - refresh_token
                - password
                - authorization_code
    # ...其他配置（可选，不是必须的，见下面文档）
    ```

## 配置属性
详细配置属性见[```SecurityProperties```](http://gitlab.develop.feedel.net/modules/eaglet-security/blob/dev/eaglet-security-core/src/main/java/com/eaglet/security/core/properties/SecurityProperties.java) 内提供```setter```方法的可配置属性都将被支持。你可以参考WIKI文档或通过IDE输入提示来进行配置。配置文件的格式你可以选择```.properties```或```.yml```，效果是一样的，在配置较多的情况下推荐使用```.yml```。


1. 密码模式获取token 
    
        header中加Authorization字段：  base64(clientId:clientSecret)
        http://localhost:8081/oauth/token?username=test&scope=all&password=123456&grant_type=password&appId=23

2. 自定义登录方式获取token（推荐）
    
        header中加Authorization字段：  base64(clientId:clientSecret)
        localhost:8081/user/login?username=test&password=123456&appId=23
    
    ```
    {
      "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Mjk5MTcyMTIsInVzZXJfbmFtZSI6InRlc3QiLCJhdXRob3JpdGllcyI6WyJhZG1pbiIsIlJPTEVfVVNFUiJdLCJqdGkiOiJlODQ4Y2NlMC1mY2QyLTQxNWEtODAxMi01NWY2NjcyYTkyOGUiLCJjbGllbnRfaWQiOiJlYWdsZXQiLCJzY29wZSI6WyJhbGwiXX0.xtZDfy53DfKwxoIEO44mRSGx6LCR7BI9Y8LkU1wLWoA",
      "token_type": "bearer",
      "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ0ZXN0Iiwic2NvcGUiOlsiYWxsIl0sImF0aSI6ImU4NDhjY2UwLWZjZDItNDE1YS04MDEyLTU1ZjY2NzJhOTI4ZSIsImV4cCI6MTUzMjUwMjAxMiwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iLCJST0xFX1VTRVIiXSwianRpIjoiYzYyZDFjNjQtYWFiZi00MGU3LThhOTctNThjOWM4MmEwNGIzIiwiY2xpZW50X2lkIjoiZWFnbGV0In0.rW2IeWNvjZ4tyC5Yl7t20bGwrg3MA5i-XX_0gsDoYUM",
      "expires_in": 7199,
      "scope": "all",
      "jti": "e848cce0-fcd2-415a-8012-55f6672a928e"
    }
    ```

3. 外部系统接入的时候获取当前用户权限列表
    
    header中加Authorization字段： token_type + " " + access_token
        http://localhost:9000/sys/user/perms
        
    ```
    [
        "sys:app", 
        "sys:app:add", 
        "sys:app:delete", 
        "sys:app:edit", 
        "sys:app:list", 
        "sys:menu", 
        "sys:menu:add", 
        "sys:menu:delete", 
        "sys:menu:list", 
        "sys:role", 
        "sys:role:add", 
        "sys:role:delete", 
        "sys:role:edit", 
        "sys:role:list", 
        "sys:user", 
        "sys:user:add", 
        "sys:user:delete", 
        "sys:user:disable", 
        "sys:user:edit", 
        "sys:user:list", 
        "sys:user:perms"
    ]
    ```
    
4. 获取当前用户菜单列表
        
        header中加Authorization字段： token_type + " " + access_token
        http://localhost:9000/sys/user/menus
    ```
    [
        {
            "icon": "el-icon-date", 
            "index": "1", 
            "title": "应用管理", 
            "subs": [
                {
                    "icon": null, 
                    "index": "app", 
                    "title": "应用列表", 
                    "subs": [ ]
                }
            ]
        }, 
        {
            "icon": "el-icon-date", 
            "index": "3", 
            "title": "系统配置", 
            "subs": [
                {
                    "icon": null, 
                    "index": "menu", 
                    "title": "菜单管理", 
                    "subs": [ ]
                }, 
                {
                    "icon": null, 
                    "index": "user", 
                    "title": "用户管理", 
                    "subs": [ ]
                }, 
                {
                    "icon": null, 
                    "index": "role", 
                    "title": "角色管理", 
                    "subs": [ ]
                }
            ]
        }
    ]
    ```
    
6. 前端监听后端返回http状态码为401的时候，刷新token，如果刷新失败就是refresh_token过期，直接重新调用获取token方法

        header中加Authorization字段：  base64(clientId:clientSecret)
        http://localhost:8081/oauth/token?grant_type=refresh_token&refresh_token=9f0509fc-5b9f-4067-acf2-38f5f545ff8f
    
7. 退出登录
    
        header中加Authorization字段： token_type + " " + access_token
        http://localhost:8081/logout

- JDBC 配置
```xml
spring.datasource.druid.url= # 或spring.datasource.url= 
spring.datasource.druid.username= # 或spring.datasource.username=
spring.datasource.druid.password= # 或spring.datasource.password=
spring.datasource.druid.driver-class-name= #或 spring.datasource.driver-class-name=
```
- 连接池配置
```
spring.datasource.druid.initial-size=
spring.datasource.druid.max-active=
spring.datasource.druid.min-idle=
spring.datasource.druid.max-wait=
spring.datasource.druid.pool-prepared-statements=
spring.datasource.druid.max-pool-prepared-statement-per-connection-size= 
spring.datasource.druid.max-open-prepared-statements= #和上面的等价
spring.datasource.druid.validation-query=
spring.datasource.druid.validation-query-timeout=
spring.datasource.druid.test-on-borrow=
spring.datasource.druid.test-on-return=
spring.datasource.druid.test-while-idle=
spring.datasource.druid.time-between-eviction-runs-millis=
spring.datasource.druid.min-evictable-idle-time-millis=
spring.datasource.druid.max-evictable-idle-time-millis=
spring.datasource.druid.filters= #配置多个英文逗号分隔
....//more
```
- 监控配置
```
# WebStatFilter配置，说明请参考Druid Wiki，配置_配置WebStatFilter
spring.datasource.druid.web-stat-filter.enabled= #是否启用StatFilter默认值true
spring.datasource.druid.web-stat-filter.url-pattern=
spring.datasource.druid.web-stat-filter.exclusions=
spring.datasource.druid.web-stat-filter.session-stat-enable=
spring.datasource.druid.web-stat-filter.session-stat-max-count=
spring.datasource.druid.web-stat-filter.principal-session-name=
spring.datasource.druid.web-stat-filter.principal-cookie-name=
spring.datasource.druid.web-stat-filter.profile-enable=

# StatViewServlet配置，说明请参考Druid Wiki，配置_StatViewServlet配置
spring.datasource.druid.stat-view-servlet.enabled= #是否启用StatViewServlet默认值true
spring.datasource.druid.stat-view-servlet.url-pattern=
spring.datasource.druid.stat-view-servlet.reset-enable=
spring.datasource.druid.stat-view-servlet.login-username=
spring.datasource.druid.stat-view-servlet.login-password=
spring.datasource.druid.stat-view-servlet.allow=
spring.datasource.druid.stat-view-servlet.deny=

# Spring监控配置，说明请参考Druid Github Wiki，配置_Druid和Spring关联监控配置
spring.datasource.druid.aop-patterns= # Spring监控AOP切入点，如x.y.z.service.*,配置多个英文逗号分隔
```
Druid Spring Boot Starter 不仅限于对以上配置属性提供支持，[```DruidDataSource```](https://github.com/alibaba/druid/blob/master/src/main/java/com/alibaba/druid/pool/DruidDataSource.java) 内提供```setter```方法的可配置属性都将被支持。你可以参考WIKI文档或通过IDE输入提示来进行配置。配置文件的格式你可以选择```.properties```或```.yml```，效果是一样的，在配置较多的情况下推荐使用```.yml```。



## 如何配置多数据源
1. 添加配置
```xml
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

# Druid 数据源配置，继承spring.datasource.* 配置，相同则覆盖
...
spring.datasource.druid.initial-size=5
spring.datasource.druid.max-active=5
...

# Druid 数据源 1 配置，继承spring.datasource.druid.* 配置，相同则覆盖
...
spring.datasource.druid.one.max-active=10
spring.datasource.druid.one.max-wait=10000
...

# Druid 数据源 2 配置，继承spring.datasource.druid.* 配置，相同则覆盖
...
spring.datasource.druid.two.max-active=20
spring.datasource.druid.two.max-wait=20000
...
```
**强烈注意**：Spring Boot 2.X 版本不再支持配置继承，多数据源的话每个数据源的所有配置都需要单独配置，否则配置不会生效

2. 创建数据源
```java
@Primary
@Bean
@ConfigurationProperties("spring.datasource.druid.one")
public DataSource dataSourceOne(){
    return DruidDataSourceBuilder.create().build();
}
@Bean
@ConfigurationProperties("spring.datasource.druid.two")
public DataSource dataSourceTwo(){
    return DruidDataSourceBuilder.create().build();
}
```

## 如何配置 Filter
你可以通过 ```spring.datasource.druid.filters=stat,wall,log4j ...``` 的方式来启用相应的内置Filter，不过这些Filter都是默认配置。如果默认配置不能满足你的需求，你可以放弃这种方式，通过配置文件来配置Filter，下面是例子。
```xml
# 配置StatFilter 
spring.datasource.druid.filter.stat.db-type=h2
spring.datasource.druid.filter.stat.log-slow-sql=true
spring.datasource.druid.filter.stat.slow-sql-millis=2000

# 配置WallFilter 
spring.datasource.druid.filter.wall.enabled=true
spring.datasource.druid.filter.wall.db-type=h2
spring.datasource.druid.filter.wall.config.delete-allow=false
spring.datasource.druid.filter.wall.config.drop-table-allow=false

# 其他 Filter 配置不再演示
```
目前为以下 Filter 提供了配置支持，请参考文档或者根据IDE提示（```spring.datasource.druid.filter.*```）进行配置。
- StatFilter
- WallFilter
- ConfigFilter
- EncodingConvertFilter
- Slf4jLogFilter
- Log4jFilter
- Log4j2Filter
- CommonsLogFilter

要想使自定义 Filter 配置生效需要将对应 Filter 的 ```enabled``` 设置为 ```true``` ，Druid Spring Boot Starter 默认会启用 StatFilter，你也可以将其 ```enabled``` 设置为 ```false``` 来禁用它。

## IDE 提示支持
![](https://raw.githubusercontent.com/lihengming/java-codes/master/shared-resources/github-images/druid-spring-boot-starter-ide-hint.jpg)

## 演示
克隆项目，运行```test```包内的```DemoApplication```。

## 参考
[Druid Wiki](https://github.com/alibaba/druid/wiki/%E9%A6%96%E9%A1%B5)

[Spring Boot Reference](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
