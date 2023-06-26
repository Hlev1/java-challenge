package io.carbonchain.hiring.java.handler;

import io.carbonchain.hiring.java.domain.Model;
import io.carbonchain.hiring.java.domain.ModelRepository;
import io.carbonchain.hiring.java.models.request.GlobalScopeSearchRequest;
import io.carbonchain.hiring.java.models.request.Request;
import io.carbonchain.hiring.java.models.request.SearchRequest;
import io.carbonchain.hiring.java.models.request.SmallestScopeSearchRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GlobalScopeRequestHandlerTest {

    ModelRepository modelRepository = new ModelRepository(new Model[] {
            new Model("Zinc", null, 5.33),
            new Model("Zinc", "USA", 3.45),
    });
    GlobalScopeRequestHandler handler = new GlobalScopeRequestHandler(modelRepository);

    @Test()
    public void testCanHandle_SmallestScopeSearchRequest_ReturnsFalse() {
        // Arrange
        SmallestScopeSearchRequest request = Mockito.mock(SmallestScopeSearchRequest.class);

        // Act
        boolean actual = handler.canHandle(request);

        // Assert
        Assertions.assertFalse(actual);
    }

    @Test()
    public void testCanHandle_GlobalScopeSearchRequest_ReturnsTrue() {
        // Arrange
        GlobalScopeSearchRequest request = Mockito.mock(GlobalScopeSearchRequest.class);

        // Act
        boolean actual = handler.canHandle(request);

        // Assert
        Assertions.assertTrue(actual);
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
    public void testHandle_HandlesGlobalScopeSearchRequest_ReturnsGlobalScope() {
        // Arrange
        GlobalScopeSearchRequest request = new GlobalScopeSearchRequest("Zinc");

        // Act
        String actual = handler.handle(request);

        // Assert
        Assertions.assertEquals("Global emission intensity for Zinc is 5.33", actual);
    }
}
