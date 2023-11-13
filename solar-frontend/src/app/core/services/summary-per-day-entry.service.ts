import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Summaryperdayentry } from '../models/summaryperdayentry';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SummaryPerDayEntryService {

  private summaryperdayentriesUrl: string;

  constructor(private http: HttpClient) {
    this.summaryperdayentriesUrl = 'http://localhost:8080/getAll';
  }

  public findAll(): Observable<Summaryperdayentry[]> {
    return this.http.get<Summaryperdayentry[]>(this.summaryperdayentriesUrl);
  }
}
