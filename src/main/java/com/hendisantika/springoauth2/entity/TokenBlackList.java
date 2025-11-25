package com.hendisantika.springoauth2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-oauth2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 04/07/18
 * Time: 07.14
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class TokenBlackList {

    @Id
    private String jti;
    private Long userId;
    private Long expires;
    private Boolean isBlackListed;

    public TokenBlackList() {
    }

    public TokenBlackList(Long userId, String jti, Long expires) {
        this.jti = jti;
        this.userId = userId;
        this.expires = expires;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public boolean isBlackListed() {
        return isBlackListed;
    }

    public void setBlackListed(boolean blackListed) {
        isBlackListed = blackListed;
    }
}
