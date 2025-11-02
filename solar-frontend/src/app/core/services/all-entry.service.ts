import { Observable } from 'rxjs';
import { Injectable, inject } from '@angular/core';
import { Allentry } from '../models/allentry';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AllEntryService {

  private http = inject(HttpClient);

  private allentriesUrlDay: string;
  private allentriesUrlMonth: string;

  private baseUrl: string = environment.backendApiHost;

  constructor() {
    this.allentriesUrlDay = this.baseUrl + '/api/v1/getAllValuesForPeriod';
    this.allentriesUrlMonth = this.baseUrl + '/api/v1/getMonthlyAccumulatedValuesForPeriod';
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
