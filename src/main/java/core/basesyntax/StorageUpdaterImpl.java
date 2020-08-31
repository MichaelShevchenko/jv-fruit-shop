package core.basesyntax;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StorageUpdaterImpl implements StorageUpdater {
    private Storage storageService = new Storage();

    public void parseDataToStorage(List<Transaction> newData) {
        for (Transaction newTransaction : newData) {
            putLineToStorage(newTransaction);
        }
    }

    public void putLineToStorage(Transaction newTransaction) {
        if (newTransaction.getOperationType().equals("b")) {
            updateStorageAfterPurchase(newTransaction);
            return;
        }
        storageService.setTransaction(newTransaction);
    }

    private void updateStorageAfterPurchase(Transaction newTransaction) {
        String fruitType = newTransaction.getName();
        int fruitAmount = newTransaction.getQuantity();
        LocalDate date = newTransaction.getDate();

        if (storageService.isFruitAbsent(fruitType)) {
            return;
        }
        Map<LocalDate, Integer> fruitReminders = new TreeMap<>(storageService.getFruit(fruitType));
        int neededToSell = fruitAmount;
        for (LocalDate toCompare : fruitReminders.keySet()) {
            int expirationDateReminder = storageService
                    .getExpirationDateReminder(fruitType, toCompare);
            if (date.isAfter(toCompare)) {
                continue;
            }
            if (expirationDateReminder > neededToSell) {
                newTransaction.setQuantity(neededToSell);
                newTransaction.setDate(toCompare);
                storageService.setTransaction(newTransaction);
                return;
            } else {
                neededToSell = neededToSell - expirationDateReminder;
                storageService.removeExpirationDateFruitReminder(fruitType, toCompare);
            }
        }
    }
}
