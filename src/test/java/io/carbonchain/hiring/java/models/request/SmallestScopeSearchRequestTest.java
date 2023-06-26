package io.carbonchain.hiring.java.models.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SmallestScopeSearchRequestTest {

    @Test
    public void testToGlobalScopeSearchRequest_CommodityTerm_IsCorrect() {
        // Arrange
        String commodity = "commodity";
        SmallestScopeSearchRequest searchRequest = new SmallestScopeSearchRequest(commodity, "asset");

        // Act
        GlobalScopeSearchRequest actual = searchRequest.toGlobalScopeSearchRequest();

        // Assert
        Assertions.assertEquals(actual.getCommodity(), commodity);
    }
}
