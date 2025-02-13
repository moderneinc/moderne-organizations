package io.moderne.organizations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import io.moderne.organizations.types.*;
import org.openrewrite.internal.lang.Nullable;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@DgsComponent
public class DevCenterDataFetcher {
    private final Map<String, DevCenter> devCenters;

    public DevCenterDataFetcher(ObjectMapper mapper) throws IOException {
        List<DevCenterAndOrganizations> devCenterAndOrganizationsList = mapper.readValue(
                getClass().getResourceAsStream("/devcenter.json"),
                new TypeReference<>() {
                }
        );
        this.devCenters = new HashMap<>();
        if (devCenterAndOrganizationsList != null) {
            for (DevCenterAndOrganizations devCenterAndOrganization : devCenterAndOrganizationsList) {
                for (String organization : devCenterAndOrganization.organizations) {
                    devCenters.put(organization, devCenterAndOrganization.devCenter);
                }
            }
            for (Map.Entry<String, DevCenter> devCenterEntry : this.devCenters.entrySet()) {
                DevCenter devCenter = requireNonNull(devCenterEntry.getValue());
                if (devCenter.getSecurity() != null) {
                    for (DevCenterRecipe devCenterRecipe : devCenter.getSecurity()) {
                        if (devCenterRecipe.getOptions() == null) {
                            devCenterRecipe.setOptions(List.of());
                        }
                    }
                }
                if (devCenter.getUpgradesAndMigrations() != null) {
                    for (DevCenterRecipeCard devCenterRecipeCard : devCenter.getUpgradesAndMigrations()) {
                        for (Measure measure : requireNonNull(devCenterRecipeCard.getMeasures())) {
                            if (requireNonNull(measure.getRecipe()).getOptions() == null) {
                                measure.getRecipe().setOptions(List.of());
                            }
                        }
                        if (devCenterRecipeCard.getFix() != null && requireNonNull(devCenterRecipeCard.getFix()).getOptions() == null) {
                            devCenterRecipeCard.getFix().setOptions(List.of());
                        }
                    }
                }
                if (devCenter.getVisualizations() != null) {
                    for (DevCenterVisualizationCard visualizationRequest : devCenter.getVisualizations()) {
                        if (visualizationRequest.getVisualizationOptions() == null) {
                            visualizationRequest.setVisualizationOptions(List.of());
                        }
                    }
                }
            }
        }
    }

    @DgsData(parentType = DgsConstants.ORGANIZATION.TYPE_NAME)
    @Nullable
    public DevCenter devCenter(DataFetchingEnvironment dfe) {
        Organization organization = dfe.getSource();
        return devCenters.get(organization.getId());
    }

    private record DevCenterAndOrganizations(DevCenter devCenter, List<String> organizations) {
    }

}
