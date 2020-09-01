package core.basesyntax;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StorageService {
    private static Map<String, TreeMap<LocalDate, Integer>> fruitsInStore = new HashMap<>();

    public TreeMap<LocalDate, Integer> getFruitReminders(String fruit) {
        return new TreeMap<>(fruitsInStore.get(fruit));
    }

    public int getExpirationDateReminder(String fruit, LocalDate date) {
        return fruitsInStore.get(fruit).get(date);
    }

    public void clearStorage() {
        fruitsInStore.clear();
    }

    public Set<String> getAllFruits() {
        return fruitsInStore.keySet();
    }

    public Map<String, TreeMap<LocalDate, Integer>> getAllData() {
        Map<String, TreeMap<LocalDate, Integer>> storageCopy = new HashMap<>();
        for (String fruit : fruitsInStore.keySet()) {
            storageCopy.put(fruit, new TreeMap<>());
            storageCopy.get(fruit).putAll(fruitsInStore.get(fruit));
        }
        return storageCopy;
    }

    public void incrementStorage(Transaction newTransaction) {
        String fruitType = newTransaction.getName();
        int fruitAmount = newTransaction.getQuantity();
        LocalDate expirationDate = newTransaction.getDate();

        if (fruitsInStore.containsKey(fruitType)
                && getFruitReminders(fruitType).containsKey(expirationDate)) {
            fruitsInStore.get(fruitType).put(expirationDate, fruitAmount
                    + getExpirationDateReminder(fruitType, expirationDate));
        } else {
            fruitsInStore.putIfAbsent(fruitType, new TreeMap<>());
            fruitsInStore.get(fruitType).put(expirationDate, fruitAmount);
        }
    }

    public void decrementStorage(Transaction newTransaction) {
        String fruitType = newTransaction.getName();
        int fruitAmount = newTransaction.getQuantity();
        LocalDate date = newTransaction.getDate();

        if (!fruitsInStore.containsKey(fruitType)) {
            return;
        }
        Map<LocalDate, Integer> fruitReminders = new TreeMap<>(getFruitReminders(fruitType));
        int neededToSell = fruitAmount;
        for (LocalDate expirationDate : fruitReminders.keySet()) {
            int expirationDateReminder = getExpirationDateReminder(fruitType, expirationDate);
            if (date.isAfter(expirationDate)) {
                continue;
            }
            if (expirationDateReminder > neededToSell) {
                fruitsInStore.get(fruitType).put(expirationDate,
                        getExpirationDateReminder(fruitType, expirationDate) - neededToSell);
                return;
            } else {
                neededToSell = neededToSell - expirationDateReminder;
                fruitsInStore.get(fruitType).remove(expirationDate);
            }
        }
    }
}
