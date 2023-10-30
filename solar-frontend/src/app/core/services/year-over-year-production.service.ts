import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { YearOverYearProductionentry } from '../models/yearoveryearproductionentry';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class YearOverYearProductionEntryService {

  private yearoveryearproductionUrl: string;

  constructor(private http: HttpClient) {
    this.yearoveryearproductionUrl = 'http://localhost:8080/getAggregatedMonthValues';
  }

  public findAll(): Observable<YearOverYearProductionentry> {
    return this.http.get<YearOverYearProductionentry>(this.yearoveryearproductionUrl);
  }
}
