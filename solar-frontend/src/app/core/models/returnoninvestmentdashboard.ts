import { ReturnOnInvestmentTableEntry } from "./returnoninvestmenttableentry";

export interface ReturnOnInvestmentDashboard {
  returnOnInvestmentDashboardEntryDtos: ReturnOnInvestmentTableEntry[];
  totalIncome: number;
  totalCost: number;
  dates: Date[];
  numberOfYearsUntilPaid: number[];
}
