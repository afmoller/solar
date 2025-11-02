import { Observable } from 'rxjs';
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Summaryperdayentry } from '../models/summaryperdayentry';

@Injectable({
  providedIn: 'root'
})
export class SummaryPerDayEntryService {

  private http = inject(HttpClient);

  private summaryperdayentriesBaseUrl: string;

  constructor() {
    this.summaryperdayentriesBaseUrl = environment.backendApiHost + '/api/v1/';
  }

  public findAll(): Observable<Summaryperdayentry[]> {
    return this.http.get<Summaryperdayentry[]>(this.summaryperdayentriesBaseUrl + 'getAll');
  }

  public findNewestEntry(): Observable<Summaryperdayentry> {
    return this.http.get<Summaryperdayentry>(this.summaryperdayentriesBaseUrl + 'getNewestEntry');
  }

  public populateSummaryPerDayForYearAndMonth(year: number, month: number): Observable<number> {
    return this.http.put<number>(this.summaryperdayentriesBaseUrl + 'populateSummaryPerDayForYearAndMonth?year=' + year + '&month=' + month, {});
  }
}
