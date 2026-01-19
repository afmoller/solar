import { Observable } from 'rxjs';
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { WeatherAllentry } from '../models/weatherallentry';

@Injectable({
  providedIn: 'root'
})
export class WeatherAllEntryService {

  private http = inject(HttpClient);

  private service: string;
  
  private baseUrl: string = environment.backendApiHost;

  constructor() {
    this.service = this.baseUrl + '/api/v1/weather/weatherbydate';
  }

  public findAll(selectedDate: string): Observable<WeatherAllentry> {
    return this.http.get<WeatherAllentry>(this.service + '?selectedDate=' + selectedDate);
  }
}
