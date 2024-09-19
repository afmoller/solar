import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { Allentry } from '../models/allentry';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AccumulatedAllEntryService {

  private accumulatedallentriesUrl: string;

  constructor(private http: HttpClient) {
    this.accumulatedallentriesUrl = environment.backendApiHost + '/api/v1/getAllAccumulatedValues';
  }

  public findAll(): Observable<Allentry> {
    return this.http.get<Allentry>(this.accumulatedallentriesUrl);
  }
}
