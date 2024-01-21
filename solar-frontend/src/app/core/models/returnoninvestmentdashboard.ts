import { ReturnOnInvestmententry } from "./returnoninvestmententry";

export interface ReturnOnInvestmentDashboard {
  returnOnInvestmentDashboardEntryDtos: ReturnOnInvestmententry[];
  totalIncome: number;
  totalCost: number;
  dates: Date[];
  numberOfYearsUntilPaid: number[];
}
