package com.esipe.msbasket.resource;

import com.esipe.msbasket.domain.Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public abstract class AuthorizedResource {

    private final String VERY_USER_URL = "http://ms-membership:8070/v1/esipe/users/verify";
    private final String TOKEN_HEADER = "Bearer ";
    private final String AUTH_ADMIN = "admin";


    String[] checkTokenValidity(String bearerToken) {
        if (bearerToken == null) {
            throw new RuntimeException("Error: token is empty");
        }


        bearerToken = bearerToken.replace(TOKEN_HEADER, "");

        Token token = new Token(bearerToken);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Token> httpEntity = new HttpEntity<>(token, new HttpHeaders());

        ResponseEntity<String[]> response = restTemplate.exchange(VERY_USER_URL, HttpMethod.POST, httpEntity, String[].class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error: could not verify user");
        }

        return response.getBody();
    }

    void authentifyAdmin(String[] authorities) {
        if (!Arrays.stream(authorities).anyMatch(authority -> {
            return authority.equalsIgnoreCase(AUTH_ADMIN);
        })) {
            throw new RuntimeException("Error: should be authenticated as an admin");
        }
    }
}
