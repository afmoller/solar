import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ReturnOnInvestmentDashboard } from '../models/returnoninvestmentdashboard';
import { EnergyCostentry } from '../models/energycostentry';
import { EnergyCostCreateentry } from '../models/energycostcreateentry';

@Injectable({
  providedIn: 'root'
})

export class EnergyCostService {
  
  private energyCostUrl: string;
  private energyCostBaseUrl: string;
  private returnOnInvestmentDashboardUrl: string;

  constructor(private http: HttpClient) {
    this.energyCostBaseUrl = 'http://localhost:8080';
    this.energyCostUrl = this.energyCostBaseUrl + '/energy-costs';
    this.returnOnInvestmentDashboardUrl = this.energyCostBaseUrl + '/return-on-investment-dashboard';
  }

  public find(): Observable<ReturnOnInvestmentDashboard> {
    return this.http.get<ReturnOnInvestmentDashboard>(this.returnOnInvestmentDashboardUrl);
  }

  public getAll(): Observable<EnergyCostentry[]> {
    return this.http.get<EnergyCostentry[]>(this.energyCostUrl);
  }

  public create(newEntry: EnergyCostCreateentry): Observable<Object> {
    return this.http.post(this.energyCostUrl, newEntry);
  }

  public delete(id: number): Observable<Object>  {
    return this.http.delete(this.energyCostUrl + '/' + id);
  }
}
