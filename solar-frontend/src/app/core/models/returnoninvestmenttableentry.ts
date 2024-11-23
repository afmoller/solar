export interface ReturnOnInvestmentTableEntry {
  id: number;
  date: Date;
  description: string;
  amountIsPositive: boolean;
  amountInMinorUnit: number;
  saldo: number;
  deltaSinceBegin: number;
  numberOfYearsUntilPaid: number;
}
