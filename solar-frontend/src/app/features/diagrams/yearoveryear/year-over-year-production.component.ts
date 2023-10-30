import { Component, OnInit, ViewChild } from '@angular/core';
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import Annotation from 'chartjs-plugin-annotation';
import { YearOverYearProductionentry } from '../../../core/models/yearoveryearproductionentry';
import { YearOverYearProductionEntryService } from '../../../core/services/year-over-year-production.service';

@Component({
  selector: 'app-year-over-year-production',
  templateUrl: './year-over-year-production.component.html',
  styleUrls: ['./year-over-year-production.component.scss']
})

export class YearOverYearProductionComponent implements OnInit {

  constructor(private yearOverYearProductionEntryService: YearOverYearProductionEntryService) {
    Chart.register(Annotation);
    Chart.register(Colors);
  }

  ngOnInit() {
    this.yearOverYearProductionEntryService.findAll().subscribe(data => {

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
