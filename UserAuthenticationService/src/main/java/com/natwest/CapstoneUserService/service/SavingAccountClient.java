package com.natwest.CapstoneUserService.service;

import com.natwest.CapstoneUserService.dto.RegisterDto;
import com.natwest.CapstoneUserService.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SavingAccountService")
public interface SavingAccountClient {
    @PostMapping("/savings/new-Account")
    public ResponseEntity<String> createAccoountNumber(@RequestBody UserDto userDto);

}
