# JWT와 Vue.js로 구현한 인증/인가 Demo Project


## 1. 개요

**전통적인 웹**에서는 `Cookie` 및 `Session`을 기반으로 클라이언트(`User`)의 상태(`State`)를 추적하고 관리하는 것이 일반적입니다. 반면 **범용 API**와 같이 **RESTful 아키텍처**를 기반으로 구현된 서버의 경우에는 클라이언트의 상태를 굳이 별도의 Session 서버를 통해 관리하지 않습니다. 이것은 **RESTful 아키텍처**의 주요한 특징 중 하나인 **stateless** 특성에서 기인한 것으로 API 서버는 다만 요청(Payload)에 따라 결과(Resource/Data)를 응답하는 것에만 집중하게 됩니다.

이러한 이유로 현행 대다수의 **API 서버 - 클라이언트(이를테면 렌더링 역할을 서버에 의존하지 않는 Single Page Application) 간의 통신**에 있어서 불가피한 사용자 인증(`Authentication`) 및 인가(`Authorization`) 기능을 구현하기 위해 `Session`의 대안으로써 대다수가 **JWT(Json Web Token)**, **OAuth** 등의 프로토콜을 사용하고 있습니다.

**spring-boot-jwt-vue3**은 Spring @RestController로 구현한 **API 서버**와 Vue.js로 구현한 **SPA 클라이언트** 간의 인증 및 인가 기능을 **JWT**로 구현한 Demo App입니다.

덧붙여 **Session을 활용한 인증/인가**의 경우, **KYUNGSE.PoC**의 또다른 프로젝트인 [project-web-starter](https://github.com/kyungseo/project-web-starter)를 참고하면 됩니다. **project-web-starter**는 `Spring Security`와 `Thymeleaf`로 구현하였습니다.

![image](/docs/vue-main.png)

### 1.1 프로젝트의 구성

#### 멀티 모듈

* **spring-boot-jwt-vue3**: 상위(parent) POM 프로젝트 모듈
  * **jwt-backend**: JWT(Token) 기반의 백엔드 서비스를 구동하기 위한 모듈 - ★ spring-boot 구동 App (8090 Port)
  * **vue3-frontend**: Vue.js, Vuex, Vue Router, Axios 등으로 구현하는 프론트엔드 UI 모듈

#### jwt-backend 모듈의 구성

```
jwt-backend: src/main
│
├── java
│    └── kyungseo.poc
│         ├── demo                          → Demo App
│         │    ├── common                     : Demo App의 common package
│         │    └── jwt                        : Demo App의 sample(JWT) package
│         ├── framework                     → Todo App module
│         │    ├── auth                       : JWT component, validation, event, etc.
│         │    ├── config                     : Application Configuration
│         │    └── ...                        : etc.
│         │
│         └──★ KyungseoPocApplication.java → Spring Boot Application (@SpringBootApplication)
│
├── resources
│    ├── i18n                               → messages
│    ├── mybatis                            → MyBatis Mapper XML
│    ├── public                             → vue3-frontend를 build한 결과물이 여기에 copy 됨!
│    ├── templates                          → freemarker 기반의 email 템플릿
│    └── application.properties             → Application Properties
│
└── pom.xml                                 → Maven POM file
```

#### vue3-frontend 모듈의 구성

```
vue3-frontend
|
├─┬ public                      → index.html 및 favicon
│ │
│ └ src
│    ├── assets                 → static 파일들이 위치
│    ├── common                 → EventBus.js 등
│    ├── components             → 화면 컴포넌트들이 위치
│    ├── modules                → Toast.vue 등 공용 모듈
│    ├── plugins                → cookie.js, font-awesome.js 등
│    ├── router                 → vue-router
│    ├── services               → Axios 기반의 서비스들이 위치
│    ├── store                  → vuex 기반의 storage
│    ├── App.vue                → 메인 App
│    └── main.js                → main.js
|
├── target
│    └─ dist                    → build target dir
|
├── babel.config.js
├── package.json                → dependency
├── pom.xml                     → Maven POM file
└── vue.config.js               → devServer에 대한 proxy 정의!

```

### 1.3 적용 기술

#### 기반 Infra.

| Technology | Spec |
| --- | --- |
| Java | java-11-openjdk-11.0.2 |
| Node | node v16.19.0, npm v8.19.3 |
| Database | H2 Database 2.1.214 (추가적으로 MariaDB 10.10.2에서 테스트) |

* **Node**의 경우 16 버전을 사용합니다. *- 18 버전의 경우 오류가 있을 수 있습니다.*

```bash
d:\dev-home\workspace-test\spring-boot-jwt-vue3>nvm list

    18.12.1
  * 16.19.0 (Currently using 64-bit executable)

d:\dev-home\workspace-test\spring-boot-jwt-vue3>npm -v
8.19.3
```

#### 기술 Spec.

<table style="border: 2px;">
  <tr>
    <td align="center"> <b>Module</b> </td>
    <td align="center"> <b>Dependency</b> </td>
  </tr>
  <tr>
    <td> <b>jwt-backend</b> </td>
    <td>
      <ul>
        <li><b>Spring Boot</b>: Spring Boot 2.7.8</li>
        <li><b>Spring Framework</b>: Spring Web 5.3.25, Spring Security 5.7.6, etc</li>
        <li><b>ORM Framework</b>: Spring Data JPA 2.7.7, Hibernate 5.6.14, QueryDSL 5.0.0</li>
        <li><b>SQL Mapper</b>: MyBatis 3.5.11</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td> <b>vue3-frontend</b> </td>
    <td>
      <ul>
        <li><b>Node.js</b>: node v16.19.0, npm  v8.19.3</li>
        <li><b>JavaScript Framework</b>: Vue 3 (+ Vuex 4, Axios, Vue Router 4, VeeValidate 4, etc)</li>
        <li><b>CSS Library</b>: Bootstrap 4.6</li>
      </ul>
    </td>
  </tr>
</table>

## 2 Project Setup

### 2.1 Clone 'spring-boot-jwt-vue3'

`git` 명령을 사용하여 **spring-boot-jwt-vue3** 프로젝트를 `clone` 합니다.

```bash
$ git clone https://github.com/kyungseo/spring-boot-jwt-vue3.git
$ cd spring-boot-jwt-vue3
```

### 2.2 SMTP 설정

**jwt-backend** 모듈을 구동(`mvn spring-boot:run`) 후 demo 사이트에 접속하여 새로운 사용자를 등록하거나 비밀번호를 재설정하는 작업을 수행할 때 event를 통해 등록된 email로 실제로 메일을 전송합니다. 메일을 정상적으로 발송하기 위해서는 **jwt-backend** 모듈의 설정 파일인 `src/main/resources/application.properties`를 편집하여 관련 설정을 수정해야 합니다.

````properties
# [ JavaMail ]
support.email=developer@company.com

spring.mail.host=smtp.google.com
spring.mail.port=465
spring.mail.protocol=smtps
spring.mail.username=deveoper
spring.mail.password=password
````

### 2.3 Build

**SMTP 설정**이 완료되었다면, 다시 **spring-boot-jwt-vue3** 모듈 디렉토리로 이동한 후 다음 명령을 사용하여 하위 모듈을 포함하여 전체 프로젝트를 `build` 하도록 합니다.

```bash
mvn clean install

````
결과 화면이 다음과 같이 나오면 정상적으로 build 작업이 완료된 것입니다.

![image](/docs/vue3-project-build-maven.png)


### 2.4 Run & Test

`build` 절차가 완료된 후 **jwt-backend** 모듈 디렉토리로 다시 이동한 후 `mvn spring-boot:run` 명령을 실행하도록 합니다.

```bash
mvn spring-boot:run

```

서버가 정상적으로 기동되었다면, 이제 Chrome 이나 Edge 등의 Browser 열고 다음 주소로 접속하여 JWT Demo App을 테스트할 수 있습니다.

* [http://localhost:8090](http://localhost:8090)
* Test ID/PW
  * Admin 유저: admin@company.com / password
  * 일반 유저: user001@company.com / password

#### 참고 사항

**vue3-frontend** 모듈의 `pom.xml`을 보면 maven build 시에 `frontend-maven-plugin`를 사용하여 다음 명령들이 수행되는 것을 확인할 수 있습니다.

```bash
$ npm install
$ npm run build
```

이때, build 결과는 `target/dist` 디렉토리에 생성되며, 이후 **jwt-backend** 모듈이 build 될 때 **vue3-frontend** 모듈의 `target/dist`의 결과 리소스들을 자신의 `src/main/resources/public` 하위에 모두 copy하는 구조입니다. 다음 **jwt-backend** 모듈의 pom 파일을 참고하시기 바랍니다.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-resources-plugin</artifactId>
    <version>3.1.0</version>
    <executions>
        <execution>
            <id>copy Vue.js frontend content</id>
            <phase>generate-resources</phase>
            <goals>
                <goal>copy-resources</goal>
            </goals>
            <configuration>
                <outputDirectory>src/main/resources/public</outputDirectory>
                <overwrite>true</overwrite>
                <resources>
                    <resource>
                        <directory>${project.parent.basedir}/vue3-frontend/target/dist</directory>
                        <includes>
                            <include>static/</include>
                            <include>index.html</include>
                            <include>favicon.ico</include>
                        </includes>
                    </resource>
                </resources>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## 3. spring-boot-jwt-vue3 주요 기능

`spring-boot-jwt-vue3`에서 구현하고 있는 주요 기능은 다음과 같습니다.

* Email 및 Username을 사용하여 사용자를 등록 - 등록 시 해당 Email이 이미 사용중인지 체크
* JWT token 생성 및 Spring Security와의 통합을 통해 역할 및 권한에 따른 URL 보안 가능
* 사용자 기기ID(Device ID)를 생성하여 다중 기기에 대한 로그인 로그아웃
* 사용자 로그아웃 시 JWT token을 Black-list에 추가하기 위한 메모리 저장소 지원
* JWT token이 만료되면 refresh를 통해 자동으로 새로운 token을 생성
* 분실된 비밀번호를 재설정 가능 - secret 및 token의 유효성 검사를 통해 리셋


### 3.1 JWT Flow

JWT token과 관련한 일련의 흐름들은 다음과 같습니다.

#### 사용자 등록 / 로그인 / 로그아웃 프로세스

![image](/docs/flow-jwt.png)


#### Token 갱신 프로세스

![image](/docs/flow-jwt-refresh.png)

### 3.2 Refresh 동작 방식

**Refresh 동작 방식**의 경우 추가 설명이 필요할 것 같습니다.

그전에 우선적으로 알아야할 사항이 있습니다. 실제로는 조금 더 복잡하지만 기술하는 내용에는 몇 가지 절차와 분기가 생략되었습니다.

* Vue client에서는 Axios를 사용해 server에 요청을 보냅니다.
* 사용자가 최최 로그인하게 되면 accessToken(이하 token)과 refreshToken이 localStorage에 저장됩니다.
* 이 Axios에 request와 response에 대한 interceptor가 사용 됩니다. - 부연하자면 interceptor는 http 요청과 응답이 실제로 전송되거나 수신되기 전에 작동하는 filter로 이해할 수 있습니다.
  * request interceptor: 서버로 요청을 전송하기 직전에 localstorage에 저장된 token을 가져와 Header에 Athentication token을 세팅하여 요청을 전송합니다.
  * response interceptor: 서버에서 수신한 응답을 가로채어 추가적으로 token refresh가 필요한지 확인하게 됩니다.

**Refresh 동작 방식**은 다음과 같습니다.
1. request interceptor client에서 서버에 token이 포함된 요청을 전송
2. server에서 요청을 받고 현재 token이 유효한지를 체크. 만약 기간이 만료된(expired) 상태라면 server에서 401 발생
3. response interceptor에서 401에러 확인 후 server에 refreshToken을 payload로 refresh api를 요청
4. server에서는 요청 받은 refreshToken이 유효한지 refreshToken 자체가 만료되지 않았지는를 체크하고 이상 없다면 새로운 token을 생성하여 발급
5. response interceptor에서는 update된 token을 다시 localStorage에 저장하고 refres api 요청 직전의 원래 요청을 retry하여 서버에 다시 요청

**관련 코드**는 **vue3-frontend** 모듈에서 `apiInterceptors.js`를 확인하면 됩니다.

```js
  axiosInstance.interceptors.request.use(
    (config) => {
      if (config.url !== "/auth/login") {
        const token = TokenService.getLocalAccessToken();
        if (token) {
          config.headers["Authorization"] = 'Bearer ' + token;
        }
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  axiosInstance.interceptors.response.use(
    (res) => {
      return res;
    },
    async (err) => {
      const originalConfig = err.config;

      if (originalConfig.url !== "/auth/login" && err.response) {
        if (err.response.status === 401 && !originalConfig._retry) {
          originalConfig._retry = true;
          try {
            const rs = await axiosInstance.post("/auth/refresh", {
              refreshToken: TokenService.getLocalRefreshToken(),
            });
            const { accessToken } = rs.data;
            store.dispatch('auth/refreshToken', accessToken);
            TokenService.updateLocalAccessToken(accessToken);

            return axiosInstance(originalConfig);
          } catch (_error) {
            return Promise.reject(_error);
        }
      }
      return Promise.reject(err);
    }
  );
```

추가적으로 refresh 요청 시 발생할 수 있는 무한루프를 방지하기 위한 조치로 **jwt-backend** 모듈 내의 `JwtAuthenticationFilter`에 `isNoFilterURI` 관련 코드를 적용하고 있습니다.

```java
private final String[] NO_FILTER_URI = {
    AppConstants.API_JWT_URI_PREFIX + "/auth/login",
    AppConstants.API_JWT_URI_PREFIX + "/auth/refresh"
};

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
    try {
        boolean isNoFilterURI = Arrays.asList(NO_FILTER_URI).contains(request.getRequestURI());
        String accessToken = getJwtFromRequest(request);
        if (!isNoFilterURI && StringUtils.hasText(accessToken) && jwtTokenValidator.validateToken(accessToken)) {
            Long userId = jwtTokenProvider.getUserIdFromJWT(accessToken);
            ...
        }
    }
    catch (Exception ex) {
        throw ex;
    }
    filterChain.doFilter(request, response);
}
```

참고로 현재 버전의 경우 refresh 동작을 쉽게 테스트하기 위해서 **vue3-backend** 설정(`application.properties`)에서 원래 1 시간(3600000 ms)인 token 만료 시간을 2 분(120000 ms) 정도로 수정하여 두었습니다. - Production 환경의 경우 1 시간 정도로 설정하는 것이 좋습니다.

```properties
token.access.expire.time=120000
```

테스트 방법은 먼저 Brower에서 개발자 도구를 연후 로그인 하도록 합니다.

![image](/docs/vue-refresh-01.png)

아무런 액션을 취하지 않고 2 분 이상 기다리도록 합니다.

충분히 시간이 지났다면 이제 다른 링크를 클릭하는 등의 액션을 수행합니다. 이때 개발자 도구의 Console 로그를 확인해보면 refresh 수행 후 원래 요청이 다시 수행되는 것을 확인할 수 있습니다.

![image](/docs/vue-refresh-02.png)


## 4 Demo App 화면

### 4.1 사용자 등록

사이트 접속 후 상단 메뉴를 통해 사용자 등록이 가능합니다.

![image](/docs/vue-register.png)

사용자 등록을 완료하기 위해서는 verification email을 반드시 확인해야 합니다.

![image](/docs/vue-register-email-verification.png)


### 4.2 로그인

사용자 등록이 완료었거나 이미 알고 있는 ID/PW(admin@company.com/password)를 사용하여 로그인합니다.

![image](/docs/vue-login-view.png)

로그인이 완료되면 사용자 프로필 화면으로 이동합니다.

![image](/docs/vue-login-profile.png)


### 4.3 권한에 따른 화면 접근

public 화면은 로그인하지 않은 익명사용자도 접근할 수 있습니다.

![image](/docs/vue-menu-public.png)

관리자 화면은 `ROLE_ADMIN` 역할을 보유한 인증사용자만 접근할 수 있습니다.

![image](/docs/vue-menu-admin.png)


스탭 화면은 `ROLE_STAFF` 역할을 보유한 인증사용자만 접근할 수 있습니다.

![image](/docs/vue-menu-staff.png)


일반 사용자 화면은 `ROLE_USER` 역할을 보유한 인증사용자만 접근할 수 있습니다.

![image](/docs/vue-menu-user.png)


### 4.4 Sample 화면


다음의 샘플 화면은 시스템에 등록된 사용자 목록을 Paging하여 보여주는 예제입니다.

![image](/docs/vue-menu-sample.png)


## 5 맺음

**spring-boot-jwt-vue3**는 **Apache 2.0** 라이선스 하에 배포됩니다. 일부 코드의 경우 [Jwt-Spring-Security-JPA](https://github.com/isopropylcyanide/Jwt-Spring-Security-JPA)의 원본 소스를 기반으로 수정되었습니다.

버그 및 이슈에 대한 리포트나 개선에 대한 의견을 환영합니다!
