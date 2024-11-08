import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Summarypermonthentry } from '../models/summarypermonthentry';

@Injectable({
  providedIn: 'root'
})
export class SummaryPerMonthEntryService {

  private summarypermonthentriesUrl: string;

  constructor(private http: HttpClient) {
    this.summarypermonthentriesUrl = environment.backendApiHost + '/api/v1/getMonthlyAccumulatedValues';
  }

  public findAll(): Observable<Summarypermonthentry[]> {
    return this.http.get<Summarypermonthentry[]>(this.summarypermonthentriesUrl);
  }
}
