package io.moderne.organizations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RepositoryMapper repositoryMapper() {
        return new RepositoryMapper.OriginBasedRepositoryMapper(
                "github.com",
                "bitbucket.org",
                "gitlab.com",
                "bitbucket.example.com/stash/scm"
        );
    }
}
