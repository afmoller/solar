import { DatePipe } from "@angular/common";
import { FormsModule } from '@angular/forms';
import { BaseChartDirective } from 'ng2-charts';
import Annotation from 'chartjs-plugin-annotation';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MomentDateAdapter } from "@angular/material-moment-adapter";
import { ReturnOnInvestmentEntry } from 'src/app/core/models/returnoninvestmententry';
import { ReturnOnInvestmentService } from 'src/app/core/services/return-on-investment.service';
import { ReturnOnInvestmentEntryDialogComponent } from '../../components/dialog/returnoninvestmentsentrydialog/returnoninvestmentsentrydialog.component';

import { 
  MatTableModule,
  MatTableDataSource
} from '@angular/material/table';

import { 
  inject,
  signal,
  OnInit,
  Component,
  ViewChild
} from '@angular/core';

import {
  Chart,
  ChartConfiguration,
  ChartEvent,
  ChartType,
  Colors
} from 'chart.js';

import {
  DateAdapter,
  MAT_DATE_LOCALE,
  MAT_DATE_FORMATS
} from "@angular/material/core";


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
  selector: 'app-returnoninvestment',
  templateUrl: './returnoninvestment.component.html',
  styleUrls: ['./returnoninvestment.component.scss'],
  standalone: true,

  imports: [
    FormsModule,
    MatIconModule,
    MatInputModule,
    MatTableModule,
    MatRadioModule,
    MatButtonModule,
    MatSidenavModule,
    MatCheckboxModule,
    BaseChartDirective,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule
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

export class ReturnOnInvestmentComponent implements OnInit {

  checked = signal(false);
  readonly dialog = inject(MatDialog);

  totalCost: string = '0';
  totalIncome: string = '0';

  dataSourceTotals = new MatTableDataSource();
  displayedColumnsTotals: string[] = ['totals',
                                      'cost',
                                      'income'
                                    ];

  dataSource = new MatTableDataSource();
  displayedColumns: string[] = ['date',
                                'cost',
                                'income',
                                'description',
                                'saldo',
                                'deltaSinceStart',
                                'numberOfYearsUntilPaid',
                                'actions'
                              ];

  events: string[] = [];
  opened: boolean = true;

  constructor(private returnOnInvestmentService: ReturnOnInvestmentService) {
    Chart.register(Annotation);
    Chart.register(Colors);
  }
  
  openCreateDialog(): void {
    const dialogRef = this.dialog.open(ReturnOnInvestmentEntryDialogComponent);
        
    dialogRef.afterClosed().subscribe(result => {
      
      if (result !== undefined) {
        let returnOnInvestmentToCreate = dialogRef.componentInstance.exportDialogData();
        this.createReturnOnInvestmentEntry(returnOnInvestmentToCreate);
      }
    });
  }
  
  openEditDialog(id?: number): void {
    if (id) {
      this.returnOnInvestmentService.get(id).subscribe(returnOnInvestmentEntry => {
        this.openAndInitialEditDialog(returnOnInvestmentEntry);
      });
    }
  }

  private createReturnOnInvestmentEntry(returnOnInvestmentToCreate: ReturnOnInvestmentEntry) {
    this.returnOnInvestmentService.create(returnOnInvestmentToCreate).subscribe(createdReturnOnInvestment => {
      this.loadData();
    });
  }

  private updateReturnOnInvestmentEntry(returnOnInvestmentToUpdate: ReturnOnInvestmentEntry) {
    this.returnOnInvestmentService.update(returnOnInvestmentToUpdate).subscribe(updatedReturnOnInvestment => {
      this.loadData();
    })
  }

  private openAndInitialEditDialog(returnOnInvestmentEntry: ReturnOnInvestmentEntry) {
    const dialogRef = this.dialog.open(ReturnOnInvestmentEntryDialogComponent);
    dialogRef.componentInstance.initiateDialog(returnOnInvestmentEntry);

    dialogRef.afterClosed().subscribe(result => {
      
      if (result !== undefined) {
        let returnOnInvestmentToUpdate = dialogRef.componentInstance.exportDialogData();
        this.updateReturnOnInvestmentEntry(returnOnInvestmentToUpdate);
      }
    });
  }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.returnOnInvestmentService.find().subscribe(data => {
      
      let returnOnInvestmentEntries = data.returnOnInvestmentDashboardEntryDtos.reverse();
      
      returnOnInvestmentEntries.forEach(entry => {
        entry.numberOfYearsUntilPaid = Math.round((entry.numberOfYearsUntilPaid + Number.EPSILON) * 100) / 100;
      });
      
      this.dataSource.data = returnOnInvestmentEntries;

      this.totalCost = this.formatValue(data.totalCost);
      this.totalIncome = this.formatValue(data.totalIncome);

      this.dataSourceTotals.data = [{income: this.totalIncome, cost: this.totalCost}];
      
      this.lineChartData.datasets[0].data = data.numberOfYearsUntilPaid;
      this.lineChartData.labels = data.dates;
      this.chart?.update();
      this.chart?.render();
    });
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
    this.returnOnInvestmentService.delete(id).subscribe(data => {
      this.loadData();
    });
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
        radius: 3,
        
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
    maintainAspectRatio: false,
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

  isHidden(): boolean {
    return !this.checked();
  }
}