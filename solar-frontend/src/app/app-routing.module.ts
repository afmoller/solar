import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllComponent } from './features/diagrams/all/all.component';
import { YearOverYearComponent} from './features/diagrams/yearoveryear/year-over-year.component';
import { DataexportentryComponent } from './features/dataexportentry/dataexportentry.component';
import { AccumulatedAllComponent } from './features/diagrams/accumulated/accumulated-all.component';
import { SummaryperdayentryComponent} from './features/tables/summaryperdayentry/summaryperdayentry.component';
import { SummarypermonthentryComponent } from './features/tables/symmarypermonthentry/summarypermonthentry.component';


const routes: Routes = [
  { path: 'all', component: AllComponent },
  { path: 'accumulated-all', component: AccumulatedAllComponent },
  { path: 'dataexportentry', component: DataexportentryComponent },
  { path: 'summaryperdayentry', component: SummaryperdayentryComponent },
  { path: 'summarypermonthentry', component: SummarypermonthentryComponent },
  { path: 'year-over-year/:valuetype/:mode', component: YearOverYearComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
