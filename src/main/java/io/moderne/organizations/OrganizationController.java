package io.moderne.organizations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.*;

import static io.moderne.organizations.DevCenterDataFetcher.parseDevCenters;

@RestController
public class OrganizationController {
    private static final String DEV_CENTER_JSON = "/devcenter.json";
    OrganizationStructureService organizationStructureService;
    private final Map<String, DevCenterDataFetcher.DevCenterAndOrganizations> devCenters;

    public OrganizationController(OrganizationStructureService organizationStructureService) {
        this.organizationStructureService = organizationStructureService;
        List<DevCenterDataFetcher.DevCenterAndOrganizations> devCenterAndOrganizations = parseDevCenters(DevCenterDataFetcher.class.getResourceAsStream(DEV_CENTER_JSON), new ObjectMapper());
        devCenters = new HashMap<>();

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

    @GetMapping("/commit-options")
    Flux<DataBuffer> getCommitOptions(ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        response.getHeaders().add("Content-Disposition", "attachment; filename=commitOptions.txt");

        return DataBufferUtils.readInputStream(() -> organizationStructureService.loadCommitOptionsInputStream(), new DefaultDataBufferFactory(), 4096);
    }

    @GetMapping("/id-mapping")
    Flux<DataBuffer> getIdMapping(ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        response.getHeaders().add("Content-Disposition", "attachment; filename=id-mapping.txt");
        return DataBufferUtils.readInputStream(OrganizationStructureService::loadNameMappingInputStream, new DefaultDataBufferFactory(), 4096);
    }

    @PostMapping("/devcenters")
    public Flux<DevCenterDataFetcher.DevCenterAndOrganizations> retrieveDevCenter(@RequestBody List<String> organizationIds
    ) {
        Set<DevCenterDataFetcher.DevCenterAndOrganizations> requestedDevCenters = new LinkedHashSet<>();
        for (String organizationId : organizationIds) {
            DevCenterDataFetcher.DevCenterAndOrganizations devCenter = devCenters.get(organizationId);
            if (devCenter != null) {
                requestedDevCenters.add(devCenter);
            }
        }

        return Flux.fromIterable(requestedDevCenters);
    }
}
