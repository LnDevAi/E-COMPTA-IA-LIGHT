package com.ecomptaia.security.controller;

import com.ecomptaia.security.service.UserLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.ecomptaia.security.entity.UserLog;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserLogController {
    private final UserLogService userLogService;

    @GetMapping("/{username}")
    public List<UserLog> getLogs(@PathVariable String username) {
        return userLogService.getLogs(username);
    }
}
