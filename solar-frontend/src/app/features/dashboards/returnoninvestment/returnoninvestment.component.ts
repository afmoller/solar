import Annotation from 'chartjs-plugin-annotation';
import { BaseChartDirective } from 'ng2-charts';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { Component, inject, model, OnInit, signal, ViewChild } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatTableDataSource, MatTableModule } from '@angular/material/table'
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { ReactiveFormsModule } from '@angular/forms';
import { ReturnOnInvestmentDashboard } from 'src/app/core/models/returnoninvestmentdashboard';
import { ReturnOnInvestmentService } from 'src/app/core/services/return-on-investment.service';
import { ReturnOnInvestmentEntry } from 'src/app/core/models/returnoninvestmententry';
import { MatSidenavModule } from '@angular/material/sidenav';
import {
  DateAdapter,
  MAT_DATE_LOCALE,
  MAT_DATE_FORMATS
} from "@angular/material/core";
import { MomentDateAdapter } from "@angular/material-moment-adapter";
import { DatePipe } from "@angular/common";
import { MatDialog } from '@angular/material/dialog';
import { ReturnOnInvestmentEntryDialog } from '../../components/dialog/editreturnoninvestmentsentry/editreturnoninvestmentsentry.component';

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
    MatSidenavModule,
    MatIconModule,
    MatInputModule,
    MatTableModule,
    MatRadioModule,
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

export class ReturnOnInvestmentComponent implements OnInit {

  readonly name = model('');
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

  constructor(
    private returnOnInvestmentService: ReturnOnInvestmentService
  ) {
    Chart.register(Annotation);
    Chart.register(Colors);
  }
  
  openCreateDialog(): void {
    const dialogRef = this.dialog.open(ReturnOnInvestmentEntryDialog);
        
    dialogRef.afterClosed().subscribe(result => {
      
      if (result !== undefined) {
        let returnOnInvestmentToCreate = dialogRef.componentInstance.exportDialogData();
        
        this.returnOnInvestmentService.create(returnOnInvestmentToCreate).subscribe(createdReturnOnInvestment => {
          this.loadData();
        })
      }
    });
  }
  
  openEditDialog(id?: number): void {
    if (id) {
      this.returnOnInvestmentService.get(id).subscribe(data => {
        const dialogRef = this.dialog.open(ReturnOnInvestmentEntryDialog);
        dialogRef.componentInstance.initiateDialog(id);
    
        dialogRef.afterClosed().subscribe(result => {
          
          if (result !== undefined) {
            let returnOnInvestmentToUpdate = dialogRef.componentInstance.exportDialogData();
            
            this.returnOnInvestmentService.update(returnOnInvestmentToUpdate).subscribe(updatedReturnOnInvestment => {
              this.loadData();
            })
          }
        });
      });
    }
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.returnOnInvestmentService.find().subscribe(data => {
      this.dataSource.data = data.returnOnInvestmentDashboardEntryDtos.reverse();

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
}