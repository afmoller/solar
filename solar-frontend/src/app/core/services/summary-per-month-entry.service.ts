import { Observable } from 'rxjs';
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Summarypermonthentry } from '../models/summarypermonthentry';

@Injectable({
  providedIn: 'root'
})
export class SummaryPerMonthEntryService {

  private http = inject(HttpClient);

  private summarypermonthentriesUrl: string;

  constructor() {
    this.summarypermonthentriesUrl = environment.backendApiHost + '/api/v1/getMonthlyAccumulatedValues';
  }

  public findAll(): Observable<Summarypermonthentry[]> {
    return this.http.get<Summarypermonthentry[]>(this.summarypermonthentriesUrl);
  }
}
