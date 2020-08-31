package core.basesyntax;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Storage {
    private static Map<String, TreeMap<LocalDate, Integer>> fruitsInStore = new HashMap<>();

    public TreeMap<LocalDate, Integer> getFruit(String fruit) {
        return new TreeMap<>(fruitsInStore.get(fruit));
    }

    public void setTransaction(Transaction newTransaction) {
        String fruitType = newTransaction.getName();
        int fruitAmount = newTransaction.getQuantity();
        String operationType = newTransaction.getOperationType();
        LocalDate date = newTransaction.getDate();

        fruitsInStore.putIfAbsent(fruitType, new TreeMap<>());
        Storage.fruitsInStore.get(fruitType).computeIfPresent(date, (key, value)
                -> StoreOperations.calculate(value, fruitAmount, operationType));
        Storage.fruitsInStore.get(fruitType).putIfAbsent(date, fruitAmount);
    }

    public int getExpirationDateReminder(String fruit, LocalDate date) {
        return fruitsInStore.get(fruit).get(date);
    }

    public void removeExpirationDateFruitReminder(String fruitType, LocalDate date) {
        fruitsInStore.get(fruitType).remove(date);
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

    public boolean isFruitAbsent(String fruitType) {
        return !fruitsInStore.containsKey(fruitType);
    }
}
