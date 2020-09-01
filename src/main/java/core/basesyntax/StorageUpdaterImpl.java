package core.basesyntax;

import java.util.List;

public class StorageUpdaterImpl implements StorageUpdater {
    private OperationFactory produceTransaction = new OperationFactory();

    public void parseDataToStorage(List<Transaction> newData) {
        for (Transaction newTransaction : newData) {
            StoreOperations transactionOperation = produceTransaction
                    .getSuitableOperation(newTransaction);
            transactionOperation.executeOperation(newTransaction);
        }
    }
}
