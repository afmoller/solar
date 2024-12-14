import { DatePipe } from "@angular/common";
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule} from '@angular/material/radio';
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { MomentDateAdapter } from "@angular/material-moment-adapter";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatDatepickerModule } from '@angular/material/datepicker';
import { EnergyCostentry } from "src/app/core/models/energycostentry";
import moment, { Moment } from 'moment';
import {TooltipPosition, MatTooltipModule} from '@angular/material/tooltip';

import { 
  FormsModule,
  ReactiveFormsModule
} from "@angular/forms";

import { 
  Component,
  inject,
  signal
} from "@angular/core";

import { 
  MatDialogRef,
  MatDialogClose,
  MatDialogActions,
  MatDialogContent
} from "@angular/material/dialog";

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
  selector: 'app-energycostentrydialog',
  templateUrl: 'energycostentrydialog.component.html',
  styleUrls: ['energycostentrydialog.component.scss'],
  standalone: true,
  imports: [
    FormsModule,
    MatIconModule,
    MatDialogClose,
    MatInputModule,
    MatRadioModule,
    MatButtonModule,
    MatDialogActions,
    MatDialogContent,
    MatFormFieldModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    MatTooltipModule
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

export class EnergyCostEntryDialogComponent {

  readonly dialogRef = inject(MatDialogRef<EnergyCostEntryDialogComponent>);
    
  entryId: number | undefined;
  dateToAsMoment = signal<Moment>(moment());
  dateFromAsMoment = signal<Moment>(moment());
  feeoneintenthousands = signal<number>(0);
  feetwointenthousands = signal<number>(0);
  feethreeintenthousands = signal<number>(0);
  energycostperkwhintenthousands = signal<number>(0);
  electricalgridcostintenthousands = signal<number>(0);
  valueaddedtaxpercentagerateinminorunit = signal<number>(0);

  constructor() {
  }
 
  public initiateDialog(energyCostEntry: EnergyCostentry) {
    this.entryId = energyCostEntry.id;
    this.dateToAsMoment.set(moment(energyCostEntry.toDate));
    this.dateFromAsMoment.set(moment(energyCostEntry.fromDate));
    this.feeoneintenthousands.set(energyCostEntry.feeOneInTenThousands);
    this.feetwointenthousands.set(energyCostEntry.feeTwoInTenThousands);
    this.feethreeintenthousands.set(energyCostEntry.feeThreeInTenThousands);
    this.energycostperkwhintenthousands.set(energyCostEntry.energyCostPerKwhInTenThousands);
    this.electricalgridcostintenthousands.set(energyCostEntry.electricalGridCostInTenThousands);
    this.valueaddedtaxpercentagerateinminorunit.set(energyCostEntry.valueAddedTaxPercentageRateInMinorUnit);
  }

  onCloseClick(): void {
    this.dialogRef.close();
  }

  exportDialogData(): EnergyCostentry {
    return {
      id: this.entryId,
      toDate: this.dateToAsMoment().toDate().toLocaleDateString(),
      fromDate: this.dateFromAsMoment().toDate().toLocaleDateString(),
      feeOneInTenThousands: this.feeoneintenthousands(),
      feeTwoInTenThousands: this.feetwointenthousands(),
      feeThreeInTenThousands: this.feethreeintenthousands(),
      energyCostPerKwhInTenThousands: this.energycostperkwhintenthousands(),
      electricalGridCostInTenThousands: this.electricalgridcostintenthousands(),
      valueAddedTaxPercentageRateInMinorUnit: this.valueaddedtaxpercentagerateinminorunit()      
    }
  }
}