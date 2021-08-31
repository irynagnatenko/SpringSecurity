package se.nackademin.backend2.user;

import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.nackademin.backend2.user.security.JWTIssuer;
import se.nackademin.backend2.user.application.SignupService;
import se.nackademin.backend2.user.presistance.InMemoryUserRepository;
import se.nackademin.backend2.user.domain.User;
import se.nackademin.backend2.user.domain.UserRepository;

import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.util.List;

@Configuration
public class AppConfiguration {

    @Value("${security.signingKey}")
    private String signingKey;

    @Value("${security.algorithm}")
    private String algorithm;

    @Value("${security.validMinutes}")
    private Integer validMinutes;


    @Bean
    public UserRepository userRepository(BCryptPasswordEncoder passwordEncoder) {
        UserRepository userRepository = new InMemoryUserRepository();
        userRepository.save(new User("admin", passwordEncoder.encode("password"), List.of("ADMIN")));
        return userRepository;
    }

    @Bean
    public SignupService signupService(UserRepository userRepository) {
        SignupService signupService = new SignupService(userRepository);
        return signupService;
    }

    @Bean
    public JWTIssuer jwtIssuer() {
        final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(algorithm);
        final byte[] signingKeyBytes = Base64.encodeBase64(signingKey.getBytes());
        return new JWTIssuer(new SecretKeySpec(signingKeyBytes, signatureAlgorithm.getJcaName()), Duration.ofMinutes(validMinutes));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
