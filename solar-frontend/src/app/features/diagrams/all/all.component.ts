import { Component, OnInit, ViewChild } from '@angular/core';
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import Annotation from 'chartjs-plugin-annotation';
import { Allentry } from '../../../core/models/allentry';
import { AllEntryService } from '../../../core/services/all-entry.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-all',
  templateUrl: './all.component.html',
  styleUrls: ['./all.component.scss']
})

export class AllComponent implements OnInit {

  constructor(
    private allEntryService: AllEntryService,
    private route: ActivatedRoute) {
    
    Chart.register(Annotation);
    Chart.register(Colors);
  }

  ngOnInit() {
    this.allEntryService.findAll('MONTH', '2023-05-01').subscribe(data => {
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
        label: 'Sale (watt hours)',
        fill: false,
      },
      {
        data: [],
        label: 'Purchase (watt hours)',
        fill: false,
      },
      {
        data: [],
        label: 'Production (watt hours)',
        fill: false,
      },
      {
        data: [],
        label: 'Consumption (watt hours)',
        fill: false,
      },
      {
        data: [],
        label: 'Self Consumption (watt hours)',
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
      legend: { 
        display: true,
        position: 'bottom'
      }
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
