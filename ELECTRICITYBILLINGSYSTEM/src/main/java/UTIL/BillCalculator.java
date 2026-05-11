package UTIL;

public class BillCalculator {
    //FIXED CHARGES
    private static final double fixed_charge=50.0;
    private static final double TAX_RATE=0.10;
    //Calculate bill based on units consumed
    public static double calculate(int units) {
        double amount = 0;
        //Slab rates(common electricity billing systems)
        if (units <= 100) {
            amount = units * 3.50;
        } else if (units <= 300) {
            amount = (100 * 3.50) + ((units - 100) * 5.00);
        } else if (units <= 500) {
            amount = (100 * 3.50) + (200 * 5.00) + ((units - 300) * 7.00);
        } else {
            amount = (100 * 3.50) + (200 * 5.00) + (200 * 7.00) + ((units - 500) * 9.00);
        }
        //add fixed charges

        amount += fixed_charge;

        //add tax
        amount += amount * TAX_RATE;

        return Math.round(amount * 100.0) / 100.0; // round to 2 decimals
    }
    public static String getslabinfo(int units ) {
        if (units <= 100) return "slab 1 (0-100 units)@ Rs.3.50/unit";
        else if (units <= 300) return "Slab 2 (101-300 units)@ Rs.5.00/unit ";
        else if (units <= 500) return "slab 3 (301-500 units)@ Rs.7.00/unit";
        else return "slab 4(500+ units )@9.00/units";
    }
    }

