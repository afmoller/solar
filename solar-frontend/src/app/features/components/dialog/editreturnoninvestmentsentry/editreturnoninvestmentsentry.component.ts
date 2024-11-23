import { Component, inject, signal } from "@angular/core";
import { FormsModule, ReactiveFormsModule, } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from '@angular/material/icon';
import { MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatDatepickerModule} from '@angular/material/datepicker';
import { MatRadioModule} from '@angular/material/radio';
import {
  DateAdapter,
  MAT_DATE_LOCALE,
  MAT_DATE_FORMATS
} from "@angular/material/core";
import { MomentDateAdapter } from "@angular/material-moment-adapter";
import { DatePipe } from "@angular/common";
import { ReturnOnInvestmentService } from "src/app/core/services/return-on-investment.service";
import { ReturnOnInvestmentEntry } from "src/app/core/models/returnoninvestmententry";

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
  selector: 'editreturnoninvestmentsentry',
  templateUrl: 'editreturnoninvestmentsentry.component.html',
  styleUrls: ['editreturnoninvestmentsentry.component.scss'],
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatDatepickerModule,
    MatRadioModule,
    ReactiveFormsModule,
    MatIconModule
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
export class ReturnOnInvestmentEntryDialog {
  readonly dialogRef = inject(MatDialogRef<ReturnOnInvestmentEntryDialog>);
    
  date = signal<string>(new Date().toISOString());
  description = signal<string>('');
  amountIsPositive = signal<boolean>(true);
  amountInMinorUnit = signal<number>(0);
  entryId: number | undefined;

  constructor(
    private returnOnInvestmentService: ReturnOnInvestmentService) {
  }
 
  public initiateDialog(returnOnInvestmentEntryId: number) {
    this.returnOnInvestmentService.get(returnOnInvestmentEntryId).subscribe(returnOnInvestmentEntry => {
      this.entryId = returnOnInvestmentEntry.id;
      this.date.set(returnOnInvestmentEntry.date);
      this.description.set(returnOnInvestmentEntry.description);
      this.amountIsPositive.set(returnOnInvestmentEntry.amountIsPositive);
      this.amountInMinorUnit.set(returnOnInvestmentEntry.amountInMinorUnit);
    });
  }

  onCloseClick(): void {
    this.dialogRef.close();
  }

  exportDialogData(): ReturnOnInvestmentEntry {
    return {
      id: this.entryId,
      date: this.date(),
      description: this.description(),
      amountInMinorUnit: this.amountInMinorUnit(),
      amountIsPositive: this.amountIsPositive()
    }
  }
}