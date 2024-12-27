import { DatePipe } from "@angular/common";
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from "@angular/material/input";
import { MatRadioModule } from '@angular/material/radio';
import { MatButtonModule } from "@angular/material/button";
import { MatTooltipModule } from '@angular/material/tooltip';
import { MomentDateAdapter } from "@angular/material-moment-adapter";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatDatepickerModule } from '@angular/material/datepicker';
import moment, { Moment } from 'moment';

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
import { EnergySaleCompensationentry } from "src/app/core/models/energysalecompensationentry";

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
    selector: 'app-energysalecompensationentrydialog',
    templateUrl: 'energysalecompensationentrydialog.component.html',
    styleUrls: ['energysalecompensationentrydialog.component.scss'],
    imports: [
        FormsModule,
        MatIconModule,
        MatDialogClose,
        MatInputModule,
        MatRadioModule,
        MatButtonModule,
        MatDialogActions,
        MatDialogContent,
        MatTooltipModule,
        MatFormFieldModule,
        MatDatepickerModule,
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

export class EnergySaleCompensationEntryDialogComponent {

  readonly dialogRef = inject(MatDialogRef<EnergySaleCompensationEntryDialogComponent>);
    
  entryId: number | undefined;
  compensationDateAsMoment = signal<Moment>(moment());
  compensationAmountInMinorUnit = signal<number>(0);
  productionYear = signal<number>(0);
  productionFromDateAsMoment = signal<Moment>(moment());
  productionToDateAsMoment = signal<Moment>(moment());
  
  constructor() {
  }
 
  public initiateDialog(energySaleCompensationentry: EnergySaleCompensationentry) {
    this.entryId = energySaleCompensationentry.id;
    this.compensationDateAsMoment.set(moment(energySaleCompensationentry.compensationDate));
    this.compensationAmountInMinorUnit.set(energySaleCompensationentry.compensationAmountInMinorUnit);
    this.productionYear.set(energySaleCompensationentry.productionYear);
    this.productionFromDateAsMoment.set(moment(energySaleCompensationentry.productionFromDate));
    this.productionToDateAsMoment.set(moment(energySaleCompensationentry.productionToDate));
  }

  onCloseClick(): void {
    this.dialogRef.close();
  }

  exportDialogData(): EnergySaleCompensationentry {
    return {
      id: this.entryId,
      compensationDate: this.compensationDateAsMoment().toDate().toLocaleDateString(),
      compensationAmountInMinorUnit: this.compensationAmountInMinorUnit(),
      productionYear: this.productionYear(),
      productionFromDate: this.productionFromDateAsMoment().toDate().toLocaleDateString(),
      productionToDate: this.productionToDateAsMoment().toDate().toLocaleDateString()
    }
  }
}