package io.carbonchain.hiring.java.controller;

import io.carbonchain.hiring.java.domain.AssetRepository;
import io.carbonchain.hiring.java.domain.ModelRepository;
import io.carbonchain.hiring.java.exception.ApplicationException;
import io.carbonchain.hiring.java.handler.RequestHandler;
import io.carbonchain.hiring.java.models.request.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ModelsControllerTest {

    RequestHandler requestHandler = Mockito.mock(RequestHandler.class);

    RequestHandler[] requestHandlers = new RequestHandler[] {
        requestHandler
    };

    ModelsController controller = new ModelsController(requestHandlers);

    @Test()
    public void testHandle_HandlerCanHandleRequest_InvokesHandler() throws ApplicationException {
        // Arrange
        Request request = new Request(new String[]{});
        Mockito.when(requestHandler.canHandle(request)).thenReturn(true);

        // Act
        controller.handle(request);

        // Assert
        Mockito.verify(requestHandler).handle(request);
    }

    @Test()
    public void testHandle_HandlerCannotHandleRequest_ThrowsApplicationException() {
        // Arrange
        Request request = new Request(new String[]{});
        Mockito.when(requestHandler.canHandle(request)).thenReturn(false);

        // Act and Assert
        Assertions.assertThrows(ApplicationException.class, () -> {
            controller.handle(request);
        });
        Mockito.verify(requestHandler, Mockito.never()).handle(request);
    }
}
