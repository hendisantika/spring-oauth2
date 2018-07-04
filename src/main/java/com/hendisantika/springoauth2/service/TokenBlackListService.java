package com.hendisantika.springoauth2.service;

import com.hendisantika.springoauth2.entity.TokenBlackList;
import com.hendisantika.springoauth2.repository.TokenBlackListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-oauth2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 05/07/18
 * Time: 06.37
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TokenBlackListService {

    @Autowired
    TokenBlackListRepo tokenBlackListRepo;

    public Boolean isBlackListed(String jti) throws TokenNotFoundException {
        Optional<TokenBlackList> token = tokenBlackListRepo.findByJti(jti);
        if (token.isPresent()) {
            return token.get().isBlackListed();
        } else {
            throw new TokenNotFoundException(jti);
        }
    }

    @Async
    public void addToEnabledList(Long userId, String jti, Long expired) {
        // clean all black listed tokens for user
        List<TokenBlackList> list = tokenBlackListRepo.queryAllByUserIdAndIsBlackListedTrue(userId);
        if (list != null && list.size() > 0) {
            list.forEach(
                    token -> {
                        token.setBlackListed(true);
                        tokenBlackListRepo.save(token);
                    }
            );
        }
        // Add new token white listed
        TokenBlackList tokenBlackList = new TokenBlackList(userId, jti, expired);
        tokenBlackList.setBlackListed(false);
        tokenBlackListRepo.save(tokenBlackList);
        tokenBlackListRepo.deleteAllByUserIdAndExpiresBefore(userId, new Date().getTime());
    }

    @Async
    public void addToBlackList(String jti) throws TokenNotFoundException {
        Optional<TokenBlackList> tokenBlackList = tokenBlackListRepo.findByJti(jti);
        if (tokenBlackList.isPresent()) {
            tokenBlackList.get().setBlackListed(true);
            tokenBlackListRepo.save(tokenBlackList.get());
        } else throw new TokenNotFoundException(jti);
    }

    public static class TokenNotFoundException extends Exception {
        public String jti;
        public String message;

        public TokenNotFoundException(String jti) {
            super();
            this.jti = jti;
            message = String.format("Token with jti[%s] not found.", jti);
        }
    }
}