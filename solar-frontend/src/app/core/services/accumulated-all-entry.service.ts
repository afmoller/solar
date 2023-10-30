import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Accumulatedallentry } from '../models/accumulatedallentry';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccumulatedAllEntryService {

  private accumulatedallentriesUrl: string;

  constructor(private http: HttpClient) {
    this.accumulatedallentriesUrl = 'http://localhost:8080/getAllAccumulatedValues';
  }

  public findAll(): Observable<Accumulatedallentry> {
    return this.http.get<Accumulatedallentry>(this.accumulatedallentriesUrl);
  }
}
