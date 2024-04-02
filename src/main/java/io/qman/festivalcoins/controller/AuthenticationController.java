package io.qman.festivalcoins.controller;


import com.fasterxml.jackson.databind.node.ObjectNode;
import io.qman.festivalcoins.APIConfig;
import io.qman.festivalcoins.entity.Account;
import io.qman.festivalcoins.exceptions.NotAcceptableException;
import io.qman.festivalcoins.repository.AccountRepository;
import io.qman.festivalcoins.util.JWToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AccountRepository accountsRepo;
    APIConfig apiConfig;

    @Autowired
    public AuthenticationController(APIConfig apiConfig, AccountRepository accountsRepo) {
        this.apiConfig = apiConfig;
        this.accountsRepo = accountsRepo;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Account> authenticateAccount(
            @RequestBody ObjectNode signInInfo,
            HttpServletRequest request) {

        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        // check whether we need and have the source IP-address of the user
        String ipAddress = JWToken.getIpAddress(request);
        if (ipAddress == null) {
            throw new NotAcceptableException("Cannot identify your source IP-Address.");
        }

        List<Account> accounts = accountsRepo.findByQuery("Accounts_find_by_email", email);
        Account account = !accounts.isEmpty() ? accounts.get(0) : null;
        System.out.println(accounts);

        if (account == null || !account.verfiyPassword(password)) {
            throw new NotAcceptableException("Cannot authenticate account with email=" + email);
        }

        // Issue a token for the account, valid for some time
        JWToken jwToken = new JWToken(account.getName(), account.getId(), account.getRole(), ipAddress);
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),
                this.apiConfig.getTokenDurationOfValidity());

        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(account);
    }
}
