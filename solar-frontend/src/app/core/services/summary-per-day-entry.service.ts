import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Summaryperdayentry } from '../models/summaryperdayentry';

@Injectable({
  providedIn: 'root'
})
export class SummaryPerDayEntryService {

  private summaryperdayentriesBaseUrl: string;

  constructor(private http: HttpClient) {
    this.summaryperdayentriesBaseUrl = 'http://localhost:8080/api/v1/';
  }

  public findAll(): Observable<Summaryperdayentry[]> {
    return this.http.get<Summaryperdayentry[]>(this.summaryperdayentriesBaseUrl + 'getAll');
  }

  public findNewestEntry(): Observable<Summaryperdayentry> {
    return this.http.get<Summaryperdayentry>(this.summaryperdayentriesBaseUrl + 'getNewestEntry');
  }
}
