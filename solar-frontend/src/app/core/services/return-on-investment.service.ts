import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ReturnOnInvestmententry } from '../models/returnoninvestmententry';

@Injectable({
  providedIn: 'root'
})

export class ReturnOnInvestmentService {

  private returnOnInvestmentUrl: string;

  constructor(private http: HttpClient) {
    this.returnOnInvestmentUrl = 'http://localhost:8080/return-on-investments';
  }

  public findAll(): Observable<ReturnOnInvestmententry[]> {
    return this.http.get<ReturnOnInvestmententry[]>(this.returnOnInvestmentUrl);
  }
}
