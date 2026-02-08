import { Component, OnInit, QueryList, ViewChild, ViewChildren, inject } from '@angular/core';
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
  rainLineChartDataVisible: boolean = true;
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
        
        
        this.lineChartData.datasets[0].data = data.temperatureIndoorInCelsiusValues;
        this.lineChartData.datasets[1].data = data.temperatureOutdoorInCelsiusValues;
        this.lineChartData.datasets[2].data = data.uvValues;
        this.lineChartData.datasets[3].data = data.windSpeedInMeterPerSecondValues;
        this.lineChartData.datasets[4].data = data.maxDailyGustInMeterPerSecondValues;

        this.lineChartData.labels = data.minToMaxLocalTimes;

        this.rainLineChartData.datasets[0].data = data.dailyRainInMillimetersValues;
        this.rainLineChartData.datasets[1].data = data.hourlyRainInMillimetersValues;
        this.rainLineChartData.datasets[2].data = data.rainRateInMillimetersPerHourValues;

        this.rainLineChartData.labels = data.minToMaxLocalTimes;

        this.solarAndHumidityLineChartData.datasets[0].data = data.solarRadiationValues;
        this.solarAndHumidityLineChartData.datasets[1].data = data.humidityIndoorValues;
        this.solarAndHumidityLineChartData.datasets[2].data = data.humidityOutdoorValues;

        this.solarAndHumidityLineChartData.labels = data.minToMaxLocalTimes;

        this.baroLineChartData.datasets[0].data = data.baromAbsoluteIndoorValues;
        this.baroLineChartData.labels = data.minToMaxLocalTimes;

        this.menuTitle = selectedDate;

        this.lineChartData.datasets[0].label = 'Temp in C';
        this.lineChartData.datasets[1].label = 'Temp out C';
        this.lineChartData.datasets[2].label = 'UV index';
        this.lineChartData.datasets[3].label = 'Wind m/s';
        this.lineChartData.datasets[4].label = 'Max gust m/s';

        this.rainLineChartData.datasets[0].label = 'Rain daily mm';
        this.rainLineChartData.datasets[1].label = 'Rain hourly mm';
        this.rainLineChartData.datasets[2].label = 'Rain rate mm/h';

        this.solarAndHumidityLineChartData.datasets[0].label = 'Radiation w/m2';
        this.solarAndHumidityLineChartData.datasets[1].label = 'Humidity in %';
        this.solarAndHumidityLineChartData.datasets[2].label = 'Humidity out %';

        this.baroLineChartData.datasets[0].label = 'Baro. abs. hPa';

        this.charts?.forEach((child) => {
          child.chart?.update();
        });
      });
  }

  decreaseDate(dateField: string): void {
    if (dateField === 'selectedDate') {
      this.shiftDateFieldValue('selectedDate', -1);
      this.dateChange();
    }
  }

  increaseDate(dateField: string): void {
    if (dateField === 'selectedDate') {
      this.shiftDateFieldValue('selectedDate', 1);
      this.dateChange();
    }
  }

   public dateChange(): void {
    this.loadDataAndPopulateChart(this.inputForm.get('selectedDate')?.value);
  }

  private shiftDateFieldValue(dateField: string, shiftValue: number) {
      let dateValue: string = this.inputForm.get(dateField)?.value;
      let dateValueAsDate: Date = new Date(dateValue);
      dateValueAsDate.setDate(dateValueAsDate.getDate() + shiftValue);

      this.inputForm.get(dateField)?.setValue(this.getDateAsString(dateValueAsDate));  
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
      }
    ],
    labels: [],
  };


  public rainLineChartData: ChartConfiguration['data'] = {
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
        }
      },
    },

    plugins: {
      legend: {
        display: true,
        position: 'right',
        labels: {
          usePointStyle: true,
        }
      }
    },
    responsive: true,
    maintainAspectRatio: false,
    animation: false,
  };

  public rainLineChartOptions: ChartConfiguration['options'] = {
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
        min: 0
      },
    },

    plugins: {
      legend: {
        display: true,
        position: 'right',
        labels: {
          usePointStyle: true,
        }
      }
    },
    responsive: true,
    maintainAspectRatio: false,
    animation: false,
  };

  public lineChartType: ChartType = 'line';

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  @ViewChildren(BaseChartDirective) charts?: QueryList<BaseChartDirective>;

  // events
  public chartClicked(source: string): void {
    if (source === 'lineChartData') {
      this.rainLineChartDataVisible = this.opened ? false: true;
      this.solarAndHumidityLineChartDataVisible = this.opened ? false: true;
      this.baroLineChartDataVisible = this.opened ? false: true;
    } else if (source === 'rainLineChartData') {
      this.lineChartDataVisible = this.opened ? false: true;
      this.solarAndHumidityLineChartDataVisible = this.opened ? false: true;
      this.baroLineChartDataVisible = this.opened ? false: true;
    } else if (source === 'solarAndHumidityLineChartData') {
      this.rainLineChartDataVisible = this.opened ? false: true;
      this.lineChartDataVisible = this.opened ? false: true;
      this.baroLineChartDataVisible = this.opened ? false: true;
    } else {
      this.rainLineChartDataVisible = this.opened ? false: true;
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
