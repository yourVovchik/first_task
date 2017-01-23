package com.balinasoft.firsttask.controller;

import com.balinasoft.firsttask.dto.ResponseDto;
import com.balinasoft.firsttask.dto.SignUserDtoIn;
import com.balinasoft.firsttask.dto.SignUserOutDto;
import com.balinasoft.firsttask.service.security.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

import static com.balinasoft.firsttask.system.StaticWrapper.wrap;

@RestController
@RequestMapping("/api/account")
@Api(tags = "Signin, signup")
public class SecurityController {
    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PermitAll
    @RequestMapping(value = "signup", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Sign up account", response = SignUserOutDto.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "security.signup.login-already-use"),
            @ApiResponse(code = 400, message = "validation-error")
    })
    public ResponseDto signup(@Validated @RequestBody SignUserDtoIn userDto) {
        return wrap(securityService.signup(userDto));
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Sign in account", response = SignUserOutDto.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "security.signin.incorrect"),
            @ApiResponse(code = 400, message = "validation-error")
    })
    public ResponseDto signin(@Validated @RequestBody SignUserDtoIn userDto) {
        return wrap(securityService.signin(userDto));
    }
}
