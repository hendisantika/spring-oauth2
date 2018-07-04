package com.hendisantika.springoauth2.repository;

import com.hendisantika.springoauth2.entity.Account;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-oauth2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 05/07/18
 * Time: 06.31
 * To change this template use File | Settings | File Templates.
 */
public interface AccountRepo extends Repository<Account, Long> {
    Optional<Account> findByUsername(String username);

    Account save(Account account);

    int deleteAccountById(Long id);
}