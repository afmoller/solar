import { Observable } from 'rxjs';
import { Injectable, inject } from '@angular/core';
import { Allentry } from '../models/allentry';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AccumulatedAllEntryService {

  private accumulatedallentriesUrl: string;

  private http = inject(HttpClient);

  constructor() {
    this.accumulatedallentriesUrl = environment.backendApiHost + '/api/v1/getAllAccumulatedValues';
  }

  public findAll(): Observable<Allentry> {
    return this.http.get<Allentry>(this.accumulatedallentriesUrl);
  }
}
