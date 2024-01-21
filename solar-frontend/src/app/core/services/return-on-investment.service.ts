import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ReturnOnInvestmentDashboard } from '../models/returnoninvestmentdashboard';

@Injectable({
  providedIn: 'root'
})

export class ReturnOnInvestmentService {

  private returnOnInvestmentDashboardUrl: string;

  constructor(private http: HttpClient) {
    this.returnOnInvestmentDashboardUrl = 'http://localhost:8080/return-on-investment-dashboard';
  }

  public find(): Observable<ReturnOnInvestmentDashboard> {
    return this.http.get<ReturnOnInvestmentDashboard>(this.returnOnInvestmentDashboardUrl);
  }
}
