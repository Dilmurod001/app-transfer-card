package uz.pdp.apptransfercard.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apptransfercard.pyload.LoginDTO;
import uz.pdp.apptransfercard.security.JwtProvider;
import uz.pdp.apptransfercard.servic.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final
    JwtProvider jwtProvider;
    final
    AuthService authService;
    final
    AuthenticationManager authenticationManager;
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public HttpEntity<?> add(@RequestBody LoginDTO loginDTO) {
        try {
             authService.loadUserByUsername(loginDTO.getUsername());
            UsernamePasswordAuthenticationToken checkOut = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            authenticationManager.authenticate(checkOut);
            String tokencha = jwtProvider.jwtProvider(loginDTO.getUsername());
            return ResponseEntity.ok(tokencha);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Parol yoki login hato");
        }


    }
}
