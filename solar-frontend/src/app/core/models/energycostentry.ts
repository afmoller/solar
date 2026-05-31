export interface EnergyCostentry {
  id?: number;
  fromDate: string;
  toDate: string;
  feeOneInTenThousands: number;
  feeTwoInTenThousands: number;
  feeThreeInTenThousands: number;
  feeFourInTenThousands: number;
  energyCostPerKwhInTenThousands: number;
  electricalGridCostInTenThousands: number;
  valueAddedTaxPercentageRateInMinorUnit: number;
}