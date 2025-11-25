# spring-oauth2

## Using Spring OAuth2 to Secure REST APIs

This project demonstrates OAuth2 authentication with Spring Boot 4.0 and Spring Security OAuth2 Authorization Server.

### Tech Stack

- **Spring Boot**: 4.0.0
- **Spring Security**: 7.0
- **Spring Security OAuth2 Authorization Server**: 7.0
- **Java**: 21
- **Database**: H2 (in-memory)

### Features

- OAuth2 Authorization Server with JWT tokens
- OAuth2 Resource Server for protecting REST APIs
- Support for Client Credentials grant type
- Support for Authorization Code grant type
- OpenID Connect (OIDC) support
- In-memory user and client storage
- H2 Console for database inspection

### Pre-configured Users

| Username | Password | Roles                 |
|----------|----------|-----------------------|
| user     | password | ROLE_USER             |
| admin    | admin    | ROLE_ADMIN, ROLE_USER |

### Pre-configured OAuth2 Clients

| Client ID    | Secret | Grant Types                                           | Scopes                       |
|--------------|--------|-------------------------------------------------------|------------------------------|
| trusted-app  | secret | client_credentials, refresh_token, authorization_code | read, write, openid, profile |
| register-app | secret | client_credentials                                    | registerUser                 |

### To Build and Run

```bash
# Clone the repository
git clone https://github.com/hendisantika/spring-oauth2.git
cd spring-oauth2

# Build the project
./mvnw clean package

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### OAuth2 Endpoints

| Endpoint                            | Description              |
|-------------------------------------|--------------------------|
| `/.well-known/openid-configuration` | OpenID Connect Discovery |
| `/oauth2/authorize`                 | Authorization endpoint   |
| `/oauth2/token`                     | Token endpoint           |
| `/oauth2/jwks`                      | JSON Web Key Set         |
| `/oauth2/introspect`                | Token introspection      |
| `/oauth2/revoke`                    | Token revocation         |
| `/userinfo`                         | OpenID Connect UserInfo  |

### API Endpoints

| Endpoint                | Required Role      | Description          |
|-------------------------|--------------------|----------------------|
| `/`                     | Public             | Hello World endpoint |
| `/api/hello`            | ROLE_USER          | Greeting for users   |
| `/api/me`               | ROLE_USER/ADMIN    | Current user info    |
| `/api/admin`            | ROLE_ADMIN         | Admin-only endpoint  |
| `/api/account/register` | SCOPE_registerUser | User registration    |
| `/h2-console`           | Public             | H2 Database Console  |

### cURL Commands

Install [jq](https://stedolan.github.io/jq/) for JSON formatting (optional).

#### Get an Access Token (Client Credentials)

```bash
curl -X POST "http://localhost:8080/oauth2/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -u "trusted-app:secret" \
  -d "grant_type=client_credentials&scope=read"
```

#### Get OpenID Configuration

```bash
curl http://localhost:8080/.well-known/openid-configuration | jq
```

#### Get JWK Set

```bash
curl http://localhost:8080/oauth2/jwks | jq
```

#### Access Protected Resource

```bash
# First, get a token
TOKEN=$(curl -s -X POST "http://localhost:8080/oauth2/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -u "trusted-app:secret" \
  -d "grant_type=client_credentials&scope=read" | jq -r '.access_token')

# Then use the token to access a protected resource
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/test
```

#### Login via Browser (Authorization Code Flow)

1. Open your browser and navigate to:
   ```
   http://localhost:8080/oauth2/authorize?response_type=code&client_id=trusted-app&scope=openid%20profile%20read&redirect_uri=http://localhost:8080/login/oauth2/code/trusted-app
   ```

2. Log in with `user/password` or `admin/admin`

3. Exchange the authorization code for tokens

### H2 Console

Access the H2 database console at: `http://localhost:8080/h2-console`

- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave empty)

### Project Structure

```
src/main/java/com/hendisantika/springoauth2/
├── config/
│   └── DataInitializer.java         # Sample data initialization
├── controller/
│   ├── AccountController.java       # Account management endpoints
│   └── GeneralController.java       # General REST endpoints
├── entity/
│   ├── Account.java                 # User entity
│   ├── Role.java                    # Role enum
│   └── TokenBlackList.java          # Token blacklist entity
├── repository/
│   ├── AccountRepo.java             # Account repository
│   └── TokenBlackListRepo.java      # Token blacklist repository
├── security/
│   ├── AuthorizationConfig.java     # OAuth2 Authorization Server config
│   └── SecurityConfig.java          # Spring Security config
├── service/
│   ├── AccountService.java          # Account/UserDetails service
│   └── TokenBlackListService.java   # Token blacklist service
└── SpringOauth2Application.java     # Main application class
```

### Migration Notes (from Spring Boot 2.x)

This project has been migrated from the deprecated `spring-cloud-starter-oauth2` to the modern Spring Security OAuth2
Authorization Server. Key changes include:

1. **Dependencies**: Replaced `spring-cloud-starter-oauth2` with:
    - `spring-boot-starter-oauth2-authorization-server`
    - `spring-boot-starter-oauth2-resource-server`

2. **Configuration**: Migrated from `AuthorizationServerConfigurerAdapter` and `WebSecurityConfigurerAdapter` to the new
   component-based configuration with `SecurityFilterChain` beans.

3. **Jakarta EE**: Updated from `javax.persistence` to `jakarta.persistence`.

4. **JUnit**: Updated from JUnit 4 to JUnit 5.

5. **Token Endpoint**: Changed from `/oauth/token` to `/oauth2/token`.

### License

This project is open source and available under the [MIT License](LICENSE).
