package core.basesyntax;

import org.junit.Assert;
import org.junit.Test;

public class StoreOperationsTest {
    StoreOperations operationsHandler = new StoreOperations();

    @Test(expected = IllegalArgumentException.class)
    public void operationCharacterNegativeTest() {
        operationsHandler.calculate(13, 5, "o");
        operationsHandler.calculate(13, 5, "+");
    }

    @Test
    public void ProperOperationsOk() {
        Assert.assertEquals(75, operationsHandler.calculate(57, 18, "s"));
        Assert.assertEquals(88, operationsHandler.calculate(111, 23, "b"));
        Assert.assertEquals(258, operationsHandler.calculate(240, 18, "r"));
    }
}
