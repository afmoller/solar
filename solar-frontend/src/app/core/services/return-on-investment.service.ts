import { Observable } from 'rxjs';
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ReturnOnInvestmentDashboard } from '../models/returnoninvestmentdashboard';
import { ReturnOnInvestmentEntry } from '../models/returnoninvestmententry';

@Injectable({
  providedIn: 'root'
})

export class ReturnOnInvestmentService {

  private http = inject(HttpClient);

  private returnOnInvestmentUrl: string;
  private returnOnInvestmentBaseUrl: string;
  private returnOnInvestmentDashboardUrl: string;

  constructor() {
    this.returnOnInvestmentBaseUrl = environment.backendApiHost + '/api/v1';
    this.returnOnInvestmentUrl = this.returnOnInvestmentBaseUrl + '/return-on-investments';
    this.returnOnInvestmentDashboardUrl = this.returnOnInvestmentBaseUrl + '/return-on-investment-dashboard';
  }

  public find(): Observable<ReturnOnInvestmentDashboard> {
    return this.http.get<ReturnOnInvestmentDashboard>(this.returnOnInvestmentDashboardUrl);
  }

  public create(newEntry: ReturnOnInvestmentEntry): Observable<Object> {
    return this.http.post(this.returnOnInvestmentUrl, newEntry);
  }

  public update(existingEntry: ReturnOnInvestmentEntry): Observable<Object> {
    return this.http.put(this.returnOnInvestmentUrl, existingEntry);
  }

  public delete(id: number): Observable<Object>  {
    return this.http.delete(this.returnOnInvestmentUrl + '/' + id);
  }

  public get(id: number): Observable<ReturnOnInvestmentEntry> {
    return this.http.get<ReturnOnInvestmentEntry>(this.returnOnInvestmentUrl + '/' + id)
  }
}
