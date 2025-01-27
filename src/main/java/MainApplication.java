import java.util.List;

public class MainApplication {
    public static void main(String[] args) {
        try {
            // Step 1: Ingest data
            DataIngestion dataIngestion = new DataIngestion();
            List<CSVRecord> csvData = dataIngestion.readCSV("data.csv");

            // Step 2: Preprocess data
            DataPreprocessor preprocessor = new DataPreprocessor();
            List<List<String>> cleanedData = preprocessor.handleMissingValues(convertCSVToList(csvData), "0");
            cleanedData = preprocessor.removeOutliers(cleanedData, 4, 1000.0); // Example: Remove outliers in column 4
            cleanedData = preprocessor.normalizeData(cleanedData, 4); // Example: Normalize column 4

            // Step 3: Store data
            DataStorage dataStorage = new DataStorage();
            dataStorage.storeInRelationalDB("jdbc:mysql://localhost:3306/mydb", "user", "password", "stocks", cleanedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to convert CSVRecord to List<List<String>>
    private static List<List<String>> convertCSVToList(List<CSVRecord> csvRecords) {
        List<List<String>> data = new ArrayList<>();
        for (CSVRecord record : csvRecords) {
            List<String> row = new ArrayList<>();
            for (String value : record) {
                row.add(value);
            }
            data.add(row);
        }
        return data;
    }
}