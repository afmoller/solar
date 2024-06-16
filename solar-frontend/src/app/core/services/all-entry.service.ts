import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Allentry } from '../models/allentry';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AllEntryService {

  private allentriesUrlDay: string;
  private allentriesUrlMonth: string;

  constructor(private http: HttpClient) {
    this.allentriesUrlDay = 'http://localhost:8080/api/v1/getAllValuesForPeriod';
    this.allentriesUrlMonth ='http://localhost:8080/api/v1/getMonthlyAccumulatedValuesForPeriod';
  }

  public findAll(resolution: String, fromDate: string, toDate: string): Observable<Allentry> {

    let service: string;

    if (resolution === 'DAY') {
      service = this.allentriesUrlDay;
    } else if (resolution === 'MONTH') {
      service = this.allentriesUrlMonth;
    } else {
      service = this.allentriesUrlMonth;
    }
    
    return this.http.get<Allentry>(service + '?selectedFromDate=' + fromDate + '&selectedToDate=' + toDate);
  }
}
