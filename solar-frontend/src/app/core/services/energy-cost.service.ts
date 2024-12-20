import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EnergyCostentry } from '../models/energycostentry';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})

export class EnergyCostService {

  private energyCostUrl: string;
  private energyCostBaseUrl: string;
  
  constructor(private http: HttpClient) {
    this.energyCostBaseUrl = environment.backendApiHost + '/api/v1';
    this.energyCostUrl = this.energyCostBaseUrl + '/energy-costs';
  }

  public getAll(): Observable<EnergyCostentry[]> {
    return this.http.get<EnergyCostentry[]>(this.energyCostUrl);
  }

  public create(newEntry: EnergyCostentry): Observable<Object> {
    return this.http.post(this.energyCostUrl, newEntry);
  }

  public update(existingEntry: EnergyCostentry): Observable<Object> {
    return this.http.put(this.energyCostUrl, existingEntry);
  }

  public delete(id: number): Observable<Object>  {
    return this.http.delete(this.energyCostUrl + '/' + id);
  }

  public get(id: number): Observable<EnergyCostentry> {
    return this.http.get<EnergyCostentry>(this.energyCostUrl + '/' + id)
  }
}
