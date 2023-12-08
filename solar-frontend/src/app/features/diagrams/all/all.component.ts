import { Component, OnInit, ViewChild } from '@angular/core';
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import Annotation from 'chartjs-plugin-annotation';
import { Allentry } from '../../../core/models/allentry';
import { AllEntryService } from '../../../core/services/all-entry.service';
import { SummaryPerDayEntryService } from '../../../core/services/summary-per-day-entry.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-all',
  templateUrl: './all.component.html',
  styleUrls: ['./all.component.scss']
})

export class AllComponent implements OnInit {

  inputForm: FormGroup;
  menuTitle: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private allEntryService: AllEntryService,
    private summaryPerDayEntryService: SummaryPerDayEntryService,
    private route: ActivatedRoute) {
    
    Chart.register(Annotation);
    Chart.register(Colors);

    this.inputForm = this.formBuilder.group({
      fromDate: '',
      toDate: ''
    });
  }

  ngOnInit() {
    this.summaryPerDayEntryService.findNewestEntry().subscribe(data => {
      let dateAsString: string = this.getDateAsString(new Date(data.date));
      
      this.inputForm.get('fromDate')?.setValue(dateAsString);
      this.inputForm.get('toDate')?.setValue(dateAsString);

      this.loadDataAndPopulateChart(dateAsString, dateAsString);
    });
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

    this.loadDataAndPopulateChart(this.inputForm.get('fromDate')?.value, toDateValue);
  }

  private loadDataAndPopulateChart(dateFrom: string, dateTo: string): void {
    this.allEntryService.findAll('MONTH', dateFrom, dateTo).subscribe(data => {
      this.lineChartData.datasets[0].data = data.saleWattHours;
      this.lineChartData.datasets[1].data = data.purchaseWattHours
      this.lineChartData.datasets[2].data = data.productionWattHours;
      this.lineChartData.datasets[3].data = data.consumptionWattHours;
      this.lineChartData.datasets[4].data = data.selfConsumptionWattHours;
      this.lineChartData.labels = data.date;

      this.menuTitle = dateFrom + ' - ' + dateTo;
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
      this.shiftMonthFieldValue('toDate', -1);
      this.toDateChange();
    } else if (dateField === 'fromDate') {
      this.shiftMonthFieldValue('fromDate', -1);
      this.fromDateChange();
    }
  }

  decreaseMonths(): void {
    this.shiftMonthFieldValue('fromDate', -1);
    this.shiftMonthFieldValue('toDate', -1);

    this.loadDataAndPopulateChart(this.inputForm.get('fromDate')?.value, this.inputForm.get('toDate')?.value)
  }
    
  increaseMonth(dateField: string): void {
    if (dateField === 'toDate') {
      this.shiftMonthFieldValue('toDate', 1);
      this.toDateChange();
    } else if (dateField === 'fromDate') {
      this.shiftMonthFieldValue('toDate', 1);
      this.fromDateChange();
    }
  }

  increaseMonths(): void {
    this.shiftMonthFieldValue('fromDate', 1);
    this.shiftMonthFieldValue('toDate', 1);

    this.loadDataAndPopulateChart(this.inputForm.get('fromDate')?.value, this.inputForm.get('toDate')?.value)
  }

  private shiftMonthFieldValue(dateField: string, shiftValue: number) {
    let dateValue: string = this.inputForm.get(dateField)?.value;
    let dateValueAsDate: Date = new Date(dateValue);
    dateValueAsDate.setMonth(dateValueAsDate.getMonth() + shiftValue);

    this.inputForm.get(dateField)?.setValue(this.getDateAsString(dateValueAsDate));
  }

  private getDateAsString(date: Date): string {
    return date.getFullYear() + '-' + this.zeroPadIfNecessary(date.getMonth() + 1) + '-' + this.zeroPadIfNecessary(date.getDate());
  }

  private zeroPadIfNecessary(value: number): string {
    return value < 10 ? '0' + value : value + '';
  }
}
