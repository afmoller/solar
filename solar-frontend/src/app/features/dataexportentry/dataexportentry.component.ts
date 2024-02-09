import { Component } from '@angular/core';
import { Dataexportentry } from '../../core/models/dataexportentry';
import { DataExportEntryService } from '../../core/services/data-export-entry.service';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-dataexportentry',
  templateUrl: './dataexportentry.component.html',
  styleUrls: ['./dataexportentry.component.scss']
})
export class DataexportentryComponent  {

  dataexportentry: Dataexportentry | undefined;

  constructor(
    private formBuilder: FormBuilder,
    private dataexportentryservice: DataExportEntryService
  ) {}

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
