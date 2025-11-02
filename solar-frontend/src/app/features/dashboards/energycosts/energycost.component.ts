import { DatePipe } from "@angular/common";
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { EnergyCostentry } from 'src/app/core/models/energycostentry';
import { EnergyCostService } from 'src/app/core/services/energy-cost.service';
import { EnergySaleCompensationentry } from 'src/app/core/models/energysalecompensationentry';
import { EnergyCostEntryDialogComponent } from '../../components/dialog/energycostentrydialog/energycostentrydialog.component';

import {
  inject,
  OnInit,
  Component,
  signal
} from '@angular/core';

import {
  MatTableModule,
  MatTableDataSource
} from '@angular/material/table'

import {
  DateAdapter,
  MAT_DATE_LOCALE,
  MAT_DATE_FORMATS
} from '@angular/material/core';

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
    imports: [
        FormsModule,
        MatIconModule,
        MatInputModule,
        MatRadioModule,
        MatTableModule,
        MatButtonModule,
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

export class EnergyCostComponent implements OnInit {

  checked = signal(false);
  readonly dialog = inject(MatDialog);
  private energyCostService = inject(EnergyCostService);

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

  ngOnInit() {
    this.loadEnergyCostData();
  }

  loadEnergyCostData() {
    this.energyCostService.getAll().subscribe(data => {
      this.dataSourceEnergyCosts.data = data.reverse();
    })
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

  openEditDialog(id?: number): void {
    if (id) {
      this.energyCostService.get(id).subscribe(energyCostEntry => {
        this.openAndInitialEditDialog(energyCostEntry);
      });
    }
  }

  openCreateDialog(): void {
    const dialogRef = this.dialog.open(EnergyCostEntryDialogComponent);

    dialogRef.afterClosed().subscribe(result => {

      if (result !== undefined) {
        let energyCostToCreate = dialogRef.componentInstance.exportDialogData();
        this.createEnergyCostEntry(energyCostToCreate);
      }
    });
  }

  private openAndInitialEditDialog(returnOnInvestmentEntry: EnergyCostentry) {
    const dialogRef = this.dialog.open(EnergyCostEntryDialogComponent);
    dialogRef.componentInstance.initiateDialog(returnOnInvestmentEntry);

    dialogRef.afterClosed().subscribe(result => {

      if (result !== undefined) {
        let energyCostToUpdate = dialogRef.componentInstance.exportDialogData();
        this.updateEnergyCostEntry(energyCostToUpdate);
      }
    });
  }

  private createEnergyCostEntry(energyCostToCreate: EnergyCostentry) {
    this.energyCostService.create(energyCostToCreate).subscribe(createdEnergyCost => {
      this.loadEnergyCostData();
    });
  }

  private updateEnergyCostEntry(energyCostToUpdate: EnergyCostentry) {
    this.energyCostService.update(energyCostToUpdate).subscribe(updatedEnergyCost => {
      this.loadEnergyCostData();
    })
  }

  isHidden(): boolean {
    return !this.checked();
  }
}
