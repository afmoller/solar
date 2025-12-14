import { Observable } from 'rxjs';
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Weatherentry } from '../models/weatherentry';

@Injectable({
  providedIn: 'root'
})

export class WeatherService {

  private http = inject(HttpClient);

  private weatherUrl: string;
  private weatherBaseUrl: string;

  constructor() {
    this.weatherBaseUrl = environment.backendApiHost + '/api/v1';
    this.weatherUrl = this.weatherBaseUrl + '/weather';
  }

  public getCurrentWeather(): Observable<Weatherentry> {
    return this.http.get<Weatherentry>(this.weatherUrl + '/current')
  }
}
