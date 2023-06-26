package io.carbonchain.hiring.java.controller;

import io.carbonchain.hiring.java.domain.Asset;
import io.carbonchain.hiring.java.domain.AssetRepository;
import io.carbonchain.hiring.java.domain.Model;
import io.carbonchain.hiring.java.domain.ModelRepository;
import io.carbonchain.hiring.java.exception.ApplicationException;
import io.carbonchain.hiring.java.models.request.Request;
import io.carbonchain.hiring.java.models.request.SearchRequest;
import io.carbonchain.hiring.java.models.request.SmallestScopeSearchRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModelsControllerTest {

    AssetRepository assetRepository = new AssetRepository(new Asset[]{
            new Asset("Khetri", "India", "Asia"),
            new Asset("Tara", "Ireland", "Europe"),
    });

    ModelRepository modelRepository = new ModelRepository(new Model[]{
            new Model("Copper", "India", 18.223),
            new Model("Zinc", null, 5.33),
    });

    final ModelsController controller = new ModelsController(assetRepository, modelRepository);

    @Test()
    public void testHandle_HandlesSmallestScopeSearchRequest_ReturnsSmallestScope() throws ApplicationException {
        // Arrange
        SmallestScopeSearchRequest request = new SmallestScopeSearchRequest("Copper", "Khetri");

        // Act
        String actual = controller.handle(request);

        // Assert
        Assertions.assertEquals("India emission intensity for Copper is 18.223", actual);
    }

    @Test()
    public void testHandle_HandlesSmallestScopeSearchRequest_ReturnsGlobalScope() throws ApplicationException {
        // Arrange
        SmallestScopeSearchRequest request = new SmallestScopeSearchRequest("Zinc", "Tara");

        // Act
        String actual = controller.handle(request);

        // Assert
        Assertions.assertEquals("Global emission intensity for Zinc is 5.33", actual);
    }

    @Test()
    public void testHandle_HandlesSearchRequest_ThrowsApplicationException() throws ApplicationException {
        // Arrange
        SearchRequest request = new SearchRequest("Zinc");

        // Act and Assert
        Assertions.assertThrows(ApplicationException.class, () -> {
            controller.handle(request);
        });
    }

    @Test()
    public void testHandle_HandlesRequest_ThrowsApplicationException() throws ApplicationException {
        // Arrange
        Request request = new Request(new String[]{"models search Copper"});

        // Act and Assert
        Assertions.assertThrows(ApplicationException.class, () -> {
            controller.handle(request);
        });
    }
}
