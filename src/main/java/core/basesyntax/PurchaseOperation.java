package core.basesyntax;

public class PurchaseOperation implements StoreOperations {
    private StorageService storageService = new StorageService();

    @Override
    public void executeOperation(Transaction newTransaction) {
        storageService.decrementStorage(newTransaction);
    }
}
