import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ReturnOnInvestmentDashboard } from '../models/returnoninvestmentdashboard';
import { EnergySaleCompensationentry } from '../models/energysalecompensationentry';
import { EnergySaleCompensationCreateentry } from '../models/energysalecompensationcreateentry';

@Injectable({
  providedIn: 'root'
})

export class EnergySaleCompensationService {
    
  private energySaleCompensationsUrl: string;
  private energySaleCompensationsBaseUrl: string;
  private returnOnInvestmentDashboardUrl: string;

  constructor(private http: HttpClient) {
    this.energySaleCompensationsBaseUrl = environment.backendApiHost + '/api/v1';
    this.energySaleCompensationsUrl = this.energySaleCompensationsBaseUrl + '/energy-sale-compensations';
    this.returnOnInvestmentDashboardUrl = this.energySaleCompensationsBaseUrl + '/return-on-investment-dashboard';
  }

  public find(): Observable<ReturnOnInvestmentDashboard> {
    return this.http.get<ReturnOnInvestmentDashboard>(this.returnOnInvestmentDashboardUrl);
  }

  public getAll(): Observable<EnergySaleCompensationentry[]> {
    return this.http.get<EnergySaleCompensationentry[]>(this.energySaleCompensationsUrl);
  }

  public create(newEntry: EnergySaleCompensationCreateentry): Observable<Object> {
    return this.http.post(this.energySaleCompensationsUrl, newEntry);
  }

  public update(existingEntry: EnergySaleCompensationentry): Observable<Object> {
    return this.http.put(this.energySaleCompensationsUrl, existingEntry);
  }

  public delete(id: number): Observable<Object>  {
    return this.http.delete(this.energySaleCompensationsUrl + '/' + id);
  }

  public get(id: number): Observable<EnergySaleCompensationentry> {
    return this.http.get<EnergySaleCompensationentry>(this.energySaleCompensationsUrl + '/' + id)
  }
}
