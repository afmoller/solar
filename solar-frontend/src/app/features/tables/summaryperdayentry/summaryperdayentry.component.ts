import { FormBuilder } from '@angular/forms';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table'
import { MatInputModule} from '@angular/material/input';
import { MatFormFieldModule} from '@angular/material/form-field';
import { Summaryperdayentry } from '../../../core/models/summaryperdayentry';
import { SummaryPerDayEntryService } from '../../../core/services/summary-per-day-entry.service';

@Component({
  selector: 'app-summaryperdayentry',
  templateUrl: './summaryperdayentry.component.html',
  styleUrls: ['./summaryperdayentry.component.scss'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule]
})
export class SummaryperdayentryComponent implements OnInit {

  dataSource = new MatTableDataSource();
  displayedColumns: string[] = ['date',
                                'productionWattHours',
                                'saleWattHours',
                                'selfConsumptionWattHours',
                                'purchaseWattHours',
                                'consumptionWattHours',
                                'autarchy',
                                'accumulatedProductionWattHours',
                                'accumulatedSaleWattHours',
                                'accumulatedSelfConsumptionWattHours',
                                'accumulatedPurchaseWattHours',
                                'accumulatedConsumptionWattHours',
                                'yearOfEntry',
                                'monthOfEntry'];

  constructor(
    private summaryPerDayEntryService: SummaryPerDayEntryService
  ) {}

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnInit() {
    this.summaryPerDayEntryService.findAll().subscribe(data => {
      this.dataSource.data = data;
    });
  }
}
