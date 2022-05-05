package auth.api;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import java.security.Security;

@SpringBootApplication
@ComponentScan( basePackages = { "auth.api", "auth.shared" } )
@EntityScan( "auth.api.entities" )
public class Startup {
    public static void main(String[] args) {
        Security.addProvider( new BouncyCastleProvider() );
        SpringApplication.run(Startup.class, args);
    }
}
