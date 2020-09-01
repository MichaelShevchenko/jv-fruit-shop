package core.basesyntax;

import java.util.HashMap;
import java.util.Map;

public class OperationFactory {
    private static final Map<String, StoreOperations> AVAILABLE_OPERATIONS = new HashMap<>();

    static {
        AVAILABLE_OPERATIONS.put("s", new SupplyOperation());
        AVAILABLE_OPERATIONS.put("b", new PurchaseOperation());
        AVAILABLE_OPERATIONS.put("r", new ReturnOperation());
    }

    public StoreOperations getSuitableOperation(Transaction newTransaction) {
        return AVAILABLE_OPERATIONS.get(newTransaction.getOperationType());
    }

    public boolean containsOperation(String operation) {
        return AVAILABLE_OPERATIONS.containsKey(operation);
    }
}
