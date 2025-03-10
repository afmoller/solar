import { MatInputModule} from '@angular/material/input';
import { MatFormFieldModule} from '@angular/material/form-field';
import { Summarypermonthentry } from 'src/app/core/models/summarypermonthentry';
import { SummaryPerMonthEntryService } from 'src/app/core/services/summary-per-month-entry.service';

import { 
  Component,
  OnInit
} from '@angular/core';

import {
  MatTableModule,
  MatTableDataSource
} from '@angular/material/table'

@Component({
    selector: 'app-summarypermonthentry',
    templateUrl: './summarypermonthentry.component.html',
    styleUrls: ['./summarypermonthentry.component.scss'],
    imports: [
        MatInputModule,
        MatTableModule,
        MatFormFieldModule
    ]
})
export class SummarypermonthentryComponent implements OnInit {

  dataSource = new MatTableDataSource();
  displayedColumns: string[] = ['yearMonthOfEntry',
                                'productionWattHours',
                                'saleWattHours',
                                'selfConsumptionWattHours',
                                'purchaseWattHours',
                                'consumptionWattHours',
                                'autarchy'
                              ];

  constructor(private summaryPerMonthEntryService: SummaryPerMonthEntryService) {}

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnInit() {
    this.summaryPerMonthEntryService.findAll().subscribe(data => {

      let summaryPerMonthEntries: Summarypermonthentry[] = data;

      summaryPerMonthEntries.forEach(entry => {
        entry.saleWattHours = entry.saleWattHours / 1000;
        entry.purchaseWattHours = entry.purchaseWattHours / 1000;
        entry.productionWattHours = entry.productionWattHours / 1000;
        entry.consumptionWattHours = entry.consumptionWattHours / 1000;
        entry.selfConsumptionWattHours = entry.selfConsumptionWattHours / 1000;

        entry.autarchy = Math.round((entry.autarchy + Number.EPSILON) * 100) / 100;
      });

      this.dataSource.data = summaryPerMonthEntries;
    });
  }
}
