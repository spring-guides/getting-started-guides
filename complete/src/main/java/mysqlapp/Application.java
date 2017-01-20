package mysqlapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses=mysqlapp.repositories.UsersRepository.class)
// This tells Spring's repository scanner to scan the package which contains the mentioned repository (ALL THE PACKAGE),here you could put the parameter 'considerNestedRepositories' if your repository is inner
@EntityScan(basePackageClasses=mysqlapp.models.User.class) // This tells Spring to scan ALL
// THE PACKAGE which CONTAINS the User class entity for entities
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
