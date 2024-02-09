import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ReturnOnInvestmentDashboard } from '../models/returnoninvestmentdashboard';
import { ReturnOnInvestmentCreateentry } from '../models/returnoninvestmentcreateentry';

@Injectable({
  providedIn: 'root'
})

export class ReturnOnInvestmentService {
  
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

  public create(newEntry: ReturnOnInvestmentCreateentry): void {
    this.http.post(this.returnOnInvestmentUrl, newEntry).subscribe();
  }

  public delete(id: number) {
    this.http.delete(this.returnOnInvestmentUrl + '/' + id).subscribe();
  }
}
