package moller.solar.solarbackend.dto;

import java.time.LocalDateTime;

public class CsvImportResultDto {

    private Integer numberOfImportedEntries;
    private LocalDateTime timestampOfFirstEntry = LocalDateTime.MAX;
    private LocalDateTime timestampOfLastEntry = LocalDateTime.MIN;

    public Integer getNumberOfImportedEntries() {
        return numberOfImportedEntries;
    }

    public void setNumberOfImportedEntries(Integer numberOfImportedEntries) {
        this.numberOfImportedEntries = numberOfImportedEntries;
    }

    public LocalDateTime getTimestampOfFirstEntry() {
        return timestampOfFirstEntry;
    }

    public void setTimestampOfFirstEntry(LocalDateTime timestampOfFirstEntry) {
        this.timestampOfFirstEntry = timestampOfFirstEntry;
    }

    public LocalDateTime getTimestampOfLastEntry() {
        return timestampOfLastEntry;
    }

    public void setTimestampOfLastEntry(LocalDateTime timestampOfLastEntry) {
        this.timestampOfLastEntry = timestampOfLastEntry;
    }
}
