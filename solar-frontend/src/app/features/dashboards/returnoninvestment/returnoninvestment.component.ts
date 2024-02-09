import { NgChartsModule } from 'ng2-charts';
import Annotation from 'chartjs-plugin-annotation';
import { BaseChartDirective } from 'ng2-charts';
import { MatInputModule} from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatDatepickerModule} from '@angular/material/datepicker';
import { MatTableDataSource, MatTableModule } from '@angular/material/table'
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ReturnOnInvestmentDashboard } from 'src/app/core/models/returnoninvestmentdashboard';
import { ReturnOnInvestmentService } from 'src/app/core/services/return-on-investment.service';
import {MatButtonModule} from '@angular/material/button';
import { ReturnOnInvestmentCreateentry } from 'src/app/core/models/returnoninvestmentcreateentry';


export const atLeastOne = (validator: ValidatorFn, controls:string[]) => (
  group: FormGroup,
): ValidationErrors | null => {
  if(!controls){
    controls = Object.keys(group.controls)
  }

  const hasAtLeastOne = group && group.controls && controls
    .some(k => !validator(group.controls[k]));

  return hasAtLeastOne ? null : {
    atLeastOne: true,
  };
};

@Component({
  selector: 'app-returnoninvestment',
  templateUrl: './returnoninvestment.component.html',
  styleUrls: ['./returnoninvestment.component.scss'],
  standalone: true,

  imports: [
    MatInputModule,
    MatTableModule,
    NgChartsModule,
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

export class ReturnOnInvestmentComponent implements OnInit {

  inputForm: FormGroup;

  totalCost: string = '0';
  totalIncome: string = '0';

  dataSource = new MatTableDataSource();
  displayedColumns: string[] = ['date',
                                'cost',
                                'income',
                                'description',
                                'saldo',
                                'deltaSinceStart',
                                'numberOfYearsUntilPaid',
                                'delete'
                              ];

  constructor(
    private formBuilder: FormBuilder,
    private returnOnInvestmentService: ReturnOnInvestmentService
  ) {
    Chart.register(Annotation);
    Chart.register(Colors);

    this.inputForm = this.buildInputForm(formBuilder);
  }

  formIsInvalid() {
    return !this.inputForm.valid;
  }

  costFieldHasValue(): boolean {
    return this.fieldHasValue(this.inputForm.get('cost'));
  }

  incomeFieldHasValue(): boolean {
    return this.fieldHasValue(this.inputForm.get('income'));
  }

  fieldHasValue(abstractControl: AbstractControl | null): boolean {
    if (abstractControl?.value) {
      return true;
    } else {
      return false;
    }
  }

  onSubmit(): void {
    let dateValue: Date = this.inputForm.get('date')?.value;

    const newEntry: ReturnOnInvestmentCreateentry =  {
      date: dateValue.toLocaleDateString(),
      amountInMinorUnit: this.incomeFieldHasValue() ? this.inputForm.get('income')?.value : this.inputForm.get('cost')?.value,
      amountIsPositive: this.incomeFieldHasValue(),
      description: this.inputForm.get('description')?.value
    }

    this.returnOnInvestmentService.create(newEntry);
    this.inputForm.reset();
    this.ngOnInit();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnInit() {
    this.returnOnInvestmentService.find().subscribe(data => {
      this.dataSource.data = data.returnOnInvestmentDashboardEntryDtos;

      this.lineChartData.datasets[0].data = data.numberOfYearsUntilPaid;
      this.lineChartData.labels = data.dates;
      this.chart?.update();

      this.totalCost = this.formatValue(data.totalCost);
      this.totalIncome = this.formatValue(data.totalIncome);
    });
  }

  buildInputForm(formBuilder: FormBuilder): FormGroup  {
    let inputForm: FormGroup = formBuilder.group({
      date: [
        new Date(),
        Validators.required
      ],
      description: [
        '',
        Validators.required
      ],
      income: [
        '',
      ],
      cost: [
        ''
      ]
    }, { validator: atLeastOne(Validators.required, ['income','cost']) });

    inputForm.get('income')?.valueChanges.subscribe(value => {
      if (value) {
        inputForm.get('cost')?.disable({emitEvent: false});
      } else {
        inputForm.get('cost')?.enable({emitEvent: false});
      }
    });

    inputForm.get('cost')?.valueChanges.subscribe(value => {
      if (value) {
        inputForm.get('income')?.disable({emitEvent: false});
      } else {
        inputForm.get('income')?.enable({emitEvent: false});
      }
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
    this.returnOnInvestmentService.delete(id);
  }

  public lineChartData: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        label: 'Number of years until paid',
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
        type: 'logarithmic',
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
    animation: false
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
