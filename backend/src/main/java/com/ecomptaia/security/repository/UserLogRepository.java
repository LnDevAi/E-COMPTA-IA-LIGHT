package com.ecomptaia.security.repository;

import com.ecomptaia.security.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
    List<UserLog> findByUsername(String username);
}
