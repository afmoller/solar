import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ReturnOnInvestmentDashboard } from '../models/returnoninvestmentdashboard';
import { EnergySaleCompensationCreateentry } from '../models/energysalecompensationcreateentry';

@Injectable({
  providedIn: 'root'
})

export class EnergySaleCompensationService {
  
  private returnOnInvestmentUrl: string;
  private returnOnInvestmentBaseUrl: string;
  private returnOnInvestmentDashboardUrl: string;

  constructor(private http: HttpClient) {
    this.returnOnInvestmentBaseUrl = 'http://localhost:8080';
    this.returnOnInvestmentUrl = this.returnOnInvestmentBaseUrl + '/return-on-investments';
    this.returnOnInvestmentDashboardUrl = this.returnOnInvestmentBaseUrl + '/return-on-investment-dashboard';
  }

  public find(): Observable<ReturnOnInvestmentDashboard> {
    return this.http.get<ReturnOnInvestmentDashboard>(this.returnOnInvestmentDashboardUrl);
  }

  public create(newEntry: EnergySaleCompensationCreateentry): Observable<Object> {
    return this.http.post(this.returnOnInvestmentUrl, newEntry);
  }

  public delete(id: number): Observable<Object>  {
    return this.http.delete(this.returnOnInvestmentUrl + '/' + id);
  }
}
