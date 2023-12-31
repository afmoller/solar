import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Allentry } from '../models/allentry';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AllEntryService {

  private allentriesUrl: string;

  constructor(private http: HttpClient) {
    this.allentriesUrl = 'http://localhost:8080/getAllValuesForPeriod';
  }

  public findAll(period: String, fromDate: string, toDate: string): Observable<Allentry> {
    return this.http.get<Allentry>(this.allentriesUrl + '?period=' + period + '&selectedFromDate=' + fromDate + '&selectedToDate=' + toDate);
  }
}
