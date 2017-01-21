package com.balinasoft.firsttask.service.security;


import com.balinasoft.firsttask.util.StringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {
    @Value("${project.security.access-token.length}")
    private int tokenLength;

    @Value("${project.security.access-token.symbols}")
    private char[] symbols;

    public String generateAccessToken() {
        return StringGenerator.generate(tokenLength, symbols);
    }

}
