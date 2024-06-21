import { Component, OnInit, ViewChild } from '@angular/core';
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import Annotation from 'chartjs-plugin-annotation';
import { AccumulatedAllEntryService } from '../../../core/services/accumulated-all-entry.service';

@Component({
  selector: 'app-accumulated-all',
  templateUrl: './accumulated-all.component.html',
  styleUrls: ['./accumulated-all.component.scss']
})

export class AccumulatedAllComponent implements OnInit {

  constructor(private accumulatedAllEntryService: AccumulatedAllEntryService) {
    Chart.register(Annotation);
    Chart.register(Colors);
  }

  ngOnInit() {
    this.accumulatedAllEntryService.findAll().subscribe(data => {
      this.lineChartData.datasets[0].data = data.saleWattHours;
      this.lineChartData.datasets[1].data = data.purchaseWattHours
      this.lineChartData.datasets[2].data = data.productionWattHours;
      this.lineChartData.datasets[3].data = data.consumptionWattHours;
      this.lineChartData.datasets[4].data = data.selfConsumptionWattHours;
      this.lineChartData.labels = data.date;

      this.chart?.update();
    });
  }

  public lineChartData: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        label: 'Accumulated Sale (watt hours)',
        fill: false,
      },
      {
        data: [],
        label: 'Accumulated Purchase (watt hours)',
        fill: false,
      },
      {
        data: [],
        label: 'Accumulated Production (watt hours)',
        fill: false,
      },
      {
        data: [],
        label: 'Accumulated Consumption (watt hours)',
        fill: false,
      },
      {
        data: [],
        label: 'Accumulated Self Consumption (watt hours)',
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
