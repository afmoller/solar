import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DataexportentryComponent } from './features/dataexportentry/dataexportentry.component';
import { AccumulatedAllComponent } from './features/diagrams/accumulated/accumulated-all.component';
import { SummaryperdayentryComponent} from './features/summaryperdayentry/summaryperdayentry.component';
import { YearOverYearProductionComponent} from './features/diagrams/yearoveryear/year-over-year-production.component';

const routes: Routes = [
  { path: 'dataexportentry', component: DataexportentryComponent },
  { path: 'summaryperdayentry', component: SummaryperdayentryComponent },
  { path: 'accumulated-all', component: AccumulatedAllComponent },
  { path: 'year-over-year-production', component: YearOverYearProductionComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
