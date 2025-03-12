package io.moderne.organizations;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import io.moderne.organizations.OrganizationStructureService;
import io.moderne.organizations.OrganizationController;
import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

@WebFluxTest(OrganizationController.class)
public class OrganizationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private OrganizationStructureService organizationStructureService;

    // Get content and throw a runtime exception if there is an error
    private String getContent(InputStream stream) {
        try{
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void testGetOrganizations() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("repo1,repo2".getBytes());
        Mockito.when(organizationStructureService.loadReposCsvInputStream()).thenReturn(inputStream);

        webTestClient.get().uri("/organizations")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_PLAIN)
                .expectHeader().valueEquals("Content-Disposition", "attachment; filename=repos.csv")
                .expectBody(DataBuffer.class)
                .consumeWith(response -> {
                    DataBuffer buffer = response.getResponseBody();
                    String content=getContent(buffer.asInputStream());
                    assertEquals(content,"repo1,repo2");
                });
    }

    @Test
    void testGetCommitOptions() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("option1,option2".getBytes());
        Mockito.when(organizationStructureService.loadCommitOptionsInputStream()).thenReturn(inputStream);

        webTestClient.get().uri("/commit-options")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_PLAIN)
                .expectHeader().valueEquals("Content-Disposition", "attachment; filename=commitOptions.txt")
                .expectBody(DataBuffer.class)
                .consumeWith(response -> {
                    DataBuffer buffer = response.getResponseBody();
                    String content=getContent(buffer.asInputStream());
                    assertEquals(content,"option1,option2");
                });
    }

    // General tests for file download endpoints
    @ParameterizedTest(name = "Testing General File Download {index}: id={0} -> name={1}")
    @CsvSource({
        "/id-mapping,/id-mapping.txt,id-mapping.txt"
    })
    void testFileDownload(String uriPath, String classPathResourceString, String attachmentFileName) throws Exception {
        String expectedContent= getContent(new ClassPathResource(classPathResourceString).getInputStream());
        webTestClient.get().uri(uriPath)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_PLAIN)
                .expectHeader().valueEquals("Content-Disposition", "attachment; filename="+attachmentFileName)
                .expectBody(DataBuffer.class)
                .consumeWith(response -> {
                    DataBuffer buffer = response.getResponseBody();
                    String content=getContent(buffer.asInputStream());
                    assertEquals(content,expectedContent);
                });
    }

}