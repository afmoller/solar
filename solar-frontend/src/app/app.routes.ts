import { Routes } from '@angular/router';
import { AllComponent } from './features/diagrams/all/all.component';
import { CsvImportComponent } from './features/dataimport/csvimport.component';
import { LoginLogoutComponent } from './common/loginlogout/loginlogout.component';
import { WeatherComponent } from './features/dashboards/weather/weather.component';
import { WeatherAllComponent } from './features/diagrams/weather/weather-all.component';
import { EnergyCostComponent } from './features/dashboards/energycosts/energycost.component';
import { DataexportentryComponent } from './features/dataexportentry/dataexportentry.component';
import { YearOverYearComponent} from './features/diagrams/yearoveryear/year-over-year.component';
import { AccumulatedAllComponent } from './features/diagrams/accumulated/accumulated-all.component';
import { SummaryperdayentryComponent} from './features/tables/summaryperdayentry/summaryperdayentry.component';
import { ReturnOnInvestmentComponent } from './features/dashboards/returnoninvestment/returnoninvestment.component';
import { SummarypermonthentryComponent } from './features/tables/symmarypermonthentry/summarypermonthentry.component';
import { EnergySaleCompensationComponent } from './features/dashboards/energysalecompensation/energysalecompensation.component';

export const routes: Routes = [
  {
    path: 'all',
    title: 'all',
    component: AllComponent,
  },
  {
    path: 'csv-import',
    title:'csv-import',
    component: CsvImportComponent,
  },
  {
    path: 'energy-cost',
    title:'energy-cost',
    component: EnergyCostComponent,
  },
  {
    path: 'login',
    title:'login',
    component: LoginLogoutComponent,
  },
  {
    path: 'accumulated-all',
    title:'accumulated-all',
    component: AccumulatedAllComponent,
  },
  {
    path: 'dataexportentry',
    title:'dataexportentry',
    component: DataexportentryComponent,
  },
  {
    path: 'summaryperdayentry',
    title:'summaryperdayentry',
    component: SummaryperdayentryComponent,
  },
  {
     path: 'return-on-investment',
     title:'return-on-investment',
     component: ReturnOnInvestmentComponent,
  },
  {
    path: 'summarypermonthentry',
    title:'summarypermonthentry',
    component: SummarypermonthentryComponent,
  },
  { path: 'year-over-year/:valuetype/:mode',
    title:'year-over-year',
    component: YearOverYearComponent,
  },
  {
    path: 'energy-sale-compensation',
    title:'energy-sale-compensation',
    component: EnergySaleCompensationComponent,
  },
  {
    path: 'weather',
    title:'weather',
    component: WeatherComponent,
  },
  {
    path: 'weather-all',
    title:'weather-all',
    component: WeatherAllComponent,
  }
];
