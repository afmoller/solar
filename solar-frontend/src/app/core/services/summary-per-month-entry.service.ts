import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SummaryPerMonthEntryService {

  private summarypermonthentriesUrl: string;

  constructor(private http: HttpClient) {
    this.summarypermonthentriesUrl = environment.backendApiHost + '/api/v1/getMonthlyAccumulatedValues';
  }

  public findAll(): Observable<SummaryPerMonthEntryService[]> {
    return this.http.get<SummaryPerMonthEntryService[]>(this.summarypermonthentriesUrl);
  }
}
