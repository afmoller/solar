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
import { Chart, Colors } from 'chart.js';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReturnOnInvestmentDashboard } from 'src/app/core/models/returnoninvestmentdashboard';
import { EnergySaleCompensationentry } from 'src/app/core/models/energysalecompensationentry';
import { EnergyCostService } from 'src/app/core/services/energy-cost.service';
import { EnergyCostCreateentry } from 'src/app/core/models/energycostcreateentry';
import { EnergyCostentry } from 'src/app/core/models/energycostentry';
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
  selector: 'app-energycost',
  templateUrl: './energycost.component.html',
  styleUrls: ['./energycost.component.scss'],
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
  ],
})

export class EnergyCostComponent implements OnInit {

  inputFormEnergyCost: FormGroup;

  dataSourceEnergyCosts = new MatTableDataSource();

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

  constructor(
    private formBuilder: FormBuilder,
    private energyCostService: EnergyCostService
  ) {
    Chart.register(Annotation);
    Chart.register(Colors);

    this.inputFormEnergyCost = this.buildEnergyCostInputForm(formBuilder);
  }

  energyCostFormIsInvalid() {
    return !this.inputFormEnergyCost.valid;
  }

  onSubmitEnergyCost() {
    let fromDateDateValue: Date = this.inputFormEnergyCost.get('datefrom')?.value.toDate();
    let toDateValue: Date = this.inputFormEnergyCost.get('dateto')?.value.toDate();

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
    this.loadEnergyCostData();
  }

  loadEnergyCostData() {
    this.energyCostService.getAll().subscribe(data => {
      this.dataSourceEnergyCosts.data = data;
    })
  }

  buildEnergyCostInputForm(formBuilder: FormBuilder): FormGroup {
    let inputForm: FormGroup = formBuilder.group({
      datefrom: [
        moment(),
        Validators.required
      ],
      dateto: [
        moment(),
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
  
  getDates(data: EnergySaleCompensationentry[]): unknown[] {
    let dataSet: string[] = [];

    data.forEach(element => {
      dataSet.push( element.compensationDate);
    })

    return dataSet;
  }
}
