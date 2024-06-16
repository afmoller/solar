import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Dataexportentry } from '../models/dataexportentry';
import { DateTimeAndValuesEntry } from '../models/datetimeandvaluesentry';
import { DateTimeAndValuesWatthoursEntry } from '../models/datetimeandvalueswatthoursentry';

@Injectable({
  providedIn: 'root'
})
export class DataExportEntryService {

  private dataexportentriesBaseUrl: string;

  constructor(private http: HttpClient) {
    this.dataexportentriesBaseUrl = 'http://localhost:8080/api/v1/';
  }

  public findByIid(iid: string | null | undefined): Observable<Dataexportentry> {
      return this.http.get<Dataexportentry>(this.dataexportentriesBaseUrl + 'getDataExportEntryByIID?iid=' + iid);
  }

  public getDateTimeAndValuesForTimespan(resolution: string, fromDate: string, toDate: string): Observable<DateTimeAndValuesEntry> {
    return this.http.get<DateTimeAndValuesEntry>(this.dataexportentriesBaseUrl + 'getDateTimeAndValuesForTimespan?resolution=' + resolution + '&selectedFromDate=' + fromDate + '&selectedToDate=' + toDate);
  }

  public getDateTimeAndValuesForTimespanWatthours(resolution: string, fromDate: string, toDate: string): Observable<DateTimeAndValuesWatthoursEntry> {
    return this.http.get<DateTimeAndValuesWatthoursEntry>(this.dataexportentriesBaseUrl + 'getDateTimeAndValuesForTimespan?resolution=' + resolution + '&selectedFromDate=' + fromDate + '&selectedToDate=' + toDate);
  }
}
