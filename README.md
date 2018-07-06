# spring-oauth2

# Using Spring Oauth2 to secure REST

This project is part of a tutorial about Oauth2 authentication on Spring. You can read the material here.

### Tutorial's Summary

How to create from scratch a REST service with Spring Boot. We'll secure it using the Oauth2 protocol, using JSON Web Tokens, or JWT. There are several interesting materials scattered on the web, however, after studying a lot of then, I believe that the theme could be examined a little further. Instead of simply showing how to configure the server, I'll try to briefly explain why such configuration is necessary.

### To Build and Run
Go to the cloned directory and run `mvn spring-boot:run` or build with your chosen IDE.

### Curl Commands
You should install [./JQ](https://stedolan.github.io/jq/) before running these Curl commands.

### To get a new token
`curl trusted-app:secret@localhost:8080/oauth/token -d "grant_type=password&username=user&password=password" | jq`

### To get a refresh token
`curl trusted-app:secret@localhost:8080/oauth/token -d "grant_type=refresh_token&jti=[JTI]&refresh_token=[REFRESH_TOKEN]" | jq`

### To access a protected resource
`curl -H "Authorization: Bearer [ACCESS_TOKEN]" localhost:8080/api/hello`