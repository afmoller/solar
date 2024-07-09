import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Dataexportentry } from '../models/dataexportentry';
import { DateTimeAndValuesEntry } from '../models/datetimeandvaluesentry';
import { DateTimeAndValuesWatthoursEntry } from '../models/datetimeandvalueswatthoursentry';

@Injectable({
  providedIn: 'root'
})
export class CsvService {

  private csvBaseUrl: string;

  constructor(private http: HttpClient) {
    this.csvBaseUrl = 'http://localhost:8080/api/v1/';
  }

  // public import(resolution: string, fromDate: string, toDate: string): Observable<DateTimeAndValuesEntry> {
  //   this.http.post<DateTimeAndValuesEntry>(this.csvBaseUrl + 'importCsvFileToDatabase');
  // }

}
