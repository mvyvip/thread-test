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

## 使用方式
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
    
5. 前端监听后端返回http状态码为401的时候，刷新token，如果刷新失败就是refresh_token过期，直接重新调用获取token方法

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
    
9. 扩展jwt默认返回数据案例

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
    
10. 获取jwt扩展属性

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

1. 所属APP配置

     ```yml
    eaglet:
      security:
        appId: 23
    # ...目前支持两种方式，前端登录的时候传appId参数或者是配置文件指定，前端传参优先级高
    ```

2. OAuth2认证服务器配置

     ```yml
    eaglet:
      security:
        oauth2:
    #      jwtSigningKey: eaglet # jwt加密秘钥
    #      tokenStore: jwt  # 目前支持jwt和redis两种方式，默认是redis，可显示指定为jwt
          clients:  # 客户端属性，通过list配置
            - clientId: eaglet  # 客户端ID
              clientSecret: 123456  # 客户端秘钥
              scope: all    # 授权范围
              accessTokenValidateSeconds: 7200  # token过期时间，默认是2小时
              refreshTokenValiditySeconds: 2592000  # refresh_token过期时间，默认是1个月
              grantType:    # 授权方式
                - refresh_token
                - password
                - authorization_code
    ```
    
3. 验证码配置

     ```yml
    eaglet:
      security:
        code:
          image:    #图片验证码配置
            width: 67   # 图片宽，默认67
            height: 23  # 图片高，默认23
            length: 4   # 验证码长度，默认为4
            expireIn: 60 # 过期时间，默认为60秒
            url:  # 要拦截的url，多个url用逗号隔开
          sms:  # 短信验证码配置
            length: 6   # 验证码长度，默认为6
            expireIn: 60 # 过期时间，默认为60秒
            url:  # 要拦截的url，多个url用逗号隔开
    ```
    
4. 扩展验证码实现相关配置
    
    ```text
    1.验证码存储逻辑默认为redis存储，可实现 ValidateCodeRepository + @Component 覆盖默认配置，生成验证码时，请求头中必须携带deviceId参数，用于判断所属
    2.图形验证码默认实现为ImageCodeGenerator，可实现 ValidateCodeGenerator + @Component("imageValidateCodeGenerator") 覆盖
    3.短信验证码发送器默认为DefaultSmsCodeSender发送（只是简单的打印），需根据实际业务配置 bean, 可实现 SmsCodeSender + @Component 接口方法覆盖 
    ```
    
## 示例项目
克隆项目，运行```eaglet-security-web```包内的```WebSecurityApplication```。
