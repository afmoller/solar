import { Weatherentry } from 'src/app/core/models/weatherentry';
import { WeatherService } from "src/app/core/services/weather.service";

import {
  inject,
  OnInit,
  Component,
} from '@angular/core';

@Component({
    selector: 'app-weather',
    templateUrl: './weather.component.html',
    styleUrls: ['./weather.component.scss']
})

export class WeatherComponent implements OnInit {

  weatherEntry: Weatherentry = {} as Weatherentry;
  
  private weatherService = inject(WeatherService);

  ngOnInit() {
    this.loadEnergyCostData();
  }

  loadEnergyCostData() {
    this.weatherService.getCurrentWeather().subscribe(data => {
      this.weatherEntry = data;  
    })
  }

  getObjectProperties(myObject: Weatherentry | undefined): (keyof Weatherentry)[] {
    if (myObject) {
      const keys = Object.keys(myObject) as Array<keyof Weatherentry>;
      return keys;
    }

    return [];
  }
}
