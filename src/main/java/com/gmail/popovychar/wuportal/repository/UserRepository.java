package com.gmail.popovychar.wuportal.repository;

import com.gmail.popovychar.wuportal.domain.WUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<WUser, Long> {

    WUser findUserByUsername(String username);

    WUser findUserByEmail(String email);
}
