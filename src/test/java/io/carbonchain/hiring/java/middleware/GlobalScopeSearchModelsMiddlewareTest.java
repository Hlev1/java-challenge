package io.carbonchain.hiring.java.middleware;

import io.carbonchain.hiring.java.models.request.GlobalScopeSearchRequest;
import io.carbonchain.hiring.java.models.request.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GlobalScopeSearchModelsMiddlewareTest {

    GlobalScopeSearchModelsMiddleware middleware = new GlobalScopeSearchModelsMiddleware();

    @Test()
    public void testHandle_MultipleParameters_ReturnsRequest() {
        // Arrange
        Request request = new Request(new String[] {"one", "two"});

        // Act
        Request actual = middleware.handle(request);

        // Assert
        Assertions.assertEquals(request, actual);
    }

    @Test()
    public void testHandle_ZeroParameters_ReturnsRequest() {
        // Arrange
        Request request = new Request(new String[] {});

        // Act
        Request actual = middleware.handle(request);

        // Assert
        Assertions.assertEquals(request, actual);
    }

    @Test()
    public void testHandle_OneParameter_ReturnsGlobalScopeSearchRequest() {
        // Arrange
        String parameter = "one";
        Request request = new Request(new String[] { parameter });

        // Act
        Request actual = middleware.handle(request);

        // Assert
        Assertions.assertTrue(actual instanceof GlobalScopeSearchRequest);
        Assertions.assertEquals(((GlobalScopeSearchRequest) actual).getCommodity(), parameter);
    }
}
