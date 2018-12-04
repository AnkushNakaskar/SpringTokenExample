package com.token.ankush.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.token.ankush.utility.SpringTokenUtitlity;
import io.jsonwebtoken.JwtParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtFilter extends GenericFilterBean {

    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("authorization");

        String userPrintFromCookie = SpringTokenUtitlity.getUserPrintFromCookie(request);
        String userFingerprintHash = SpringTokenUtitlity.generateMessageDigest(userPrintFromCookie);
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(req, res);
        } else {

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ServletException("Missing or invalid Authorization header");
            }

            final String token = authHeader.substring(7);

            try {
                JwtParser parser = Jwts.parser();
                final Claims claims = parser.setSigningKey("secretkey").parseClaimsJws(token).getBody();
                String claimKey = claims.get("userFingerprint", String.class);
                if(StringUtils.equalsIgnoreCase(userFingerprintHash,claimKey)){
                    request.setAttribute("claims", claims);
                }else{
                    throw new ServletException("invalid claim for  userFingerprint");
                }

            } catch (final SignatureException e) {
                throw new ServletException("Invalid token");
            }

            chain.doFilter(req, res);
        }
    }
}
