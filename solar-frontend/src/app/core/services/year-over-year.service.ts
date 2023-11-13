import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { YearOverYearEntry } from '../models/yearoveryearentry';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class YearOverYearEntryService {

  private yearoveryearUrl: string;

  constructor(private http: HttpClient) {
    this.yearoveryearUrl = 'http://localhost:8080/getAggregatedMonthValues';
  }

  public findAll(valueType: string): Observable<YearOverYearEntry> {
    return this.http.get<YearOverYearEntry>(this.yearoveryearUrl + '?valueType=' + valueType);
  }
}
