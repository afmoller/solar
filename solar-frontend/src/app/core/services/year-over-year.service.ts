import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { YearOverYearEntry } from '../models/yearoveryearentry';

@Injectable({
  providedIn: 'root'
})
export class YearOverYearEntryService {

  private yearoveryearUrl: string;

  constructor(private http: HttpClient) {
    this.yearoveryearUrl = environment.backendApiHost + '/api/v1/getAggregatedMonthValues';
  }

  public findAll(valueType: string): Observable<YearOverYearEntry> {
    return this.http.get<YearOverYearEntry>(this.yearoveryearUrl + '?valueType=' + valueType);
  }
}
