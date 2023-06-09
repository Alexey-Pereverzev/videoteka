package ru.gb.authorizationservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.gb.authorizationservice.config.CustomUserDetailsService;
import ru.gb.authorizationservice.entities.User;
import ru.gb.authorizationservice.exceptions.ResourceNotFoundException;
import ru.gb.authorizationservice.repositories.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromJwt(request);
            if (token != null && jwtTokenUtil.validateJwtToken(token).equals("")) {

                String username = findUsernameById(Long.valueOf(jwtTokenUtil.getUserIdFromToken(token)));

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception ignored) {
//                    catch (Exception e) {
        }
        filterChain.doFilter(request, response);
    }

    public String findUsernameById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Польователь с id="+id+" не найден"));
        if (user.isDeleted()) {
            throw new ResourceNotFoundException("Польователь с id="+id+" был удален");
        }
        return user.getUsername();
    }

    private String getTokenFromJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
