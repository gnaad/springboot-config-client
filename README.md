# Spring Cloud Config
## Config Client

This project is built to showcase the function of Spring Cloud Config.

### Advantages of this setup
* No need to restart running application for properties update
* Use of Spring Cloud Bus is not required
* AOP will take care of getting updated values from server

### Step 1
To add Config Client to existing project. Add below dependency
1. spring-cloud-starter-config
2. spring-boot-starter-actuator
3. spring-boot-starter-aop

### Step 2
Mention application name, profile and most importantly add the below properties<br/>

`management.endpoints.web.exposure.include=refresh`<br/>
`spring.config.import=optional:configserver:http://localhost:8888`

This enables an endpoint `/refresh` which needs to be refreshed before calling config value.

### Step 3
Add the below AOP code snippet. This code is used to get updated config properties from server.
```
@Aspect
@Component
public class BeforeConfig {

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void refreshConfig() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.postForObject("http://localhost:8080/actuator/refresh", new HttpEntity<>(null, headers), String.class);
    }
}
```

### Step 4
Add the below Configuration code snippet. This snippet is used to map config properties using prefix as parameter in annotation.
```
@Component("applicationProperties")
@ConfigurationProperties(prefix = "spring.datasource")
public class ApplicationProperties {

    private String url;
    private String username;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
```

### Step 5
Set the fetched values in controller/service/DAO layer using getter.
```
@RestController
public class ConfigController {

    @Autowired
    @Qualifier("applicationProperties")
    private ApplicationProperties prop;

    @GetMapping("/get")
    public String getEndPoint() {
        return prop.getUrl() + " " + prop.getUsername();
    }
}
```

### Step 6
Add the below annotation in main class of application to enable AOP and Configuration Properties.
```
@EnableAspectJAutoProxy
@EnableConfigurationProperties(ApplicationProperties.class)
```