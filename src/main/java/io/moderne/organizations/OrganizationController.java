package io.moderne.organizations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.*;

import static io.moderne.organizations.DevCenterDataFetcher.parseDevCenters;

@RestController
public class OrganizationController {
    private static final String DEV_CENTER_JSON = "/devcenter.json";
    OrganizationStructureService organizationStructureService;
    private final Map<String, DevCenterDataFetcher.DevCenterAndOrganizations> devCenters;

    public OrganizationController(OrganizationStructureService organizationStructureService, ObjectMapper mapper) {
        this.organizationStructureService = organizationStructureService;
        List<DevCenterDataFetcher.DevCenterAndOrganizations> devCenterAndOrganizations = parseDevCenters(DevCenterDataFetcher.class.getResourceAsStream(DEV_CENTER_JSON), mapper);
        devCenters = new LinkedHashMap<>();

        for (DevCenterDataFetcher.DevCenterAndOrganizations devCenterAndOrganization : devCenterAndOrganizations) {
            for (String organization : devCenterAndOrganization.organizations()) {
                devCenters.put(organization, devCenterAndOrganization);
            }
        }
    }

    @GetMapping("/organizations")
    Flux<DataBuffer> getOrganizations(ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        response.getHeaders().add("Content-Disposition", "attachment; filename=repos.csv");

        return DataBufferUtils.readInputStream(() -> organizationStructureService.loadReposCsvInputStream(), new DefaultDataBufferFactory(), 4096);
    }

    @GetMapping("/devcenters")
    public Flux<DataBuffer> retrieveDevCenter(ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().add("Content-Disposition", "attachment; filename=devcenters.json");
        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory(false);
        return DataBufferUtils.read(new ClassPathResource("devcenter.json"), dataBufferFactory, 4096);
    }
}
