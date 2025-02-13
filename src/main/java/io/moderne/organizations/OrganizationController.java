package io.moderne.organizations;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class OrganizationController {
    private static final String DEV_CENTER_JSON = "/devcenter.json";
    OrganizationStructureService organizationStructureService;

    public OrganizationController(OrganizationStructureService organizationStructureService) {
        this.organizationStructureService = organizationStructureService;
    }

    @GetMapping("/organizations")
    Flux<DataBuffer> getOrganizations(ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        response.getHeaders().add("Content-Disposition", "attachment; filename=repos.csv");

        return DataBufferUtils.readInputStream(() -> organizationStructureService.getReposCsvInputStream(), new DefaultDataBufferFactory(), 4096);
    }

    @GetMapping("/commit-options")
    Flux<DataBuffer> getCommitOptions(ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        response.getHeaders().add("Content-Disposition", "attachment; filename=commitOptions.txt");

        return DataBufferUtils.readInputStream(() -> organizationStructureService.getCommitOptionsInputStream(), new DefaultDataBufferFactory(), 4096);
    }

    @GetMapping("/id-mapping")
    Flux<DataBuffer> getIdMapping(ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        response.getHeaders().add("Content-Disposition", "attachment; filename=id-mapping.txt");
        return DataBufferUtils.readInputStream(OrganizationStructureService::getNameMappingInputStream, new DefaultDataBufferFactory(), 4096);
    }

    @GetMapping("/devcenter")
    Flux<DataBuffer> getDevCenter(ServerHttpResponse response) throws IOException {
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        response.getHeaders().add("Content-Disposition", "attachment; filename=devcenter.json");
        InputStream inputStream = new ClassPathResource(DEV_CENTER_JSON).getInputStream();
        return DataBufferUtils.readInputStream(() -> inputStream, new DefaultDataBufferFactory(), 4096);
    }
}
