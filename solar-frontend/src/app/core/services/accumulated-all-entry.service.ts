import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Allentry } from '../models/allentry';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccumulatedAllEntryService {

  private accumulatedallentriesUrl: string;

  constructor(private http: HttpClient) {
    this.accumulatedallentriesUrl = 'http://localhost:8080/api/v1/getAllAccumulatedValues';
  }

  public findAll(): Observable<Allentry> {
    return this.http.get<Allentry>(this.accumulatedallentriesUrl);
  }
}
