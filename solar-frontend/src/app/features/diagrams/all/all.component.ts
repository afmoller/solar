import { Component, OnInit, ViewChild } from '@angular/core';
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import Annotation from 'chartjs-plugin-annotation';
import { Allentry } from '../../../core/models/allentry';
import { AllEntryService } from '../../../core/services/all-entry.service';
import { SummaryPerDayEntryService } from '../../../core/services/summary-per-day-entry.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { DataExportEntryService } from 'src/app/core/services/data-export-entry.service';

@Component({
  selector: 'app-all',
  templateUrl: './all.component.html',
  styleUrls: ['./all.component.scss'],
  standalone: true,

  imports: [
    BaseChartDirective,
    ReactiveFormsModule
  ]
})

export class AllComponent implements OnInit {

  inputForm: FormGroup;
  menuTitle: string = '';
  menuTitlePrefix: string = '';
  selectionScope: string = 'DAY';
  shiftSelectionScope: string = 'month'
  
  static readonly menuTitlePrefixWatts: string = 'watts';
  static readonly menuTitlePrefixWattHours: string = 'watt hours';
      
  constructor(
    private formBuilder: FormBuilder,
    private allEntryService: AllEntryService,
    private summaryPerDayEntryService: SummaryPerDayEntryService,
    private dataExportEntryService: DataExportEntryService,
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

      this.loadDataAndPopulateChart(dateAsString, dateAsString, this.selectionScope);
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

    this.loadDataAndPopulateChart(fromDateValue, this.inputForm.get('toDate')?.value, this.selectionScope);
  }

  public toDateChange(): void {
    let fromDateValue: string = this.inputForm.get('fromDate')?.value;
    let toDateValue: string = this.inputForm.get('toDate')?.value

    // Only change the from date automatically if the value is not set or if the
    // from date is later than the to date.
    if ((!fromDateValue && toDateValue) || (new Date(fromDateValue) > new Date(toDateValue))) {
      this.inputForm.get('fromDate')?.setValue(toDateValue);
    }

    this.loadDataAndPopulateChart(this.inputForm.get('fromDate')?.value, toDateValue, this.selectionScope);
  }

  private loadDataAndPopulateChart(dateFrom: string, dateTo: string, selectionType: string): void {
    
    if (selectionType === 'MONTH' || selectionType === 'DAY') {
      this.menuTitlePrefix = AllComponent.menuTitlePrefixWattHours;
      this.allEntryService.findAll(selectionType, dateFrom, dateTo).subscribe(data => {
        this.lineChartData.datasets[0].data = data.saleWattHours;
        this.lineChartData.datasets[1].data = data.purchaseWattHours
        this.lineChartData.datasets[2].data = data.productionWattHours;
        this.lineChartData.datasets[3].data = data.consumptionWattHours;
        this.lineChartData.datasets[4].data = data.selfConsumptionWattHours;
        this.lineChartData.labels = data.date;
        this.chart?.update();
      });
    } else if (selectionType === 'HOUR') {
      this.menuTitlePrefix = AllComponent.menuTitlePrefixWattHours;
      this.dataExportEntryService.getDateTimeAndValuesForTimespanWatthours(selectionType, dateFrom, dateTo).subscribe(data => {
        this.lineChartData.datasets[0].data = data.saleWatthours;
        this.lineChartData.datasets[1].data = data.purchaseWatthours;
        this.lineChartData.datasets[2].data = data.productionWatthours;
        this.lineChartData.datasets[3].data = data.consumptionWatthours;
        this.lineChartData.datasets[4].data = data.selfConsumptionWatthours;
        this.lineChartData.labels = data.dateTimes;
        this.chart?.update();
      });
    } else if (selectionType === 'MINUTE') {
      this.menuTitlePrefix = AllComponent.menuTitlePrefixWatts;
      this.dataExportEntryService.getDateTimeAndValuesForTimespan(selectionType, dateFrom, dateTo).subscribe(data => {
        this.lineChartData.datasets[0].data = data.saleWattages;
        this.lineChartData.datasets[1].data = data.purchaseWattages;
        this.lineChartData.datasets[2].data = data.productionWattages;
        this.lineChartData.datasets[3].data = data.consumptionWattages;
        this.lineChartData.datasets[4].data = data.selfConsumptionWattages;
        this.lineChartData.labels = data.dateTimes;
        this.chart?.update();
      });
    }
    this.menuTitle = dateFrom + ' - ' + dateTo;

    this.lineChartData.datasets[0].label = 'Sale (' + this.menuTitlePrefix +')';
    this.lineChartData.datasets[1].label = 'Purchase (' + this.menuTitlePrefix +')';
    this.lineChartData.datasets[2].label = 'Production (' + this.menuTitlePrefix +')';
    this.lineChartData.datasets[3].label = 'Consumption (' + this.menuTitlePrefix +')';
    this.lineChartData.datasets[4].label = 'Self Consumption (' + this.menuTitlePrefix +')';

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
      },
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

  setChartType(type: string) {
    if (type === 'line') {
      this.lineChartType = 'line';
    } else if (type === 'bar') {
      this.lineChartType = 'bar';
    }
  }

  setSelectionScope(selectionScope:string): void {
    this.selectionScope = selectionScope;
    this.adjustShiftSelectionScope();
    this.loadDataAndPopulateChart(this.inputForm.get('fromDate')?.value, this.inputForm.get('toDate')?.value, selectionScope)
  }

  adjustShiftSelectionScope() {
    if (this.selectionScope === 'MONTH') {
      this.shiftSelectionScope = 'year';
    } else if (this.selectionScope === 'DAY') {
      this.shiftSelectionScope = 'month';
    } else if (this.selectionScope === 'HOUR' || this.selectionScope === 'MINUTE') {
      this.shiftSelectionScope = 'day';
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

    this.loadDataAndPopulateChart(this.inputForm.get('fromDate')?.value, this.inputForm.get('toDate')?.value, this.selectionScope)
  }

  increaseMonth(dateField: string): void {
    if (dateField === 'toDate') {
      this.shiftMonthFieldValue('toDate', 1);
      this.toDateChange();
    } else if (dateField === 'fromDate') {
      this.shiftMonthFieldValue('fromDate', 1);
      this.fromDateChange();
    }
  }

  increaseMonths(): void {
    this.shiftMonthFieldValue('fromDate', 1);
    this.shiftMonthFieldValue('toDate', 1);

    this.loadDataAndPopulateChart(this.inputForm.get('fromDate')?.value, this.inputForm.get('toDate')?.value, this.selectionScope)
  }

  private shiftMonthFieldValue(dateField: string, shiftValue: number) {

    if (this.selectionScope === 'DAY') {
      let dateValue: string = this.inputForm.get(dateField)?.value;
      let dateValueAsDate: Date = new Date(dateValue);
      dateValueAsDate.setMonth(dateValueAsDate.getMonth() + shiftValue);
  
      this.inputForm.get(dateField)?.setValue(this.getDateAsString(dateValueAsDate));
    } else if (this.selectionScope === 'HOUR' || this.selectionScope === 'MINUTE') {
      let dateValue: string = this.inputForm.get(dateField)?.value;
      let dateValueAsDate: Date = new Date(dateValue);
      dateValueAsDate.setDate(dateValueAsDate.getDate() + shiftValue);
  
      this.inputForm.get(dateField)?.setValue(this.getDateAsString(dateValueAsDate));
    }
  }

  private getDateAsString(date: Date): string {
    return date.getFullYear() + '-' + this.zeroPadIfNecessary(date.getMonth() + 1) + '-' + this.zeroPadIfNecessary(date.getDate());
  }

  private zeroPadIfNecessary(value: number): string {
    return value < 10 ? '0' + value : value + '';
  }

}
