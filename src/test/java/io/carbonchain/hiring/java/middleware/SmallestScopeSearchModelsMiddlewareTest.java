package io.carbonchain.hiring.java.middleware;

import io.carbonchain.hiring.java.models.request.Request;
import io.carbonchain.hiring.java.models.request.SmallestScopeSearchRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SmallestScopeSearchModelsMiddlewareTest {

    SmallestScopeSearchModelsMiddleware middleware = new SmallestScopeSearchModelsMiddleware();

    @Test()
    public void testHandle_ThreeParameters_ReturnsRequest() {
        // Arrange
        Request request = new Request(new String[] {"one", "two", "three"});

        // Act
        Request actual = middleware.handle(request);

        // Assert
        Assertions.assertEquals(request, actual);
    }

    @Test()
    public void testHandle_TwoParameters_ReturnsSmallestScopeSearchRequest() {
        // Arrange
        String parameter1 = "one";
        String parameter2 = "two";
        Request request = new Request(new String[] { parameter1, parameter2 });

        // Act
        Request actual = middleware.handle(request);

        // Assert
        Assertions.assertTrue(actual instanceof SmallestScopeSearchRequest);
        Assertions.assertEquals(((SmallestScopeSearchRequest) actual).getCommodity(), parameter1);
        Assertions.assertEquals(((SmallestScopeSearchRequest) actual).getAsset(), parameter2);
    }

    @Test()
    public void testHandle_OneParameter_ReturnsRequest() {
        // Arrange
        Request request = new Request(new String[] { "one" });

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

}
