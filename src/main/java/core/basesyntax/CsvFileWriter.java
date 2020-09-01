package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class CsvFileWriter {
    private static final String OUTPUT_FILE_HEADER = "fruit,quantity\n";
    private final String outputFilePath;
    private StorageService storageService = new StorageService();

    public CsvFileWriter(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public void writeToFile() throws IOException {
        PrintWriter writer = new PrintWriter(new File(outputFilePath));
        StringBuilder formattedData = new StringBuilder(OUTPUT_FILE_HEADER);
        for (String fruit : storageService.getAllFruits()) {
            int fruitInstanceSum = 0;
            for (LocalDate expirationDate : storageService.getFruit(fruit).keySet()) {
                fruitInstanceSum += storageService.getExpirationDateReminder(fruit, expirationDate);
            }
            formattedData.append(fruit).append(',').append(fruitInstanceSum).append('\n');
        }
        writer.write(formattedData.toString());
        writer.close();
    }
}
