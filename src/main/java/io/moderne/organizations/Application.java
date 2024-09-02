package io.moderne.organizations;

import org.openrewrite.GitRemote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(ModerneConfiguration.class)
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    GitRemote.Parser gitRemoteParser(ModerneConfiguration moderneConfiguration) {
        ScmConfiguration scmConfiguration = moderneConfiguration.getScm();
        GitRemote.Parser gitRemoteParser = new GitRemote.Parser();
        scmConfiguration.getRepositories()
                .forEach(scmRepository -> gitRemoteParser.registerRemote(
                        scmRepository.getType(),
                        scmRepository.getBaseUrl(),
                        scmRepository.getAlternativeUrls()
                ));
        return gitRemoteParser;
    }

}
