import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table'
import { MatInputModule} from '@angular/material/input';
import { MatFormFieldModule} from '@angular/material/form-field';
import { SummaryPerMonthEntryService } from 'src/app/core/services/summary-per-month-entry.service';

@Component({
  selector: 'app-summarypermonthentry',
  templateUrl: './summarypermonthentry.component.html',
  styleUrls: ['./summarypermonthentry.component.scss'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule]
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

  constructor(
    private summaryPerMonthEntryService: SummaryPerMonthEntryService
  ) {}

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnInit() {
    this.summaryPerMonthEntryService.findAll().subscribe(data => {
      this.dataSource.data = data;
    });
  }
}
