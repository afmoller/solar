import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SummaryPerMonthEntryService {

  private summarypermonthentriesUrl: string;

  constructor(private http: HttpClient) {
    this.summarypermonthentriesUrl = 'http://localhost:8080/getMonthlyAccumulatedValues';
  }

  public findAll(): Observable<SummaryPerMonthEntryService[]> {
    return this.http.get<SummaryPerMonthEntryService[]>(this.summarypermonthentriesUrl);
  }
}
