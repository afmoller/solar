import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Dataexportentry } from '../models/dataexportentry';
import { Observable } from 'rxjs';
import { DateTimeAndValuesEntry } from '../models/datetimeandvaluesentry';

@Injectable({
  providedIn: 'root'
})
export class DataExportEntryService {

  private dataexportentriesBaseUrl: string;

  constructor(private http: HttpClient) {
    this.dataexportentriesBaseUrl = 'http://localhost:8080/';
  }

  public findByIid(iid: string | null | undefined): Observable<Dataexportentry> {
      return this.http.get<Dataexportentry>(this.dataexportentriesBaseUrl + 'getDataExportEntryByIID?iid=' + iid);
  }

  public getDateTimeAndValuesForTimespan(fromDate: string, toDate: string): Observable<DateTimeAndValuesEntry> {
    return this.http.get<DateTimeAndValuesEntry>(this.dataexportentriesBaseUrl + 'getDateTimeAndValuesForTimespan?selectedFromDate=' + fromDate + '&selectedToDate=' + toDate);
  }
}
