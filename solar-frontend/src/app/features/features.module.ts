import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataexportentryComponent } from './dataexportentry/dataexportentry.component';
import { SummaryperdayentryComponent } from './tables/summaryperdayentry/summaryperdayentry.component';
import { AccumulatedAllComponent } from './diagrams/accumulated/accumulated-all.component';
import { YearOverYearComponent} from './diagrams/yearoveryear/year-over-year.component';
import { SummarypermonthentryComponent } from './tables/symmarypermonthentry/summarypermonthentry.component';

@NgModule({
  declarations: [
    DataexportentryComponent,
    SummaryperdayentryComponent,
    SummarypermonthentryComponent
    AccumulatedAllComponent,
    YearOverYearComponent
  ],
  imports: [
    CommonModule
  ]
})
export class FeaturesModule { }
