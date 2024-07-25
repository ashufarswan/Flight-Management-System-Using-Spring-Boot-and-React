package com.api.filter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class Jwtutil {
	    //requirement :
	    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	    //    public static final long JWT_TOKEN_VALIDITY =  60;
	    private String secret = "c9a8ea2db5f9207bec1b04b0984d2d74c68ddc00ad2cdb2cff2ed08992234dd5e6346b74519573ad9cf26862131ab68ea0ebe2cd6e0ac28112431ee43841db8de3b62b04d68195beaa4dcedd45e266f71c6ff5f9ed2f4f01236c5a13918fb420d55257dd70b0ea539735921b84a145c29c67ffd52f1a71e5beaa002833a6600c51194593c84fcd081a87dadf6d73586c6dd40e409dfdb84e75a69f8b5b778304aa5ce6d15dcdcd60f452be7932868db863f2fc4253d50b315072e8e7311d6473deb0046b75a05589b84e33f0d0ccf96bd947a2120025fc25cea38af34d3486f313e81e804ec827a05e6e1a4cd6002de609629ec7a1e607a1cd52e99046518a706415e6cc3c2b736d9f713b412699573a1649a0e254e839fffeda1007459daf3e6ef10bdf0d2cb8219dc5ad2760fd8644612840bda72c0d872b2369b470b38eccd369fa2ca741be1ac74e87bf1a0d11fae71fa0fbab4ef7d5ed38d3c5330e94a7bcb1d4f074d54f3037c1b01e98773021530071d2832ebe625e300000f214788a1756a80f0d6aff7b89b78246cd51104a3e8640f51a0514c11d717e222c252b6effb8c218db4d5313a8c891a36bb986229943973f0d6ea187596cdc7e3b0e60049d8154eb564b2c39558d2d2141b0ba57feb00f4fb8e88ab7b2fd2e933e9e537d0de10b689a5d8dad7da95cb5f4f454720610c40b65b9a43d17b285023c20da2e";

	    //retrieve username from jwt token
	    public String getUsernameFromToken(String token) {
	        return getClaimFromToken(token, Claims::getSubject);
	    }

	    //retrieve expiration date from jwt token
	    public Date getExpirationDateFromToken(String token) {
	        return getClaimFromToken(token, Claims::getExpiration);
	    }

	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }

	    //for retrieveing any information from token we will need the secret key
	    private Claims getAllClaimsFromToken(String token) {
	         /*return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();*/
	         return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))).build().parseClaimsJws(token).getBody();
	    	
	    }	

	    //check if the token has expired
	    private Boolean isTokenExpired(String token) {
	        final Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }

	    //generate token for user
	    public String generateToken(String userName,String role,String id,String email) {
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("role",role);
	        claims.put("id",userName);
	        claims.put("email", email);
	        claims.put("id", id);
	        return doGenerateToken(claims, userName);
	    }

	    //while creating the token -
	    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	    //2. Sign the JWT using the HS512 algorithm and secret key.
	    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	    //   compaction of the JWT to a URL-safe string
	    private String doGenerateToken(Map<String, Object> claims, String subject) {

	        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
	                .signWith( Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)),SignatureAlgorithm.HS512).compact();
	    }

	    //validate token
	    public void validateToken(String token) {
	        try {
	        	Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))).build().parseClaimsJws(token);
	        }
	        catch(JwtException e) {
	        	throw e;
	        }
	    }
	    
	    public String extractRole(String token) {
	    	 try {
	             Claims claims = Jwts.parserBuilder()
	                 .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
	                 .build()
	                 .parseClaimsJws(token)
	                 .getBody();
	             String role = (String) claims.get("role");
	             return role;
	         } catch (JwtException e) {
	             throw e;
	         }
	    }
}
