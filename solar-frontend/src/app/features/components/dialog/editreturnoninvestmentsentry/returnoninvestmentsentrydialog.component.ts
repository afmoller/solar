import { DatePipe } from "@angular/common";
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule} from '@angular/material/radio';
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { MomentDateAdapter } from "@angular/material-moment-adapter";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ReturnOnInvestmentEntry } from "src/app/core/models/returnoninvestmententry";
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
  selector: 'app-returnoninvestmentsentrydialog',
  templateUrl: 'returnoninvestmentsentrydialog.component.html',
  styleUrls: ['returnoninvestmentsentrydialog.component.scss'],
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

export class ReturnOnInvestmentEntryDialogComponent {

  readonly dialogRef = inject(MatDialogRef<ReturnOnInvestmentEntryDialogComponent>);
    
  dateAsMoment = signal<Moment>(moment());
  description = signal<string>('');
  amountIsPositive = signal<boolean>(true);
  amountInMinorUnit = signal<number>(0);
  entryId: number | undefined;

  constructor() {
  }
 
  public initiateDialog(returnOnInvestmentEntry: ReturnOnInvestmentEntry) {
    this.entryId = returnOnInvestmentEntry.id;
    this.dateAsMoment.set(moment(returnOnInvestmentEntry.date));
    this.description.set(returnOnInvestmentEntry.description);
    this.amountIsPositive.set(returnOnInvestmentEntry.amountIsPositive);
    this.amountInMinorUnit.set(returnOnInvestmentEntry.amountInMinorUnit);
  }

  onCloseClick(): void {
    this.dialogRef.close();
  }

  exportDialogData(): ReturnOnInvestmentEntry {
    return {
      id: this.entryId,
      date: this.dateAsMoment().toDate().toLocaleDateString(),
      description: this.description(),
      amountInMinorUnit: this.amountInMinorUnit(),
      amountIsPositive: this.amountIsPositive()
    }
  }

  setAmountIsPositive(amountIsPositive: boolean) {
    this.amountIsPositive.set(amountIsPositive);
  }
}