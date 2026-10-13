import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { Dataexportentry } from 'app/core/models/dataexportentry';
import { DataExportEntryService } from 'app/core/services/data-export-entry.service';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

@Component({
    selector: 'app-dataexportentry',
    templateUrl: './dataexportentry.component.html',
    styleUrls: ['./dataexportentry.component.scss'],
    changeDetection: ChangeDetectionStrategy.Eager,
    imports: [
        ReactiveFormsModule
    ]
})
export class DataexportentryComponent  {

  private formBuilder = inject(FormBuilder);
  private dataexportentryservice = inject(DataExportEntryService);

  dataexportentry: Dataexportentry | undefined;

  inputForm = this.formBuilder.group({
    iid: ''
  });

  onSubmit(): void {
    this.dataexportentryservice.findByIid(this.inputForm.get('iid')?.value).subscribe(data => {
      this.dataexportentry = data;
    });
    this.inputForm.reset();
  }
}
