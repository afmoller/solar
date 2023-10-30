import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataexportentryComponent } from './dataexportentry/dataexportentry.component';
import { SummaryperdayentryComponent } from './summaryperdayentry/summaryperdayentry.component';
import { AccumulatedAllComponent } from './diagrams/accumulated/accumulated-all.component';
import { YearOverYearProductionComponent} from './features/diagrams/yearoveryear/year-over-year-production.component';

@NgModule({
  declarations: [
    DataexportentryComponent,
    SummaryperdayentryComponent,
    AccumulatedAllComponent,
    YearOverYearProductionComponent
  ],
  imports: [
    CommonModule
  ]
})
export class FeaturesModule { }
