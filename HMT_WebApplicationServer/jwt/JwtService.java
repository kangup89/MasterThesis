package com.kangup.dvpis.jwt;

import static java.time.ZoneOffset.UTC;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;

import com.kangup.dvpis.jwt.auth.SecretKeyProvider;
import com.kangup.dvpis.jwt.exception.JwtAuthenticationException;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Component
public class JwtService {
    private static final String ISSUER = "com.kangup.dvpis";
    private final SecretKeyProvider secretKeyProvider;
    

    /*String mongoContextPath = "/root-context.xml";	
	AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(mongoContextPath);
	MongoTemplate mongoTemplate = (MongoTemplate) ctx.getBean("mongoTemplate");*/
    
	// Set credentials      
	MongoCredential credential = MongoCredential.createCredential("kangup", "dvpis_database", "0215".toCharArray());
	ServerAddress serverAddress = new ServerAddress("ds039261.mlab.com", 39261);

	// Mongo Client
	MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential)); 

	// Mongo DB Factory
	SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(
	            mongoClient, "dvpis_database");

	MongoTemplate mongoTemplate = new MongoTemplate(simpleMongoDbFactory);

    @SuppressWarnings("unused")
    public JwtService() {
        this(null);
    }

    @Autowired
    public JwtService(SecretKeyProvider secretKeyProvider) {
        this.secretKeyProvider = secretKeyProvider;
    }

    /*public String tokenFor(MinimalProfile minimalProfile) throws IOException, URISyntaxException {
        byte[] secretKey = secretKeyProvider.getKey();
        Date expiration = Date.from(LocalDateTime.now(UTC).plusHours(2).toInstant(UTC));
        return Jwts.builder()
                .setSubject(minimalProfile.getUsername())
        		//.setSubject("user")
                .setExpiration(expiration)
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }*/
    
    public String tokenFor(String username){
    	Key key = MacProvider.generateKey();
    	System.out.println("key!: " + key);
        //byte[] secretKey = secretKeyProvider.getKey();

        Date expiration = Date.from(LocalDateTime.now(UTC).plusHours(2).toInstant(UTC));
        return Jwts.builder()
                //.setSubject(minimalProfile.getUsername())
        		.setSubject(username)
        		
                .setExpiration(expiration)
                .setIssuer(ISSUER)
                //.signWith(SignatureAlgorithm.HS256, key)
                .signWith(SignatureAlgorithm.HS256, "secret".getBytes(StandardCharsets.UTF_8))
                .compact();
    }
    
    /*String jwt = Jwts.builder()
	  .setSubject("users/TzMUocMF4p")
	  .setExpiration(new Date(1300819380))
	  .claim("name", "Robert Token Man")
	  .claim("scope", "self groups/admins")
	  .signWith(
	    SignatureAlgorithm.HS256,
	    "secret".getBytes("UTF-8")
	  )
	  .compact();*/

    /*public Optional<MinimalProfile> verify(String token) throws IOException, URISyntaxException {
        byte[] secretKey = secretKeyProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        //Jws<Claims> claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token);
        
        return profileService.minimal(claims.getBody().getSubject().toString());
    }*/
    
    public Jws<Claims> verify(String token) throws SignatureException {
    	Key key = MacProvider.generateKey();
    	if(token == null) return null;
    	else if(token.startsWith("bearer")) token = token.substring(7);
        //byte[] secretKey = secretKeyProvider.getKey();
    	try{
    		Jws<Claims> claims = Jwts.parser().setSigningKey("secret".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
    		return claims;
    	} catch (Exception e) {
            throw new JwtAuthenticationException("Failed to verify token", e);
        }
		//io.jsonwebtoken.ExpiredJwtException
    	//Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }
    
    public String getUsernameFromToken(String token) throws SignatureException{
    	Jws<Claims> claims = verify(token);
    	if(claims == null) return null;
    	String username = claims.getBody().getSubject();
    	return username;
    }
    
    /*public boolean verify(String token) {
    	try {
    		Jws<Claims> claims = Jwts.parser().setSigningKey("secret".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
    		return true;
    	} catch (Exception e) {
            throw new JwtAuthenticationException("Failed to verify token", e);
        }
    }*/
    
    /*String jwt = <jwt passed in from above>
	Jws<Claims> claims = Jwts.parser()
	  .setSigningKey("secret".getBytes("UTF-8"))
	  .parseClaimsJws(jwt)
	String scope = claims.getBody().get("scope")
	assertEquals(scope, "self groups/admins");*/
}
