import { Component, OnInit, ViewChild } from '@angular/core';
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import Annotation from 'chartjs-plugin-annotation';
import { Allentry } from '../../../core/models/allentry';
import { AllEntryService } from '../../../core/services/all-entry.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-all',
  templateUrl: './all.component.html',
  styleUrls: ['./all.component.scss']
})

export class AllComponent implements OnInit {

  inputForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private allEntryService: AllEntryService,
    private route: ActivatedRoute) {
    
    Chart.register(Annotation);
    Chart.register(Colors);

    this.inputForm = this.formBuilder.group({
      fromDate: '',
      toDate: ''
    });
  }

  ngOnInit() {
    this.loadDataAndPopulateChart('2023-05-01', '2023-05-01');
  }

  public fromDateChange(): void {
    let fromDateValue: string = this.inputForm.get('fromDate')?.value;
    let toDateValue: string = this.inputForm.get('toDate')?.value

    // Only change the to date automatically if the value is not set or if the 
    // from date is later than the to date.
    if ((fromDateValue && !toDateValue) || (new Date(fromDateValue) > new Date(toDateValue))) {
      this.inputForm.get('toDate')?.setValue(fromDateValue);
    }

    this.loadDataAndPopulateChart(fromDateValue, this.inputForm.get('toDate')?.value);
  }


  public toDateChange(): void {
    let fromDateValue: string = this.inputForm.get('fromDate')?.value;
    let toDateValue: string = this.inputForm.get('toDate')?.value

    // Only change the from date automatically if the value is not set or if the 
    // from date is later than the to date.
    if ((!fromDateValue && toDateValue) || (new Date(fromDateValue) > new Date(toDateValue))) {
      this.inputForm.get('fromDate')?.setValue(toDateValue);
    }

    this.loadDataAndPopulateChart(this.inputForm.get('fromDate')?.value, toDateValue, );
  }


  onSubmit(): void {

    let dateValueFromFromDate = this.inputForm.get('fromDate')?.value ?? '2022-06-01';
    let dateValueFromToDate = this.inputForm.get('toDate')?.value ?? '2022-06-01';

    this.loadDataAndPopulateChart(dateValueFromFromDate, dateValueFromToDate);
  }

  private loadDataAndPopulateChart(dateFrom: string, dateTo: string): void {
    this.allEntryService.findAll('MONTH', dateFrom, dateTo).subscribe(data => {
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

  setChartType(type: string) {
    if (type === 'line') {
      this.lineChartType = 'line';
    } else if (type === 'bar') {
      this.lineChartType = 'bar';
    }
  }

  decreaseMonth(dateField: string): void {
    if (dateField === 'toDate') {

      let dateValue: string = this.inputForm.get('toDate')?.value;
      let dateValueAsDate: Date = new Date(dateValue);
      dateValueAsDate.setMonth(dateValueAsDate.getMonth() - 1);

      this.inputForm.get('toDate')?.setValue(this.getDateAsString(dateValueAsDate));
      this.toDateChange();

    } else if (dateField === 'fromDate') {
      let dateValue: string = this.inputForm.get('fromDate')?.value;
      let dateValueAsDate: Date = new Date(dateValue);
      dateValueAsDate.setMonth(dateValueAsDate.getMonth() - 1);

      this.inputForm.get('fromDate')?.setValue(this.getDateAsString(dateValueAsDate));
      this.fromDateChange();
    }

  }
    
  increaseMonth(dateField: string): void {
    
    if (dateField === 'toDate') {

      let dateValue: string = this.inputForm.get('toDate')?.value;
      let dateValueAsDate: Date = new Date(dateValue);
      dateValueAsDate.setMonth(dateValueAsDate.getMonth() + 1);

      this.inputForm.get('toDate')?.setValue(this.getDateAsString(dateValueAsDate));
      this.toDateChange();

    } else if (dateField === 'fromDate') {
      let dateValue: string = this.inputForm.get('fromDate')?.value;
      let dateValueAsDate: Date = new Date(dateValue);
      dateValueAsDate.setMonth(dateValueAsDate.getMonth() + 1);

      this.inputForm.get('fromDate')?.setValue(this.getDateAsString(dateValueAsDate));
      this.fromDateChange();
    }
  }

  private getDateAsString(date: Date): string {
    return date.getFullYear() + '-' + this.zeroPadIfNecessary(date.getMonth() + 1) + '-' + this.zeroPadIfNecessary(date.getDate());
  }

  private zeroPadIfNecessary(value: number): string {
    return value < 10 ? '0' + value : value + '';
  }
}
