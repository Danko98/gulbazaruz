package uz.gullbozor.gullbozor.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.gullbozor.gullbozor.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {


    private static final long expireTime = 1000 * 60 * 24;
    private static final String secretKey = "xechkimgaAytmansasadlfsrghrbgdddddaaassssskjdngdrgndbgtdbgrdg";


    public String generateToken(String username, Set<Role> roles) {
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
    }

    public String getUser(String token){
        try {
            String username = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return username;


        }catch (Exception e) {
            return null;
        }

    }


}
