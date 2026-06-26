package exercise7;

import java.util.HashMap;
import java.util.Map;

// ─────────────────────────────────────────────
//  Financial Forecasting — recursive + memoised
// ─────────────────────────────────────────────
public class FinancialForecasting {

    // ── 1. Pure recursion — O(n) calls, O(n) stack depth ──
    /**
     * Calculates future value after `years` at a constant annual growth rate.
     * FV(n) = FV(n-1) * (1 + rate)
     * Base case: FV(0) = presentValue
     */
    public static double futureValueRecursive(double presentValue, double rate, int years) {
        if (years == 0) return presentValue;
        return futureValueRecursive(presentValue, rate, years - 1) * (1 + rate);
    }

    // ── 2. Memoised recursion — avoids repeated sub-problems ──
    private static final Map<Integer, Double> memo = new HashMap<>();

    public static double futureValueMemo(double presentValue, double rate, int years) {
        if (years == 0) return presentValue;
        if (memo.containsKey(years)) return memo.get(years);
        double result = futureValueMemo(presentValue, rate, years - 1) * (1 + rate);
        memo.put(years, result);
        return result;
    }

    // ── 3. Iterative — O(n) time, O(1) space (best practice) ──
    public static double futureValueIterative(double presentValue, double rate, int years) {
        double fv = presentValue;
        for (int i = 0; i < years; i++) fv *= (1 + rate);
        return fv;
    }

    // ── 4. Variable growth rates (array of rates per year) ──
    public static double futureValueVariableRates(double presentValue, double[] rates, int year) {
        if (year == 0) return presentValue;
        return futureValueVariableRates(presentValue, rates, year - 1) * (1 + rates[year - 1]);
    }

    // ─────────────────────────────────────────
    //  Driver
    // ─────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("═══ Exercise 7 : Financial Forecasting ═══\n");

        double principal = 100_000.0;
        double rate      = 0.08;       // 8% annual growth
        int    years     = 10;

        System.out.printf("  Principal : ₹%.2f%n",  principal);
        System.out.printf("  Rate      : %.0f%% p.a.%n", rate * 100);
        System.out.printf("  Period    : %d years%n%n", years);

        double rv = futureValueRecursive(principal, rate, years);
        double mv = futureValueMemo(principal, rate, years);
        double iv = futureValueIterative(principal, rate, years);

        System.out.printf("  Recursive  FV → ₹%,.2f%n", rv);
        System.out.printf("  Memoised   FV → ₹%,.2f%n", mv);
        System.out.printf("  Iterative  FV → ₹%,.2f%n%n", iv);

        // Year-by-year projection
        System.out.println("  Year-by-year projection (recursive):");
        memo.clear();
        for (int y = 0; y <= years; y++) {
            System.out.printf("    Year %2d → ₹%,.2f%n", y,
                    futureValueRecursive(principal, rate, y));
        }

        // Variable-rate example
        double[] rates = {0.06, 0.07, 0.09, 0.10, 0.08};
        System.out.printf("%n  Variable rates %s over 5 years → ₹%,.2f%n",
                java.util.Arrays.toString(rates),
                futureValueVariableRates(principal, rates, rates.length));

        System.out.println("""

  ── Complexity Analysis ──
  Pure Recursive : Time O(n) | Space O(n) [call stack]
  Memoised       : Time O(n) | Space O(n) [memo map]  — avoids recomputation for branching recursion
  Iterative      : Time O(n) | Space O(1) — BEST for this linear recurrence

  Recursion risk: StackOverflowError for very large `years` (default JVM stack ~500-1000 frames).
  Optimisation   : Use iterative or tail-call style; for compound interest Math.pow() gives O(1).
  Math.pow form  : FV = PV * (1 + r)^n  → O(log n) via fast exponentiation
""");
    }
}
