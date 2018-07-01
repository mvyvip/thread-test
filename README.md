# Eaglet Spring Boot Starter Security Oauth2
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.alibaba/druid-spring-boot-starter/badge.svg)](http://gitlab.develop.feedel.net/modules/eaglet-security)

## 项目介绍
Eaglet Spring Boot Starter Security Oauth2 用于帮助你在Spring Boot项目中轻松集 Spring Security Oauth2 用于权限认证

## 项目结构

``` lua
eaglet-security
├── eaglet-security-browser -- 基于spring security 版本集成非前后端分离web项目的模块（目前可以先不用管这个项目）
├── eaglet-security-core -- 核心源代码模块
├── eaglet-spring-boot-starter-security-oauth2 -- 基于springboot做的starter，用于快速集成spring security oauth2
├── eaglet-security-starter-spring -- 基于spring做的starter，用于非springboot项目快速集成spring security oauth2
├── eaglet-security-web -- 统一权限管理系统，前后端分离项目可参考这个
```

## 在spring-boot 项目中使用
1. 在 Spring Boot 项目中加入```eaglet-spring-boot-starter-security-oauth2```依赖

    
    ```Maven```
    <dependency>
        <groupId>com.eaglet</groupId>
        <artifactId>eaglet-security-core</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    ```
    
2. 添加常用配置
    ```yml
   eaglet:
      security:
        oauth2:
          jwtSigningKey: eaglet
          tokenStore: jwt
          clients:
            - clientId: eaglet
              clientSecret: 123456
    # ...其他配置（可选，不是必须的，见下面文档）
    ```
    
3. 密码模式获取token 
    
        header中加Authorization字段：  base64(clientId:clientSecret)
        http://localhost:8081/oauth/token?username=test&scope=all&password=123456&grant_type=password

4. 自定义登录方式获取token 
    
        header中加Authorization字段：  base64(clientId:clientSecret)
        localhost:8081/user/login?username=test&password=123456
    
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

4. 获取当前用户权限列表
    
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


5. 获取当前用户菜单列表
        
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

5. 刷新token

        header中加Authorization字段：  base64(clientId:clientSecret)
        http://localhost:8081/oauth/token?grant_type=refresh_token&refresh_token=9f0509fc-5b9f-4067-acf2-38f5f545ff8f
    

6. 退出登录
    
        header中加Authorization字段： token_type + " " + access_token
        http://localhost:8081/logout

    
7. 自定义登录验证伪代码实现
    
```java
@Slf4j
@Component
public class ConsumerUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.findUserByName(username);
        if(sysUser == null) {
            return null;
        }

        List<String> perms = sysUserService.findPerms(sysUser.getId());

        return new CustomUserDetails(sysUser.getId(), username, sysUser.getPassword(),
            sysUser.getStatus() == 1 ? true : false, true, true, true,
            AuthorityUtils.createAuthorityList(perms.toArray(new String[perms.size()])));
    }
}
```
8. 扩展security默认返回数据案例

```java
@Data
public class CustomUserDetails extends User {

    private Long userId;

    public CustomUserDetails(String username, String password,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public CustomUserDetails(Long userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }

}
```

扩展jwt默认返回数据案例

```java
public class DefaultJwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(enhance());

        return accessToken;
    }

    public Map<String, Object> enhance() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "test");
        return info;
    }

}

```


获取jwt扩展属性
```java
@GetMapping("/me")
public Object getCurrentUser(@AuthenticationPrincipal Authentication user, HttpServletRequest request) throws UnsupportedEncodingException {
    String token = StringUtils.substringAfter(request.getHeader("Authorization"), "bearer ");
    
    Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
            .parseClaimsJws(token).getBody();
    
    String name = (String) claims.get("name");
    
    return user;
}

```

## 配置属性
详细配置属性见[```SecurityProperties```](http://gitlab.develop.feedel.net/modules/eaglet-security/blob/dev/eaglet-security-core/src/main/java/com/eaglet/security/core/properties/SecurityProperties.java) 内提供```setter```方法的可配置属性都将被支持。你可以参考WIKI文档或通过IDE输入提示来进行配置。配置文件的格式你可以选择```.properties```或```.yml```，效果是一样的，在配置较多的情况下推荐使用```.yml```。


## 示例项目
克隆项目，运行```eaglet-security-web```包内的```WebSecurityApplication```。
