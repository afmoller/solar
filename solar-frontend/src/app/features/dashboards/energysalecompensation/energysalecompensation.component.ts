import Annotation from 'chartjs-plugin-annotation';
import { BaseChartDirective } from 'ng2-charts';
import { MatInputModule} from '@angular/material/input';
import { MatRadioModule} from '@angular/material/radio';
import { MatButtonModule} from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatDatepickerModule} from '@angular/material/datepicker';
import { MatTableDataSource, MatTableModule } from '@angular/material/table'
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReturnOnInvestmentDashboard } from 'src/app/core/models/returnoninvestmentdashboard';
import { EnergySaleCompensationService } from 'src/app/core/services/energy-sale-compensation.service';
import { EnergySaleCompensationCreateentry } from 'src/app/core/models/energysalecompensationcreateentry';
import { EnergySaleCompensationentry } from 'src/app/core/models/energysalecompensationentry';
import {
  DateAdapter,
  MAT_DATE_LOCALE,
  MAT_DATE_FORMATS
} from "@angular/material/core";
import { MomentDateAdapter } from "@angular/material-moment-adapter";
import { DatePipe } from "@angular/common";
import moment from 'moment';

export const MY_FORMATS = {
  parse: {
    dateInput: "YYYY-MM-DD"
  },
  display: {
    dateInput: "YYYY-MM-DD",
    monthYearLabel: "MMM YYYY",
    dateA11yLabel: "YYYY-MM-DD",
    monthYearA11yLabel: "MMMM YYYY"
  }
};

@Component({
  selector: 'app-energysalecompensation',
  templateUrl: './energysalecompensation.component.html',
  styleUrls: ['./energysalecompensation.component.scss'],
  standalone: true,

  imports: [
    MatInputModule,
    MatRadioModule,
    MatTableModule,
    MatButtonModule,
    BaseChartDirective,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule
  ],
  providers: [
    MatDatepickerModule,
    {    
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE]
    },
    { 
      provide: MAT_DATE_FORMATS,
      useValue: MY_FORMATS
    },
    DatePipe,
  ]
})

export class EnergySaleCompensationComponent implements OnInit {

  inputForm: FormGroup;
  
  dataSource = new MatTableDataSource();
  displayedColumns: string[] = ['compensationdate',
                                'compensation',
                                'productionyear',
                                'productionfrom',
                                'productionto',
                                'delete'
                               ];

  dataSourceCumulated = new MatTableDataSource();
  displayedColumnsCumulated: string[] = ['compensationDate',
                                         'compensation',
                                         'compensationCumulated',
                                         'productionYear',
                                         'compensationYear'
                                        ];

  constructor(
    private formBuilder: FormBuilder,
    private energySaleCompensationService: EnergySaleCompensationService
  ) {
    
    Chart.register(Annotation);
    Chart.register(Colors);

    this.inputForm = this.buildInputForm(formBuilder);
  }

  formIsInvalid() {
    return !this.inputForm.valid;
  }

  onSubmit(): void {
    let compensationDateValue: Date = this.inputForm.get('compensationdate')?.value.toDate();
    let productionfromValue: Date = this.inputForm.get('productionfrom')?.value.toDate();
    let productionToValue: Date = this.inputForm.get('productionto')?.value.toDate();

    const newEntry: EnergySaleCompensationCreateentry =  {
      compensationDate: compensationDateValue.toLocaleDateString(),
      productionFrom: productionfromValue.toLocaleDateString(),
      productionTo: productionToValue.toLocaleDateString(),
      compensationAmountInMinorUnit: this.inputForm.get('compensation')?.value,
      productionYear: this.inputForm.get('productionyear')?.value
    }

    this.energySaleCompensationService.create(newEntry).subscribe(data => {
      this.inputForm.reset();
      this.loadData();
    });
  }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.energySaleCompensationService.getAll().subscribe(data => {
      this.dataSource.data = data;
      this.dataSourceCumulated.data = this.createDataSourceCumulated(data);

      this.lineChartData.datasets[0].data = this.createCompensationDataSet(data);
      this.lineChartData.labels =  this.getDates(data);

      this.lineChartDataCumulated.datasets[0].data = this.createCompensationDataSetCumulated(data);
      this.lineChartDataCumulated.labels =  this.getDates(data);

      this.dataSource.data = this.dataSource.data.reverse();
      this.dataSourceCumulated.data = this.dataSourceCumulated.data.reverse();

      this.charts?.forEach((child) => {
        child.chart?.update()
      });
    });
  }

  buildInputForm(formBuilder: FormBuilder): FormGroup  {
    let inputForm: FormGroup = formBuilder.group({
      compensationdate: [
        moment(),
        Validators.required
      ],
      productionfrom: [
        moment(),
        Validators.required
      ],
      productionto: [
        moment(),
        Validators.required
      ],
      compensation: [
        '',
        Validators.required
      ],
      productionyear: [
        '',
        Validators.required
      ],
    });

    return inputForm;
  }

  getValueIfPositive(amount: number, isPositive: boolean): string {
    if (isPositive) {
      return this.formatValue(amount);
    } else {
      return '';
    }
  }

  getValueIfNegative(amount: number, isPositive: boolean): string {
    if (!isPositive) {
      return '-' + this.formatValue(amount);
    } else {
      return '';
    }
  }

  formatValue(amountInMinor: number): string {
    if (amountInMinor === 0) {
      return amountInMinor.toString();
    }

    let amountAsUnformattedString = amountInMinor.toString();

    let majorPart: string = amountAsUnformattedString.substring(0, amountAsUnformattedString.length - 2);
    let minorPart: string = amountAsUnformattedString.substring(amountAsUnformattedString.length - 2);

     return majorPart + '.' + minorPart;
  }

  deleteRow(id: number) {
    this.energySaleCompensationService.delete(id).subscribe(data => {
      this.loadData();
    });
  }

  extractYear(date: string): string {
    return new Date(date).getFullYear().toString();
  }

  oddOrEven(yearAsString: string) {
    let yearAsNumber: number = new Number(yearAsString).valueOf();

    if ((yearAsNumber % 2) == 1) {
      return 'odd';
    } else {
      return 'even';
    }
  }
  
  divideValueByHundred(valueToDivideByHundred: number): string {
    return (valueToDivideByHundred / 100).toString();
  }

  divideValueByTenThousand(valueToDivideByTenThousand: number): string {
    return (valueToDivideByTenThousand / 10000).toString();
  }


  public lineChartData: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        label: 'Compensation in CHF',
        fill: false,
      },
    ],
    labels: [],
  };

  public lineChartDataCumulated: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        label: 'Compensation in CHF cumulated',
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
        min: 0,
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
    animation: false
  };

  public lineChartType: ChartType = 'line';

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  @ViewChildren(BaseChartDirective) charts?: QueryList<BaseChartDirective>;

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

  createCompensationDataSet(data: EnergySaleCompensationentry[]): number[] {
    let dataSet: number[] = [];

    data.forEach(element => {
      dataSet.push(element.compensationAmountInMinorUnit  / 100);
    })

    return dataSet;
  }

  createCompensationDataSetCumulated(data: EnergySaleCompensationentry[]): number[] {
    let cumulated: number = 0;

    let dataSet: number[] = [];

    data.forEach(element => {
      cumulated = cumulated + element.compensationAmountInMinorUnit;
      dataSet.push(cumulated  / 100);
    })

    return dataSet;
  }

  getDates(data: EnergySaleCompensationentry[]): unknown[] {
    let dataSet: string[] = [];

    data.forEach(element => {
      dataSet.push( element.compensationDate);
    })

    return dataSet;
  }

  createDataSourceCumulated(data: EnergySaleCompensationentry[]): any[] {

    let cumulated: number = 0;

    let dataSet: any[] = [];

    data.forEach(element => {

      cumulated = cumulated + element.compensationAmountInMinorUnit;

      let dataSetRow = {
        compensationDate: element.compensationDate,
        compensation: element.compensationAmountInMinorUnit,
        productionYear: element.productionYear,
        compensationCumulated: cumulated,
        oddYear: element.productionYear % 2 === 0
      }

      dataSet.push(dataSetRow);
    })

    return dataSet;
  }
}
