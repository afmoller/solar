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

  valueType: string | null = '';

  constructor(
    private yearOverYearEntryService: YearOverYearEntryService,
    private route: ActivatedRoute) {

    Chart.register(Annotation);
    Chart.register(Colors);
  }

  ngOnInit() {

    this.route.params.subscribe(routeParams => {
      this.valueType = routeParams['valuetype'];
    
      this.valueType = this.valueType ?? 'production';
    
      this.yearOverYearEntryService.findAll(this.valueType).subscribe(data => {

        const nrOfMonthsPerYear = 12;
        let index = 0;
  
        let monthValues = data.monthValues;
  
        for (let i = 0; i < monthValues.length; i += nrOfMonthsPerYear) {
          const twelveMonths = monthValues.slice(i, i + nrOfMonthsPerYear);
  
          this.lineChartData.datasets[index].data = twelveMonths;
          this.lineChartData.datasets[index].label = data.years[index];
  
          index++;
        }
  
        this.lineChartData.labels = [1,2,3,4,5,6,7,8,9,10,11,12];
  
        this.chart?.update();
      });    
    });
  }

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
    ],
    labels: [],
  };

  public lineChartOptions: ChartConfiguration['options'] = {
    elements: {
      line: {
        tension: 0.1,
      },
    },
    scales: {
      // We use this empty structure as a placeholder for dynamic theming.
      y: {
        position: 'left',
      }
    },

    plugins: {
      legend: { display: true }
    },
    responsive: true,
    maintainAspectRatio: true,
    aspectRatio: 3,
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
