import { Observable } from 'rxjs';
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Dataexportentry } from '../models/dataexportentry';
import { environment } from '../../../environments/environment';
import { DateTimeAndValuesEntry } from '../models/datetimeandvaluesentry';
import { DateTimeAndValuesWatthoursEntry } from '../models/datetimeandvalueswatthoursentry';

@Injectable({
  providedIn: 'root'
})
export class DataExportEntryService {

  private http = inject(HttpClient);

  private dataexportentriesBaseUrl: string;

  constructor() {
    this.dataexportentriesBaseUrl = environment.backendApiHost + '/api/v1/';
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
