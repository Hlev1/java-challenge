package io.carbonchain.hiring.java.handler;

import io.carbonchain.hiring.java.domain.Asset;
import io.carbonchain.hiring.java.domain.AssetRepository;
import io.carbonchain.hiring.java.domain.Model;
import io.carbonchain.hiring.java.domain.ModelRepository;
import io.carbonchain.hiring.java.models.request.GlobalScopeSearchRequest;
import io.carbonchain.hiring.java.models.request.Request;
import io.carbonchain.hiring.java.models.request.SearchRequest;
import io.carbonchain.hiring.java.models.request.SmallestScopeSearchRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

public class SmallestScopeSearchRequestHandlerTest {

    GlobalScopeRequestHandler globalScopeRequestHandler = Mockito.mock(GlobalScopeRequestHandler.class);

    AssetRepository assetRepository = new AssetRepository(new Asset[]{
            new Asset("Khetri", "India", "Asia"),
            new Asset("Cerro Verde", "Peru", "South America"),
            new Asset("El Abra", "Chile", "South America"),
            new Asset("Red Dog", "USA", "North America"),
            new Asset("Antamina", "Peru", "South America"),
            new Asset("Tara", "Ireland", "Europe"),
    });
    ModelRepository modelRepository = new ModelRepository(new Model[] {
            new Model("Zinc", null, 5.33),
            new Model("Copper", "India", 18.223),
    });

    SmallestScopeSearchRequestHandler handler =
            new SmallestScopeSearchRequestHandler(assetRepository, modelRepository, globalScopeRequestHandler);

    @Test()
    public void testCanHandle_SmallestScopeSearchRequest_ReturnsTrue() {
        // Arrange
        SmallestScopeSearchRequest request = Mockito.mock(SmallestScopeSearchRequest.class);

        // Act
        boolean actual = handler.canHandle(request);

        // Assert
        Assertions.assertTrue(actual);
    }

    @Test()
    public void testCanHandle_GlobalScopeSearchRequest_ReturnsFalse() {
        // Arrange
        GlobalScopeSearchRequest request = Mockito.mock(GlobalScopeSearchRequest.class);

        // Act
        boolean actual = handler.canHandle(request);

        // Assert
        Assertions.assertFalse(actual);
    }

    @Test()
    public void testCanHandle_SearchRequest_ReturnsFalse() {
        // Arrange
        SearchRequest request = Mockito.mock(SearchRequest.class);

        // Act
        boolean actual = handler.canHandle(request);

        // Assert
        Assertions.assertFalse(actual);
    }

    @Test()
    public void testCanHandle_Request_ReturnsFalse() {
        // Arrange
        Request request = Mockito.mock(Request.class);

        // Act
        boolean actual = handler.canHandle(request);

        // Assert
        Assertions.assertFalse(actual);
    }

    @Test()
    public void testHandle_HandlesSmallestScopeSearchRequest_ReturnsSmallestScope() {
        // Arrange
        SmallestScopeSearchRequest request = new SmallestScopeSearchRequest("Copper", "Khetri");

        // Act
        String actual = handler.handle(request);

        // Assert
        Assertions.assertEquals("India emission intensity for Copper is 18.223", actual);
    }

    @Test()
    public void testHandle_HandlesSmallestScopeSearchRequest_ReturnsGlobalScope() {
        // Arrange
        Mockito.when(globalScopeRequestHandler.handle(any())).thenReturn("globalEmission");
        SmallestScopeSearchRequest request = new SmallestScopeSearchRequest("Zinc", "Khetri");

        // Act
        String actual = handler.handle(request);

        // Assert
        Assertions.assertEquals(globalScopeRequestHandler.handle(request), actual);
    }

    @Test()
    public void testHandle_AssetNotFound_ReturnsGlobalScopeWithoutCallingModelRepository() {
        // Arrange
        Mockito.when(globalScopeRequestHandler.handle(any())).thenReturn("globalEmission");
        ModelRepository modelRepository = Mockito.mock(ModelRepository.class);
        SmallestScopeSearchRequest request = new SmallestScopeSearchRequest("Zinc", "NotFound");
        SmallestScopeSearchRequestHandler handler =
                new SmallestScopeSearchRequestHandler(assetRepository, modelRepository, globalScopeRequestHandler);

        // Act
        String actual = handler.handle(request);

        // Assert
        Assertions.assertEquals(globalScopeRequestHandler.handle(request), actual);
        Mockito.verify(modelRepository, Mockito.never()).findAllByCommodity(any());
    }
}
