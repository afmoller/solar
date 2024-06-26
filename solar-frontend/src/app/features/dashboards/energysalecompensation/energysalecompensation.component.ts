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
import { EnergyCostService } from 'src/app/core/services/energy-cost.service';
import { EnergyCostCreateentry } from 'src/app/core/models/energycostcreateentry';
import { EnergyCostentry } from 'src/app/core/models/energycostentry';

@Component({
  selector: 'app-energysalecompensation',
  templateUrl: './energysalecompensation.component.html',
  styleUrls: ['./energysalecompensation.component.scss'],
  standalone: true,

  imports: [
    MatInputModule,
    MatTableModule,
    BaseChartDirective,
    MatRadioModule,
    MatButtonModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule
  ],
  providers: [
    MatDatepickerModule,
  ],
})

export class EnergySaleCompensationComponent implements OnInit {

  inputForm: FormGroup;
  inputFormEnergyCost: FormGroup;

  dataSource = new MatTableDataSource();
  dataSourceEnergyCosts = new MatTableDataSource();

  displayedColumns: string[] = ['compensationdate',
                                'compensation',
                                'productionyear',
                                'productionfrom',
                                'productionto',
                                'delete'
                               ];

  displayedEnergyCostColumns: string[] = ['datefrom',
                                          'dateto',
                                          'electricalgridcostintenthousands',
                                          'energycostperkwhintenthousands',
                                          'feeoneintenthousands',
                                          'feetwointenthousands',
                                          'feethreeintenthousands',
                                          'totalcostvatexcluded',
                                          'totalcostvatincluded',
                                          'valueAddedTaxRate',
                                          'deleteenergycost'
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
    private energyCostService: EnergyCostService,
    private energySaleCompensationService: EnergySaleCompensationService
  ) {
    Chart.register(Annotation);
    Chart.register(Colors);

    this.inputForm = this.buildInputForm(formBuilder);
    this.inputFormEnergyCost = this.buildEnergyCostInputForm(formBuilder);
  }

  formIsInvalid() {
    return !this.inputForm.valid;
  }

  energyCostFormIsInvalid() {
    return !this.inputFormEnergyCost.valid;
  }

  onSubmit(): void {
    let compensationDateValue: Date = this.inputForm.get('compensationdate')?.value;
    let productionfromValue: Date = this.inputForm.get('productionfrom')?.value;
    let productionToValue: Date = this.inputForm.get('productionto')?.value;

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

  onSubmitEnergyCost() {
    debugger;
    
    let fromDateDateValue: Date = this.inputFormEnergyCost.get('datefrom')?.value;
    let toDateValue: Date = this.inputFormEnergyCost.get('dateto')?.value;
    
    const newEntry: EnergyCostCreateentry =  {
      fromDate: fromDateDateValue.toLocaleDateString(),
      toDate: toDateValue.toLocaleDateString(),
      feeOneInTenThousands: this.inputFormEnergyCost.get('feeoneintenthousands')?.value,
      feeTwoInTenThousands: this.inputFormEnergyCost.get('feetwointenthousands')?.value,
      feeThreeInTenThousands: this.inputFormEnergyCost.get('feethreeintenthousands')?.value,
      electricalGridCostInTenThousands: this.inputFormEnergyCost.get('electricalgridcostintenthousands')?.value,
      energyCostPerKwhInTenThousands: this.inputFormEnergyCost.get('energycostperkwhintenthousands')?.value,
      valueAddedTaxPercentageRateInMinorUnit: this.inputFormEnergyCost.get('valueaddedtaxpercentagerateinminorunit')?.value
    }

    this.energyCostService.create(newEntry).subscribe(data => {
      this.inputFormEnergyCost.reset();
      this.loadEnergyCostData();
    });
  }

  ngOnInit() {
    this.loadData();
    this.loadEnergyCostData();
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

  loadEnergyCostData() {
    this.energyCostService.getAll().subscribe(data => {
      this.dataSourceEnergyCosts.data = data;
    })
  }

  buildInputForm(formBuilder: FormBuilder): FormGroup  {
    let inputForm: FormGroup = formBuilder.group({
      compensationdate: [
        new Date(),
        Validators.required
      ],
      productionfrom: [
        new Date(),
        Validators.required
      ],
      productionto: [
        new Date(),
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

  buildEnergyCostInputForm(formBuilder: FormBuilder): FormGroup {
    let inputForm: FormGroup = formBuilder.group({
      datefrom: [
        new Date(),
        Validators.required
      ],
      dateto: [
        new Date(),
        Validators.required
      ],
      electricalgridcostintenthousands: [
        '',
        Validators.required
      ],
      energycostperkwhintenthousands: [
        '',
        Validators.required
      ],
      feeoneintenthousands: [
        '',
        Validators.required
      ],
      feetwointenthousands: [
        '',
        Validators.required
      ],
      feethreeintenthousands: [
        '',
        Validators.required
      ],
      valueaddedtaxpercentagerateinminorunit: [
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

  deleteEnergyCostRow(id: number) {
    this.energyCostService.delete(id).subscribe(data => {
      this.loadEnergyCostData();
    });
  }
   
  totalCostVatExcludedInTenThousands(energyCostentry: EnergyCostentry): number {
    return energyCostentry.electricalGridCostInTenThousands +
                                      energyCostentry.energyCostPerKwhInTenThousands +
                                      energyCostentry.feeOneInTenThousands +
                                      energyCostentry.feeTwoInTenThousands +
                                      energyCostentry.feeThreeInTenThousands;
  }
  
  totalCostVatIncluded(energyCostentry: EnergyCostentry): string {
    let totalCostVatExcludedInTenThousands = this.totalCostVatExcludedInTenThousands(energyCostentry);
    let vatFactor = 10000 + energyCostentry.valueAddedTaxPercentageRateInMinorUnit;

    return (totalCostVatExcludedInTenThousands * vatFactor / 10000 / 10000).toFixed(7).toString();
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
