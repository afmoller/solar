export interface EnergyCostCreateentry {
  fromDate: string;
  toDate: string;
  feeOneInTenThousands: number;
  feeTwoInTenThousands: number;
  energyCostPerKwhInTenThousands: number;
  electricalGridCostInTenThousands: number;
  valueAddedTaxPercentageRateInMinorUnit: number;
}
