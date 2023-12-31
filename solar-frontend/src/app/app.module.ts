import { NgModule } from '@angular/core';
import { NgChartsModule } from 'ng2-charts';
import { AppComponent } from './app.component';
import { NgChartsConfiguration } from 'ng2-charts';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { BrowserModule } from '@angular/platform-browser';
import { MatFormFieldModule} from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AllComponent } from './features/diagrams/all/all.component';
import { YearOverYearComponent} from './features/diagrams/yearoveryear/year-over-year.component';
import { DataexportentryComponent} from './features/dataexportentry/dataexportentry.component';
import { AccumulatedAllComponent } from './features/diagrams/accumulated/accumulated-all.component';
import { SummaryperdayentryComponent} from './features/tables/summaryperdayentry/summaryperdayentry.component';
import { SummarypermonthentryComponent } from './features/tables/symmarypermonthentry/summarypermonthentry.component';

@NgModule({
  declarations: [
    AppComponent,
    AllComponent,
    YearOverYearComponent,
    AccumulatedAllComponent,
    DataexportentryComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    MatTableModule,
    NgChartsModule,
    HttpClientModule,
    AppRoutingModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    SummaryperdayentryComponent,
    SummarypermonthentryComponent
  ],
  providers: [
    { provide: NgChartsConfiguration, useValue: { generateColors: false }}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
