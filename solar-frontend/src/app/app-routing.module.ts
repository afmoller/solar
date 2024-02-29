import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllComponent } from './features/diagrams/all/all.component';
import { YearOverYearComponent} from './features/diagrams/yearoveryear/year-over-year.component';
import { DataexportentryComponent } from './features/dataexportentry/dataexportentry.component';
import { AccumulatedAllComponent } from './features/diagrams/accumulated/accumulated-all.component';
import { SummaryperdayentryComponent} from './features/tables/summaryperdayentry/summaryperdayentry.component';
import { ReturnOnInvestmentComponent } from './features/dashboards/returnoninvestment/returnoninvestment.component';
import { SummarypermonthentryComponent } from './features/tables/symmarypermonthentry/summarypermonthentry.component';
import { EnergySaleCompensationComponent } from './features/dashboards/energysalecompensation/energysalecompensation.component';

const routes: Routes = [
  { path: 'all', component: AllComponent },
  { path: 'accumulated-all', component: AccumulatedAllComponent },
  { path: 'dataexportentry', component: DataexportentryComponent },
  { path: 'summaryperdayentry', component: SummaryperdayentryComponent },
  { path: 'return-on-investment', component: ReturnOnInvestmentComponent },
  { path: 'summarypermonthentry', component: SummarypermonthentryComponent },
  { path: 'year-over-year/:valuetype/:mode', component: YearOverYearComponent },
  { path: 'energy-sale-compensation', component: EnergySaleCompensationComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
