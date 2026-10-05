import java.util.*;

public class StockTradingPlatform {

    static Scanner sc = new Scanner(System.in);

    // Stock prices
    static Map<String, Double> stocks = new HashMap<>();

    // User holdings
    static Map<String, Integer> portfolio = new HashMap<>();

    static double cash = 10000.0;

    // Transaction history
    static ArrayList<String> transactions = new ArrayList<>();

    public static void main(String[] args) {

        // Add sample stocks
        stocks.put("AAPL", 180.0);
        stocks.put("GOOG", 140.0);
        stocks.put("MSFT", 420.0);
        stocks.put("AMZN", 175.0);
        stocks.put("TSLA", 250.0);

        int choice;

        do {
            System.out.println("\n===== STOCK TRADING PLATFORM =====");
            System.out.println("1. View Stocks");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewStocks();
                    break;

                case 2:
                    buyStock();
                    break;

                case 3:
                    sellStock();
                    break;

                case 4:
                    viewPortfolio();
                    break;

                case 5:
                    viewTransactions();
                    break;

                case 6:
                    System.out.println("Thank you for using the Stock Trading Platform!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }

    // Display available stocks
    static void viewStocks() {

        System.out.println("\n----- AVAILABLE STOCKS -----");

        for (Map.Entry<String, Double> entry : stocks.entrySet()) {
            System.out.println(
                entry.getKey() + " : $" +
                String.format("%.2f", entry.getValue())
            );
        }

        System.out.println("Available Cash: $" +
                String.format("%.2f", cash));
    }

    // Buy stock
    static void buyStock() {

        System.out.print("Enter stock symbol: ");
        String symbol = sc.next().toUpperCase();

        if (!stocks.containsKey(symbol)) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        if (quantity <= 0) {
            System.out.println("Invalid quantity!");
            return;
        }

        double price = stocks.get(symbol);
        double totalCost = price * quantity;

        if (totalCost > cash) {
            System.out.println("Insufficient cash!");
            return;
        }

        cash -= totalCost;

        portfolio.put(
            symbol,
            portfolio.getOrDefault(symbol, 0) + quantity
        );

        transactions.add(
            "Bought " + quantity + " shares of " +
            symbol + " for $" + String.format("%.2f", totalCost)
        );

        System.out.println("Stock purchased successfully!");
    }

    // Sell stock
    static void sellStock() {

        System.out.print("Enter stock symbol: ");
        String symbol = sc.next().toUpperCase();

        if (!portfolio.containsKey(symbol)) {
            System.out.println("You do not own this stock!");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        int owned = portfolio.get(symbol);

        if (quantity <= 0 || quantity > owned) {
            System.out.println("Invalid quantity!");
            return;
        }

        double price = stocks.get(symbol);
        double totalValue = price * quantity;

        cash += totalValue;

        if (quantity == owned) {
            portfolio.remove(symbol);
        } else {
            portfolio.put(symbol, owned - quantity);
        }

        transactions.add(
            "Sold " + quantity + " shares of " +
            symbol + " for $" + String.format("%.2f", totalValue)
        );

        System.out.println("Stock sold successfully!");
    }

    // Display portfolio
    static void viewPortfolio() {

        System.out.println("\n----- MY PORTFOLIO -----");

        if (portfolio.isEmpty()) {
            System.out.println("No stocks owned.");
        } else {

            double totalPortfolioValue = 0;

            for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {

                String symbol = entry.getKey();
                int quantity = entry.getValue();
                double price = stocks.get(symbol);

                double value = quantity * price;

                totalPortfolioValue += value;

                System.out.println(
                    symbol + " | Quantity: " +
                    quantity + " | Value: $" +
                    String.format("%.2f", value)
                );
            }

            System.out.println("------------------------");
            System.out.println("Stock Value: $" +
                    String.format("%.2f", totalPortfolioValue));
        }

        System.out.println("Cash: $" +
                String.format("%.2f", cash));

        double totalValue = cash;

        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            totalValue += stocks.get(entry.getKey()) * entry.getValue();
        }

        System.out.println("Total Portfolio Value: $" +
                String.format("%.2f", totalValue));
    }

    // Display transaction history
    static void viewTransactions() {

        System.out.println("\n----- TRANSACTION HISTORY -----");

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }
}