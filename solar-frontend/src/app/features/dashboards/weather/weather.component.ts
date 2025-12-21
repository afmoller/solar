import { Weatherentry } from 'src/app/core/models/weatherentry';
import { WeatherService } from "src/app/core/services/weather.service";
import { WeatherTableentry } from 'src/app/core/dto/weathertableentry';

import {
  inject,
  OnInit,
  Component
} from '@angular/core';

import {
  MatTableModule,
  MatTableDataSource
} from '@angular/material/table'

@Component({
    selector: 'app-weather',
    templateUrl: './weather.component.html',
    styleUrls: ['./weather.component.scss'],
    imports: [
      MatTableModule
    ]
})

export class WeatherComponent implements OnInit {

  dataSourceMiscEntries = new MatTableDataSource();
  dataSourceWindEntries = new MatTableDataSource();
  dataSourceRainEntries = new MatTableDataSource();
  dataSourceIndoorEntries = new MatTableDataSource();
  dataSourceOutdoorEntries = new MatTableDataSource();
  
  displayedColumns: string[] = ['measurement',
                                'value'
                               ];

  weatherEntry: Weatherentry = {} as Weatherentry;
  
  private weatherService = inject(WeatherService);

  ngOnInit() {
    this.loadEnergyCostData();
  }

  loadEnergyCostData() {
    this.weatherService.getCurrentWeather().subscribe(data => {
      this.weatherEntry = data;
      
      this.dataSourceMiscEntries.data = this.getMiscEntries(data);
      this.dataSourceWindEntries.data = this.getWindEntries(data);
      this.dataSourceRainEntries.data = this.getRainEntries(data);
      this.dataSourceIndoorEntries.data = this.getIndoorEntries(data);
      this.dataSourceOutdoorEntries.data = this.getOutdoorEntries(data);
    })
  }
  
  getIndoorEntries(data: Weatherentry): WeatherTableentry[] {
    let entries = new Array<WeatherTableentry>();
    
    entries.push({key: 'Temperture', value: data.temperatureIndoorInCelcius.toFixed(1) + '°C'});
    entries.push({key: 'Humidity', value: data.humidityIndoor.toFixed(0) + '%'});
    entries.push({key: 'Barometer Rel', value: data.baromRelativeIndoor.toFixed(0) + ' hPa'});
    entries.push({key: 'Barometer Abs', value: data.baromAbsoluteIndoor.toFixed(0) + ' hPa'});
    
    return entries;
  }

  getOutdoorEntries(data: Weatherentry): WeatherTableentry[] {
    let entries = new Array<WeatherTableentry>();
    
    entries.push({key: 'Temperture', value: data.temperatureOutdoorInCelsius.toFixed(1) + '°C'});
    entries.push({key: 'Humidity', value: data.humidityOutdoor.toFixed(0) + '%'});
    entries.push({key: 'Solarradiation', value: data.solarRadiation.toFixed(1) + ' w/m2'});
    entries.push({key: 'UV Index', value: data.uv.toFixed(0)});
    
    return entries;
  }

   getRainEntries(data: Weatherentry): WeatherTableentry[] {
    let entries = new Array<WeatherTableentry>();
    
    entries.push({key: 'Rate', value: data.rainRateInMillimetersPerHour.toFixed(1) + ' mm/h'});
    entries.push({key: 'Event', value: data.eventRainInMillimeters.toFixed(1) + ' mm'});
    entries.push({key: 'Hourly', value: data.hourlyRainInMillimeters.toFixed(1) + ' mm'});
    entries.push({key: 'Daily', value: data.dailyRainInMillimeters.toFixed(1) + ' mm'});
    entries.push({key: 'Weekly', value: data.weeklyRainInMillimeters.toFixed(1) + ' mm'});
    entries.push({key: 'Monthly', value: data.monthlyRainInMillimeters.toFixed(1) + ' mm'});
    entries.push({key: 'Yearly', value: data.yearlyRainInMillimeters.toFixed(0) + ' mm'});
    
    return entries;
  }

  getWindEntries(data: Weatherentry): WeatherTableentry[] {
    let entries = new Array<WeatherTableentry>();
    
    entries.push({key: 'Wind speed', value: data.windSpeedInMeterPerSecond.toFixed(1) + ' m/s'});
    entries.push({key: 'Wind Direction', value: data.windDirection.toFixed(0) + '°'});
    entries.push({key: 'Gust', value: data.windGustInMeterPerSecond.toFixed(1) + ' m/s'});
    entries.push({key: 'Max Daily Gust', value: data.maxDailyGustInMeterPerSecond.toFixed(1) + ' m/s'});
    
    return entries;
  }

  getMiscEntries(data: Weatherentry): WeatherTableentry[] {
    let entries = new Array<WeatherTableentry>();
    
    entries.push({key: 'Frequency', value: data.freq + ' Hz'});
    entries.push({key: 'Heap', value: data.heap.toFixed(0) + ' B'});
    entries.push({key: 'Interval', value: data.interval.toFixed(0) + 's'});
    entries.push({key: 'Model', value: data.model});
    entries.push({key: 'Runtime', value: data.runtime.toFixed(0) + ' s (' + this.toHumanReadable(data.runtime) + ')'});
    entries.push({key: 'Station type', value: data.stationType});
    entries.push({key: 'wh25 Batt', value: data.wh25Batt.toFixed(0)});
    entries.push({key: 'wh65 Batt', value: data.wh65Batt.toFixed(0)});
    
    return entries;
  }

  toHumanReadable(seconds: number): string {
    seconds = seconds || 0;
    seconds = Number(seconds);
    seconds = Math.abs(seconds);

    const d = Math.floor(seconds / (3600 * 24));
    const h = Math.floor(seconds % (3600 * 24) / 3600);
    const m = Math.floor(seconds % 3600 / 60);
    const s = Math.floor(seconds % 60);
    const parts = [];

    if (d > 0) {
      parts.push(d + ' d');
    }

    if (h > 0) {
      parts.push(h + ' h');
    }

    if (m > 0) {
      parts.push(m + ' m');
    }

    if (s > 0) {
      parts.push(s + ' s');
    }

    return parts.join(', ');
  }
}


