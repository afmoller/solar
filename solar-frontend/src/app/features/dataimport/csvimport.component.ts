import { Subscription, finalize } from 'rxjs';
import { Component, Input } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CsvImportResult } from 'src/app/core/models/csvimportresult';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatButtonModule, MatIconButton } from '@angular/material/button';
import { SummaryPerDayEntryService } from 'src/app/core/services/summary-per-day-entry.service';

interface Year {
  value: number;
  viewValue: string;
}

interface Month {
  value: number;
  viewValue: string;
}

@Component({
  selector: 'app-csvimport',
  templateUrl: './csvimport.component.html',
  styleUrls: ['./csvimport.component.scss'],
  standalone: true,

  imports: [
    MatIconButton,
    MatIconModule,
    MatButtonModule,
    MatSelectModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatProgressBarModule
  ]
})

export class CsvImportComponent  {

  years: Year[] = [
    {value: 2024, viewValue: '2024'},
    {value: 2025, viewValue: '2025'},
    {value: 2026, viewValue: '2026'},
  ];

  months: Month[] = [
    {value: 1, viewValue: 'Jan'},
    {value: 2, viewValue: 'Feb'},
    {value: 3, viewValue: 'Mar'},
    {value: 4, viewValue: 'Apr'},
    {value: 5, viewValue: 'May'},
    {value: 6, viewValue: 'Jun'},
    {value: 7, viewValue: 'Jul'},
    {value: 8, viewValue: 'Aug'},
    {value: 9, viewValue: 'Sep'},
    {value: 10, viewValue: 'Oct'},
    {value: 11, viewValue: 'Nov'},
    {value: 12, viewValue: 'Dec'},
  ];

  private baseUrl: string = environment.backendApiHost;

  @Input()
  requiredFileType:string;

  fileName = '';
  uploadProgress:number;
  aggregationResult?: number;

  selectedYear?: number;
  selectedMonth?: number;

  uploadSub: Subscription = new Subscription;
  csvImportResult?: CsvImportResult | null;

  constructor(
    private http: HttpClient,
    private summaryPerDayEntryService: SummaryPerDayEntryService
  ) {
    this.requiredFileType = 'text/csv';
    this.uploadProgress = 0;
  }

  onFileSelected(event: any) {
    const file:File = event.target.files[0];

    if (file) {
      this.fileName = file.name;
      const fileToUpload = new FormData();

      fileToUpload.append('csvFile', new Blob([file], { type: 'multipart/form-data;' }), file.name);

      const upload$ = this.http
      .post<CsvImportResult>(this.baseUrl + "/api/v1/importCsvFileToDatabase", fileToUpload,
          {
            reportProgress: true,
            observe: 'events'
          }
        )
      .pipe(finalize(() => this.reset()));

      this.uploadSub = upload$.subscribe(event => {
        if (HttpEventType.UploadProgress == event.type) {
          if (event.total) {
            this.uploadProgress = Math.round(100 * (event.loaded / event.total));
          }
        } else if (HttpEventType.Response == event.type) {
          this.csvImportResult = event.body;
        }
      })
    }
  }

  reset() {
    this.uploadProgress = 0;
    this.uploadSub.unsubscribe();
  }

  aggregate(): void {
    if (this.selectedYear && this.selectedMonth) {
      this.summaryPerDayEntryService.populateSummaryPerDayForYearAndMonth(this.selectedYear, this.selectedMonth).subscribe(data => {
        this.aggregationResult = data;
      })
    }
  }

  aggregationDisabled(): boolean {
    if (this.selectedYear && this.selectedMonth) {
      return false;
    } else {
      return true;
    }
  }
}
