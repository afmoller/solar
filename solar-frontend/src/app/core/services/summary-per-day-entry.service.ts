import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Summaryperdayentry } from '../models/summaryperdayentry';

@Injectable({
  providedIn: 'root'
})
export class SummaryPerDayEntryService {

  private summaryperdayentriesBaseUrl: string;

  constructor(private http: HttpClient) {
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
