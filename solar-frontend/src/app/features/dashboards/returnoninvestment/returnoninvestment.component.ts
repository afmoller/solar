import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table'
import { MatInputModule} from '@angular/material/input';
import { MatFormFieldModule} from '@angular/material/form-field';
import { ReturnOnInvestmentService } from 'src/app/core/services/return-on-investment.service';

@Component({
  selector: 'app-returnoninvestment',
  templateUrl: './returnoninvestment.component.html',
  styleUrls: ['./returnoninvestment.component.scss'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule]
})
export class ReturnOnInvestmentComponent implements OnInit {

  dataSource = new MatTableDataSource();
  displayedColumns: string[] = ['date',
                                'cost',
                                'income',
                                'description',
                                'saldo',
                                'deltaSinceStart',
                                'numberOfYearsUntilPaid',
                              ];

  constructor(
    private returnOnInvestmentService: ReturnOnInvestmentService
  ) {}

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnInit() {
    this.returnOnInvestmentService.findAll().subscribe(data => {
      this.dataSource.data = data;
    });
  }


  getValueIfPositive(amount: number, isPositive: boolean): string {
    if (isPositive) {
      return amount.toString();
    } else {
      return '';
    }
  }

  getValueIfNegative(amount: number, isPositive: boolean): string {
    if (!isPositive) {
      return '-' + amount.toString();
    } else {
      return '';
    }
  }
}


