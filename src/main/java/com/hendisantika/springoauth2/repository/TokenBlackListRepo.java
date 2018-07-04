package com.hendisantika.springoauth2.repository;

import com.hendisantika.springoauth2.entity.TokenBlackList;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-oauth2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 05/07/18
 * Time: 06.32
 * To change this template use File | Settings | File Templates.
 */
public interface TokenBlackListRepo extends Repository<TokenBlackList, Long> {
    Optional<TokenBlackList> findByJti(String jti);

    List<TokenBlackList> queryAllByUserIdAndIsBlackListedTrue(Long userId);

    void save(TokenBlackList tokenBlackList);

    List<TokenBlackList> deleteAllByUserIdAndExpiresBefore(Long userId, Long date);
}