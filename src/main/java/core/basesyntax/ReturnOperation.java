package core.basesyntax;

public class ReturnOperation implements StoreOperations {
    private StorageService storageService = new StorageService();

    @Override
    public void executeOperation(Transaction newTransaction) {
        storageService.incrementStorage(newTransaction);
    }
}
