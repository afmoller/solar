import { Component, OnInit, ViewChild, inject } from '@angular/core';
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import Annotation from 'chartjs-plugin-annotation';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { WeatherAllEntryService } from 'src/app/core/services/weather-all-entry.service';

@Component({
    selector: 'app-weather-all',
    templateUrl: './weather-all.component.html',
    styleUrls: ['./weather-all.component.scss'],
    imports: [
        BaseChartDirective,
        ReactiveFormsModule,
        MatSidenavModule,
        MatToolbarModule,
        MatIconModule
    ]
})

export class WeatherAllComponent implements OnInit {

  private formBuilder = inject(FormBuilder);
  private weatherAllEntryService = inject(WeatherAllEntryService);
  
  inputForm: FormGroup;
  menuTitle: string = '';
  menuTitlePrefix: string = '';
  events: string[] = [];
  opened: boolean = true;

  lineChartDataVisible: boolean = true;
  solarAndHumidityLineChartDataVisible: boolean = true;
  baroLineChartDataVisible: boolean = true;

  constructor() {

    Chart.register(Annotation);
    Chart.register(Colors);

    this.inputForm = this.formBuilder.group({
      selectedDate: ''
    });
  }

  ngOnInit() {
      let dateAsString: string = this.getDateAsString(new Date());

      this.inputForm.get('selectedDate')?.setValue(dateAsString);
      
      this.loadDataAndPopulateChart(dateAsString);
  }

  public selectedDateChange(): void {
    let selectedDateValue: string = this.inputForm.get('selectedDate')?.value;
    
    this.loadDataAndPopulateChart(selectedDateValue);
  }
  
  private loadDataAndPopulateChart(selectedDate: string): void {
    
      this.weatherAllEntryService.findAll(selectedDate).subscribe(data => {
        this.lineChartData.datasets[0].data = data.dailyRainInMillimetersValues;
        this.lineChartData.datasets[1].data = data.hourlyRainInMillimetersValues;
        this.lineChartData.datasets[2].data = data.maxDailyGustInMeterPerSecondValues;
        this.lineChartData.datasets[3].data = data.rainRateInMillimetersPerHourValues;
        this.lineChartData.datasets[4].data = data.temperatureIndoorInCelsiusValues;
        this.lineChartData.datasets[5].data = data.temperatureOutdoorInCelsiusValues;
        this.lineChartData.datasets[6].data = data.uvValues;
        this.lineChartData.datasets[7].data = data.windSpeedInMeterPerSecondValues;

        this.lineChartData.labels = data.minToMaxLocalTimes;

        
        this.solarAndHumidityLineChartData.datasets[0].data = data.solarRadiationValues;
        this.solarAndHumidityLineChartData.datasets[1].data = data.humidityIndoorValues;
        this.solarAndHumidityLineChartData.datasets[2].data = data.humidityOutdoorValues;

        this.solarAndHumidityLineChartData.labels = data.minToMaxLocalTimes;

        this.baroLineChartData.datasets[0].data = data.baromAbsoluteIndoorValues;
        this.baroLineChartData.labels = data.minToMaxLocalTimes;

        this.menuTitle = selectedDate;

        this.lineChartData.datasets[0].label = 'Rain daily mm';
        this.lineChartData.datasets[1].label = 'Rain hourly mm';
        this.lineChartData.datasets[2].label = 'Max daily gust m/s';
        this.lineChartData.datasets[3].label = 'Rain rate mm/h';
        this.lineChartData.datasets[4].label = 'Temperature indoor C';
        this.lineChartData.datasets[5].label = 'Temperature outdoor C';
        this.lineChartData.datasets[6].label = 'UV index';
        this.lineChartData.datasets[7].label = 'Wind speed m/s';

        
        this.solarAndHumidityLineChartData.datasets[0].label = 'Solar radiation w/m2';
        this.solarAndHumidityLineChartData.datasets[1].label = 'Humidity indoor %';
        this.solarAndHumidityLineChartData.datasets[2].label = 'Humidity outdoor %';

        this.baroLineChartData.datasets[0].label = 'Barometer absolute hPa';

        if (this.chart) {
          if(this.chart.options) {
            if (this.chart.options.plugins) {
              if(this.chart.options.plugins.title) {
                this.chart.options.plugins.title.text = this.menuTitle;
              }
            }
          }
        }

        this.chart?.update();
        this.chart?.render();
      });
    
    

    this.chart?.update();
    this.chart?.render();
  }

  public baroLineChartData: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        fill: false,
      }
    ],
    labels: [],
  };

  public solarAndHumidityLineChartData: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        fill: false,
      },
      {
        data: [],
        fill: false,
      },
      {
        data: [],
        fill: false,
      }
    ],
    labels: [],
  };

  public lineChartData: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        fill: false,
      },
      {
        data: [],
        fill: false,
      },
      {
        data: [],
        fill: false,
      },
      {
        data: [],
        fill: false,
      },
      {
        data: [],
        fill: false,
      },
      {
        data: [],
        fill: false,
      },
      {
        data: [],
        fill: false,
      },
      {
        data: [],
        fill: false,
      }
    ],
    labels: [],
  };

  public lineChartOptions: ChartConfiguration['options'] = {
    elements: {
      line: {
        tension: 0.1,
      },
      point: {
        radius: 1,
      }
    },
    scales: {
      // We use this empty structure as a placeholder for dynamic theming.
      y: {
        position: 'left',
        ticks: {
          // Disabled rotation for performance
          maxRotation: 0,
        },
      },
    },

    plugins: {
      legend: {
        display: true,
        position: 'bottom'
      },
      title: {
        display: true,
        text: ''
      }
    },
    responsive: true,
    maintainAspectRatio: false,
    animation: false,
  };

  public lineChartType: ChartType = 'line';

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  // events
  public chartClicked(source: string): void {
    if (source === 'lineChartData') {
      this.solarAndHumidityLineChartDataVisible = this.opened ? false: true;
      this.baroLineChartDataVisible = this.opened ? false: true;
    } else if (source === 'solarAndHumidityLineChartData') {
      this.lineChartDataVisible = this.opened ? false: true;
      this.baroLineChartDataVisible = this.opened ? false: true;
    } else {
      this.lineChartDataVisible = this.opened ? false: true;
      this.solarAndHumidityLineChartDataVisible = this.opened ? false: true;
    }
  }

  public chartHovered({
    event,
    active,
  }: {
    event?: ChartEvent;
    active?: object[];
  }): void {

  }

  private getDateAsString(date: Date): string {
    return date.getFullYear() + '-' + this.zeroPadIfNecessary(date.getMonth() + 1) + '-' + this.zeroPadIfNecessary(date.getDate());
  }

  private zeroPadIfNecessary(value: number): string {
    return value < 10 ? '0' + value : value + '';
  }
}
