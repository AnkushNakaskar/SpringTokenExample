package com.token.ankush.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

public class SpringTokenUtitlity {

    private static SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomUserToken(){
        byte[] randomFgp = new byte[50];
        secureRandom.nextBytes(randomFgp);
        String userFingerprint = DatatypeConverter.printHexBinary(randomFgp);
        return userFingerprint;
    }

    public static String generateCookiesForresponse(HttpServletResponse response,String userFingerprint){
        String fingerprintCookie = "__Secure-Fgp=" + userFingerprint + "; SameSite=Strict; HttpOnly; Secure";
        response.addHeader("Set-Cookie", fingerprintCookie);
        return userFingerprint;
    }

    public static String generateMessageDigest(String userFingerprint){

        String userFingerprintHash =null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] userFingerprintDigest = digest.digest(userFingerprint.getBytes("utf-8"));
            userFingerprintHash = DatatypeConverter.printHexBinary(userFingerprintDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return userFingerprintHash;
    }

    public static String generateJWTToken(String userFingerprintHash,String email){
        String jwtToken="";

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        c.add(Calendar.MINUTE, 15);
        Date expirationDate = c.getTime();
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("typ", "JWT");

        jwtToken =Jwts.builder().setSubject(email).setExpiration(expirationDate).setIssuedAt(new Date()).claim("userFingerprint", userFingerprintHash).setHeader(headerClaims).signWith(SignatureAlgorithm.HS256, "secretkey").compact();
//        jwtToken = Jwts.builder().setSubject(email).claim("roles", "user").setIssuedAt(new Date())
//                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
        return jwtToken;
    }

    public static String getUserPrintFromCookie(HttpServletRequest request){
        String userFingerprint = null;
        if (request.getCookies() != null && request.getCookies().length > 0) {
            List<Cookie> cookies = Arrays.stream(request.getCookies()).collect(Collectors.toList());
            Optional<Cookie> cookie = cookies.stream().filter(c -> "__Secure-Fgp".equals(c.getName())).findFirst();
            if (cookie.isPresent()) {
                userFingerprint = cookie.get().getValue();
            }
        }
        return userFingerprint;
    }
}
