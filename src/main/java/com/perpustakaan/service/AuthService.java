package com.perpustakaan.service;

import com.perpustakaan.dto.auth.AuthResponse;
import com.perpustakaan.dto.auth.LoginRequest;
import com.perpustakaan.dto.auth.RegisterRequest;
import com.perpustakaan.entity.Pengguna;
import com.perpustakaan.exception.AuthenticationFailedException;
import com.perpustakaan.exception.InvalidOperationException;
import com.perpustakaan.repository.PenggunaRepository;
import com.perpustakaan.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PenggunaRepository penggunaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(PenggunaRepository penggunaRepository,
                        PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager,
                        JwtUtil jwtUtil) {
        this.penggunaRepository = penggunaRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {

        if (penggunaRepository.existsByUsername(request.getUsername())) {
            throw new InvalidOperationException("Username sudah dipakai, silakan pilih username lain");
        }

        Pengguna pengguna = new Pengguna();
        pengguna.setUsername(request.getUsername());
        pengguna.setPassword(passwordEncoder.encode(request.getPassword()));
        pengguna.setRole(request.getRole());

        penggunaRepository.save(pengguna);

        String token = jwtUtil.generateToken(pengguna.getUsername(), pengguna.getRole());

        return new AuthResponse(pengguna.getUsername(), pengguna.getRole(), token);
    }

    public AuthResponse login(LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new AuthenticationFailedException("Username atau password salah");
        }

        Pengguna pengguna = penggunaRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationFailedException("Username atau password salah"));

        String token = jwtUtil.generateToken(pengguna.getUsername(), pengguna.getRole());

        return new AuthResponse(pengguna.getUsername(), pengguna.getRole(), token);
    }
}