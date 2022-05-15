package twenty2.auth.api;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import java.security.Security;

@SpringBootApplication
@ComponentScan( basePackages = { "twenty2.auth.api", "twenty2.auth.shared", "twenty2.auth.security",
        "twenty2.core.api.springframework" } )
@EntityScan( "twenty2.auth.api.entities" )
public class Startup {
    public static void main(String[] args) {
        Security.addProvider( new BouncyCastleProvider() );
        SpringApplication.run(Startup.class, args);
    }
}
