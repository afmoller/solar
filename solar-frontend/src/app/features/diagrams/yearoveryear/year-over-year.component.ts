import { BaseChartDirective } from 'ng2-charts';
import { ActivatedRoute } from '@angular/router';
import Annotation from 'chartjs-plugin-annotation';
import { MatSidenavModule } from '@angular/material/sidenav';
import { YearOverYearEntryService } from '../../../core/services/year-over-year.service';

import {
  OnInit,
  Component,
  ViewChild,
  inject
} from '@angular/core';

import {
  Chart,
  Colors,
  ChartType,
  ChartEvent,
  ChartConfiguration
} from 'chart.js';

import {
  MatTableModule,
  MatTableDataSource
} from '@angular/material/table'

@Component({
    selector: 'app-year-over-year',
    templateUrl: './year-over-year.component.html',
    styleUrls: ['./year-over-year.component.scss'],
    imports: [
        MatTableModule,
        MatSidenavModule,
        BaseChartDirective
    ]
})

export class YearOverYearComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private yearOverYearEntryService = inject(YearOverYearEntryService);

  mode: string | null = '';
  valueType: string = '';
  menuTitle: string = '';

  private monthLabels: string[] = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  displayedColumns: string[] = ['Year'].concat(this.monthLabels);
  dataSourceTableRows = new MatTableDataSource<string[]>();

  events: string[] = [];
  opened: boolean = true;

  constructor() {
    Chart.register(Annotation);
    Chart.register(Colors);
  }

  ngOnInit() {
    this.route.params.subscribe(routeParams => {
      this.mode = routeParams['mode'];
      this.mode = this.mode ?? 'normal';

      this.valueType = routeParams['valuetype'];
      this.valueType = this.valueType ?? 'production';
      this.menuTitle = this.getDiagramTitle(this.valueType, this.mode);

      this.loadDataAndUpdateDiagram();
    });
  }

  private divideByThousand(values: number[]) : number[] {
    return values.map(value => value/1000);
  }

  private loadDataAndUpdateDiagram() {
    this.yearOverYearEntryService.findAll(this.valueType).subscribe(data => {

      const nrOfMonthsPerYear = 12;
      let index = 0;

      let monthValues = data.monthValues;
      let tableRows : string[][] = [];

      for (let i = 0; i < monthValues.length; i += nrOfMonthsPerYear) {
        const twelveMonths = monthValues.slice(i, i + nrOfMonthsPerYear);

        if(this.mode === 'normal') {
          this.lineChartData.datasets[index].data = this.doDivideValue(this.valueType) ? this.divideByThousand(twelveMonths) : twelveMonths;
        } else {
          for(let i = 0; i < 12; i++) {
            if (i > 0) {
              twelveMonths[i] = twelveMonths[i] + twelveMonths[i-1];
            }
          }
          this.lineChartData.datasets[index].data = this.doDivideValue(this.valueType) ? this.divideByThousand(twelveMonths) : twelveMonths;
        }

        if (data.years[index] == '-1') {
          data.years[index] = 'Lowest';
        }
        if (data.years[index] == '-2') {
          data.years[index] = 'Highest';
        }
        if (data.years[index] == '-3') {
          data.years[index] = 'Average';
        }

        this.lineChartData.datasets[index].label = data.years[index];

        let tableRow: Array<string> = [];
        tableRow.push(String(data.years[index]));
        tableRow = tableRow.concat((this.doDivideValue(this.valueType) ? this.divideByThousand(twelveMonths) : twelveMonths).map(String));
        tableRows.push(tableRow);

        index++;
      }

      if (this.chart) {
        if(this.chart.options) {
          if (this.chart.options.plugins) {
            if(this.chart.options.plugins.title) {
              this.chart.options.plugins.title.text = this.menuTitle;
            }
          }
        }
      }

      this.lineChartData.labels = this.monthLabels;
      this.chart?.update();
      this.chart?.render();

      this.dataSourceTableRows.data = tableRows;
    });
  }

  private doDivideValue(valueType: string): boolean {
    return valueType !== 'autarky';
  }

  private getDiagramTitle(valueType: string, mode: string): string {
    let suffix: string = '';

    if (valueType === 'autarky') {
      suffix = 'in %'
    } else {
      if (mode) {
        switch (mode) {
          case 'normal':
            suffix = 'in kilowatt-hours per month';
            break;
          case 'accumulated':
            suffix = 'in kilowatt-hours per month cumulated';
            break;
        }
      }
    }

    if (valueType) {
      let diagramTitle: string = '';

      switch (valueType) {
        case 'production':
          diagramTitle = 'Production';
          break;
        case 'consumption':
          diagramTitle = 'Consumption';
          break;
        case 'purchase':
          diagramTitle =  'Purchase';
          break;
        case 'sale':
          diagramTitle =  'Sale';
          break;
        case 'selfconsumption':
          diagramTitle =  'Selfconsumption';
          break;
        case 'autarky':
          diagramTitle =  'Autarky';
          break;
      }

      return diagramTitle + ' ' + suffix;
    }

    return 'The valueType is either not set or has an unknown value';
  }

  public lineChartData: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [], // 2022
        fill: false,
        borderColor: 'rgb(144, 238, 144)',
      },
      {
        data: [], // 2023
        fill: false,
        borderColor: 'rgb(173, 216, 230)',
      },
      {
        data: [], // 2024
        fill: false,
        borderColor: 'rgb(255, 230, 153)',
      },
      {
        data: [], // 2025
        fill: false,
        borderColor: 'rgb(255, 102, 0)',
      },
      {
        data: [], // 2026
        fill: false,
        borderColor: 'rgb(55, 102, 250)',
      },
      {
        data: [],
        fill: false,
        borderColor: 'rgb(255, 153, 153)',
        borderDash: [10,10],
      },
      {
        data: [],
        fill: false,
        borderColor: 'rgb(153, 255, 153)',
        borderDash: [10,10],
      },
      {
        data: [],
        fill: false,
        borderColor: 'rgb(153, 153, 255)',
        borderDash: [10,10],
      },
    ],
    labels: [],
  };

  public lineChartOptions: ChartConfiguration['options'] = {
    elements: {
      line: {
        tension: 0.1,
      },
      point: {
        radius: 3,
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
      }
    },

    plugins: {
      legend: {
        display: true,
        position: 'bottom',
        labels: {
          usePointStyle: true,
        }
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
  public chartClicked({
    event,
    active,
  }: {
    event?: ChartEvent;
    active?: object[];
  }): void {
  }

  public chartHovered({
    event,
    active,
  }: {
    event?: ChartEvent;
    active?: object[];
  }): void {
  }
}
