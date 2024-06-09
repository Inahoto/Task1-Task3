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

		// Calling the method 
		Set<String> uniqueBreedColumnData = GetUniqueColumnValuesByName("Breed");
		for (String uniqueBreedName : uniqueBreedColumnData) {
			System.out.println("Breed: " + uniqueBreedName);
		}

	}
	
	
	//Declared the CSV File path as static final so that it remain constant and unchanged
	private static final String CSVFilePath = System.getProperty("user.dir") + "\\data\\rakutenCSV.csv";
	//Function to read the Breed column value 
	public static Set<String> GetUniqueColumnValuesByName(String columnHeaderName) {
		//Using HashSet java collections to store only unique values and eliminate duplicate if found
		Set<String> uniqueData = new HashSet<>();
		try (
				// opens the csv file for reading
				FileReader cscFilereader = new FileReader(CSVFilePath);

				// Parses the CSV file and allows iteration over records
				CSVParser csvParser = new CSVParser(cscFilereader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
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
