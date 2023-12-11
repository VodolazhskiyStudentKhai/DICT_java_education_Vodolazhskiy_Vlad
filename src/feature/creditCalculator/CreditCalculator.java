package feature.creditCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CreditCalculator {
    public static void main(String[] args) {
        Properties sp = System.getProperties();

        String t = sp.getProperty("type", "");

        double lp, li, ap;
        int n;

        lp = Double.parseDouble(sp.getProperty("principal", "0"));
        n = Integer.parseInt(sp.getProperty("periods", "0"));
        li = Double.parseDouble(sp.getProperty("interest", "-1"));

        switch(t) {
            case "diff":
                CreditOperations.getDiff(lp, n, li);
                break;
            case "annuity":
                if(sp.size() < 4) {
                    System.out.println("Incorrect parameters");
                    return;
                }
                ap = Double.parseDouble(sp.getProperty("payment", "0"));
                CreditOperations.getAnnuity(lp, n, li, ap);
                break;

            default: System.out.println("Incorrect parameters");
        }
    }
}

class CreditOperations {

    public static void getAnnuity(Double lp, Integer n, Double li, Double ap) {
        if (lp < 0 || n < 0 || li < 0 || ap < 0) {
            System.out.println("Incorrect parameters.");
            return;
        }

        double i = getI(li);

        if (n == 0) {
            getNumberOfMonths(lp, ap, i);
        } else if (lp == 0.0) {
            getLoanPrincipal(ap, n, i);
        } else if (ap == 0.0) {
            getNumberOfAnnuity(lp, n, i);
        }
    }

    public static void getDiff(Double lp, Integer n, Double li) {
        if (lp <= 0 || n <= 0 || li <= 0) {
            System.out.println("Incorrect parameters.");
            return;
        }

        List<Double> result = getD(lp, n, li);

        for (int i = 0; i < n; i++) {
            System.out.printf("Month %d: payment is %.2f\n", i + 1, result.get(i));
        }
        System.out.printf("Overpayment = %.2f\n", Math.ceil(result.stream().mapToDouble(Double::doubleValue).sum() - lp));
    }

    public static void getNumberOfAnnuity(Double lp, Integer n, Double i) {
        double result = Math.ceil(lp * ((i * Math.pow(1 + i, n)) / (Math.pow(1 + i, n) - 1)));
        System.out.printf("Your annuity payment = %.2f!\n", result);
    }

    public static void getLoanPrincipal(Double ap, Integer n, Double i) {
        double result = Math.floor(ap / ((i * Math.pow(1 + i, n)) / (Math.pow(1 + i, n) - 1)));
        System.out.printf("Your loan principal = %.2f!\n", result);
    }

    public static void getNumberOfMonths(Double lp, Double mp, Double i) {
        int result = (int) Math.ceil(Math.log(mp / (mp - i * lp)) / Math.log(1 + i));
        System.out.println(getMonthsMessage(result));
    }

    private static Double getI(Double li) {
        return li / (12 * 100.0);
    }

    private static List<Double> getD(Double lp, Integer n, Double li) {
        double i = getI(li);
        List<Double> result = new ArrayList<>();
        for (int j = 1; j <= n; j++) {
            result.add(Math.ceil(lp / n + (i * (lp - (lp * (j - 1)) / n))));
            // ...
        }
        return result;
    }

    private static String getMonthsMessage(Integer months) {
        StringBuilder message = new StringBuilder("It will take ");
        if (months >= 12) {
            int y = months / 12;
            message.append(y).append(" ").append(y > 1 ? "years" : "year");
            // ...
        }
        int m = months % 12;
        if (m != 0) {
            message.append(" and ").append(m).append(" ").append(m > 1 ? "months" : "month");
            // ...
        }
        message.append(" to repay this loan!");
        return message.toString();
    }
}
