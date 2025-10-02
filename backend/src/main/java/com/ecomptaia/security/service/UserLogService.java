package com.ecomptaia.security.service;

import com.ecomptaia.security.entity.UserLog;
import com.ecomptaia.security.repository.UserLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLogService {
    private final UserLogRepository userLogRepository;

    public void log(String username, String action) {
        UserLog log = new UserLog();
        log.setUsername(username);
        log.setAction(action);
        log.setDate(LocalDateTime.now());
        userLogRepository.save(log);
    }

    public List<UserLog> getLogs(String username) {
        return userLogRepository.findByUsername(username);
    }
}
