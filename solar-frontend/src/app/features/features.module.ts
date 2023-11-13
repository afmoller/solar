import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SummaryperdayentryComponent } from './tables/summaryperdayentry/summaryperdayentry.component';
import { SummarypermonthentryComponent } from './tables/symmarypermonthentry/summarypermonthentry.component';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    SummaryperdayentryComponent,
    SummarypermonthentryComponent,
  ]
})
export class FeaturesModule { }