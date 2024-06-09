package Rakuten.RakutenTest1;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class task1ReadCSVFile {

	public static void main(String[] args) throws IOException {

		// Calling the method which contains the Logics for getting the Unique Dog Breed
		// name
		Set<String> uniqueBreedColumnData = GetUniqueColumnValuesByName("Breed");
		for (String uniqueBreedName : uniqueBreedColumnData) {
			// on every iteration 'uniqueBreedName' is removing any white spaces and
			// converting to lower case
			String formatedBreedNames = uniqueBreedName.replaceAll("\\s", "").toLowerCase();
			// Print the 'formatedBreedNames' in the console logs
			System.out.println("Breed: " + formatedBreedNames);
		}

	}

	// Declared the CSV File path as static final so that it remain constant and
	// unchanged
	private static final String CSVFilePath = System.getProperty("user.dir") + "\\data\\rakutenCSV.csv";

	// Function to read the Breed column Header and its Column values
	public static Set<String> GetUniqueColumnValuesByName(String columnHeaderName) {
		// Using HashSet java collections to store only unique values and eliminate
		// duplicate if found
		Set<String> uniqueData = new HashSet<>();
		try (
				// FileReader is used to open and read the content of the CSV file
				FileReader cscFilereader = new FileReader(CSVFilePath);

				// It is a class provided by Apache Commons API. It is used to parsing the CSV
				// file.
				// CSVParser reads the content of a CSV file and parses it into individual
				// records (rows) and fields (columns)
				CSVParser csvParser = new CSVParser(cscFilereader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
			//using for each loop to add the values to HashSet.
			for (CSVRecord csvRecord : csvParser) {
				String columnValue = csvRecord.get(columnHeaderName);
				uniqueData.add(columnValue);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uniqueData;
	}

}
