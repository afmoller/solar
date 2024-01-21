import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table'
import { MatInputModule} from '@angular/material/input';
import { MatFormFieldModule} from '@angular/material/form-field';
import { ReturnOnInvestmentService } from 'src/app/core/services/return-on-investment.service';
import { ReturnOnInvestmentDashboard } from 'src/app/core/models/returnoninvestmentdashboard';

@Component({
  selector: 'app-returnoninvestment',
  templateUrl: './returnoninvestment.component.html',
  styleUrls: ['./returnoninvestment.component.scss'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule]
})
export class ReturnOnInvestmentComponent implements OnInit {

  returnOnInvestmentDashboard!: ReturnOnInvestmentDashboard;
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
    this.returnOnInvestmentService.find().subscribe(data => {
      this.returnOnInvestmentDashboard = data;
      this.dataSource.data = data.returnOnInvestmentDashboardEntryDtos;
    });
  }


  getValueIfPositive(amount: number, isPositive: boolean): string {
    if (isPositive) {
      return this.formatValue(amount);      
    } else {
      return '';
    }
  }

  getValueIfNegative(amount: number, isPositive: boolean): string {
    if (!isPositive) {
      return '-' + this.formatValue(amount);
    } else {
      return '';
    }
  }

  formatValue(amountInMinor: number): string {
    if (amountInMinor === 0) {
      return amountInMinor.toString();
    }

    let amountAsUnformattedString = amountInMinor.toString();
      
    let majorPart: string = amountAsUnformattedString.substring(0, amountAsUnformattedString.length - 2);
    let minorPart: string = amountAsUnformattedString.substring(amountAsUnformattedString.length - 2); 

     return majorPart + '.' + minorPart;
  }
}
