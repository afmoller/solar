import { Component, OnInit, ViewChild } from '@angular/core';
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import Annotation from 'chartjs-plugin-annotation';
import { YearOverYearEntry } from '../../../core/models/yearoveryearentry';
import { YearOverYearEntryService } from '../../../core/services/year-over-year.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-year-over-year',
  templateUrl: './year-over-year.component.html',
  styleUrls: ['./year-over-year.component.scss']
})

export class YearOverYearComponent implements OnInit {

  mode: string | null = '';
  valueType: string = '';
  menuTitle: string = '';

  tableRows : number[][] = [];
  private monthLabels: string[] = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  displayedColumns: string[] = ['Year'].concat(this.monthLabels);
  
  constructor(
    private yearOverYearEntryService: YearOverYearEntryService,
    private route: ActivatedRoute) {

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

      this.tableRows = [];

      this.loadDataAndUpdateDiagram();
    });
  }

  private loadDataAndUpdateDiagram() {
    this.yearOverYearEntryService.findAll(this.valueType).subscribe(data => {

      const nrOfMonthsPerYear = 12;
      let index = 0;

      let monthValues = data.monthValues;
      
      for (let i = 0; i < monthValues.length; i += nrOfMonthsPerYear) {
        const twelveMonths = monthValues.slice(i, i + nrOfMonthsPerYear);

        if(this.mode === 'normal') {
          this.lineChartData.datasets[index].data = twelveMonths;
        } else {
          for(let i = 0; i < 12; i++) {
            if (i > 0) {
              twelveMonths[i] = twelveMonths[i] + twelveMonths[i-1];     
            } 
          }
          this.lineChartData.datasets[index].data = twelveMonths;
          
        }
        this.lineChartData.datasets[index].label = data.years[index];
      
        let tableRow: Array<number> = [];
        tableRow.push(Number(data.years[index]));
        tableRow = tableRow.concat(twelveMonths);

        this.tableRows.push(tableRow);

        index++;
      }

      this.lineChartData.labels = this.monthLabels;  
      this.chart?.update();

    }); 
  }

  private getDiagramTitle(valueType: string, mode: string): string {
    let suffix: string = '';
    
    if (valueType === 'autarky') {
      suffix = 'in %'
    } else {
      if (mode) {
        switch (mode) {
          case 'normal':
            suffix = 'in watt hours per month';
            break;
          case 'accumulated':
            suffix = 'in watt hours per month accumulated';
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
        data: [],
        fill: false,
        borderColor: 'rgb(144, 238, 144)',
      },
      {
        data: [],
        fill: false,
        borderColor: 'rgb(173, 216, 230)',
      },
      {
        data: [],
        fill: false,
        borderColor: 'rgb(255, 230, 153)',
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
        radius: 0 // default to disabled in all datasets
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
        position: 'bottom'
      }
    },
    responsive: true,
    maintainAspectRatio: true,
    aspectRatio: 3,
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
