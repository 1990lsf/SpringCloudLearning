package com.springframework.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/3/23
 * @see WebController
 * @since 1.0
 */
@RestController
@RequestMapping("/foo")
public class WebController {

    @GetMapping("/read")
    public String readFoo(){
        return "readFoo "+ UUID.randomUUID().toString();
    }


    @PreAuthorize("hasAuthority('FOO_WRITE')")
    @PostMapping("/write")
    public String writeFoo(){
        return "write foo " + UUID.randomUUID().toString();
    }

}
