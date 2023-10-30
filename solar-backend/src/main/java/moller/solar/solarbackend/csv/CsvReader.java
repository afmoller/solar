package moller.solar.solarbackend.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;

@Component
public class CsvReader {

    public Iterable<CSVRecord> parseFile( Reader in) {

        try {
            CSVFormat csvFormat = CSVFormat.Builder.create()
                    .setHeader()
                    .setDelimiter(";")
                    .setAllowMissingColumnNames(true)
                    .build();

            return csvFormat.parse(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
