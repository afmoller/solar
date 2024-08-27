import Annotation from 'chartjs-plugin-annotation';
import { BaseChartDirective } from 'ng2-charts';
import { MatInputModule} from '@angular/material/input';
import { MatRadioModule} from '@angular/material/radio';
import { MatButtonModule} from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren, input } from '@angular/core';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatDatepickerModule} from '@angular/material/datepicker';
import { MatTableDataSource, MatTableModule } from '@angular/material/table'
import { Chart, ChartConfiguration, ChartEvent, ChartType, Colors } from 'chart.js';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReturnOnInvestmentDashboard } from 'src/app/core/models/returnoninvestmentdashboard';
import { EnergySaleCompensationService } from 'src/app/core/services/energy-sale-compensation.service';
import { EnergySaleCompensationCreateentry } from 'src/app/core/models/energysalecompensationcreateentry';
import { EnergySaleCompensationentry } from 'src/app/core/models/energysalecompensationentry';
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
  selector: 'app-yearmonthdaydatepicker',
  templateUrl: './yearmonthdaydatepicker.component.html',
  styleUrls: ['./yearmonthdaydatepicker.component.scss'],
  standalone: true,

  imports: [
    MatInputModule,
    MatRadioModule,
    MatTableModule,
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
  ],
})

export class YearMonthDayDatePickerComponent  {
  @Input() label = '';
  @Input() initialDate = moment();
}
