import { MatInputModule} from '@angular/material/input';
import { MatFormFieldModule} from '@angular/material/form-field';
import { Summaryperdayentry } from 'src/app/core/models/summaryperdayentry';
import { SummaryPerDayEntryService } from '../../../core/services/summary-per-day-entry.service';

import {
  OnInit,
  Component
} from '@angular/core';

import { 
  MatTableModule,
  MatTableDataSource
} from '@angular/material/table'

@Component({
    selector: 'app-summaryperdayentry',
    templateUrl: './summaryperdayentry.component.html',
    styleUrls: ['./summaryperdayentry.component.scss'],
    imports: [
        MatInputModule,
        MatTableModule,
        MatFormFieldModule
    ]
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
      let summaryPerDayEntries: Summaryperdayentry[] = data;

      summaryPerDayEntries.forEach(entry => {
        entry.accumulatedSaleWattHours = entry.accumulatedSaleWattHours / 1000;
        entry.accumulatedPurchaseWattHours = entry.accumulatedPurchaseWattHours / 1000;
        entry.accumulatedProductionWattHours = entry.accumulatedProductionWattHours / 1000;
        entry.accumulatedConsumptionWattHours = entry.accumulatedConsumptionWattHours / 1000;
        entry.accumulatedSelfConsumptionWattHours = entry.accumulatedSelfConsumptionWattHours / 1000;

        entry.saleWattHours = entry.saleWattHours / 1000;
        entry.purchaseWattHours = entry.purchaseWattHours / 1000;
        entry.productionWattHours = entry.productionWattHours / 1000;
        entry.consumptionWattHours = entry.consumptionWattHours / 1000;
        entry.selfConsumptionWattHours = entry.selfConsumptionWattHours / 1000;

        entry.autarchy = Math.round((entry.autarchy + Number.EPSILON) * 100) / 100;
      });

      this.dataSource.data = summaryPerDayEntries.reverse();
    });
  }
}
