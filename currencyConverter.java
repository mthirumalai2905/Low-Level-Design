import java.util.*;

public class CurrencyConverter {
    private static class ConversionEdge {
        String from;
        String to;
        double rate;

        ConversionEdge(String from, String to, double rate) {
            this.from = from;
            this.to = to;
            this.rate = rate;
        }
    }

    public static double[] convertCurrencies(String[][] rates, String[][] queries) {
        Map<String, Map<String, Double>> conversionGraph = buildConversionGraph(rates);
        
        double[] results = new double[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String sourceCurrency = queries[i][0];
            String targetCurrency = queries[i][1];
            results[i] = findConversionRate(conversionGraph, sourceCurrency, targetCurrency);
        }
        
        return results;
    }

    private static Map<String, Map<String, Double>> buildConversionGraph(String[][] rates) {
        Map<String, Map<String, Double>> graph = new HashMap<>();
        
        for (String[] rate : rates) {
            String from = rate[0];
            String to = rate[1];
            double conversionRate = Double.parseDouble(rate[2]);
            
            graph.putIfAbsent(from, new HashMap<>());
            graph.get(from).put(to, conversionRate);
            
            graph.putIfAbsent(to, new HashMap<>());
            graph.get(to).put(from, 1.0 / conversionRate);
        }
        
        return graph;
    }

    private static double findConversionRate(Map<String, Map<String, Double>> graph, 
                                          String source, String target) {
        if (!graph.containsKey(source) || !graph.containsKey(target)) {
            return -1.0;
        }
        if (source.equals(target)) {
            return 1.0;
        }

        Queue<String> queue = new LinkedList<>();
        Map<String, Double> cumulativeRates = new HashMap<>();
        
        queue.offer(source);
        cumulativeRates.put(source, 1.0);
        
        while (!queue.isEmpty()) {
            String currentCurrency = queue.poll();
            double currentRate = cumulativeRates.get(currentCurrency);
            
            for (Map.Entry<String, Double> neighbor : graph.get(currentCurrency).entrySet()) {
                String nextCurrency = neighbor.getKey();
                double conversionRate = neighbor.getValue();
                
                if (!cumulativeRates.containsKey(nextCurrency)) {
                    double newRate = currentRate * conversionRate;
                    cumulativeRates.put(nextCurrency, newRate);
                    queue.offer(nextCurrency);
                    
                    if (nextCurrency.equals(target)) {
                        return newRate;
                    }
                }
            }
        }
        
        return -1.0;
    }

    public static void main(String[] args) {
        String[][] rates1 = {
            {"USD", "EUR", "0.85"},
            {"EUR", "GBP", "0.86"},
            {"GBP", "JPY", "155.0"}
        };
        String[][] queries1 = {
            {"USD", "EUR"},
            {"USD", "JPY"},
            {"EUR", "USD"},
            {"USD", "AUD"}
        };
        
        System.out.println("Test Case 1:");
        double[] results1 = convertCurrencies(rates1, queries1);
        System.out.println(Arrays.toString(results1));
        
        String[][] rates2 = {
            {"USD", "JPY", "110.0"},
            {"JPY", "CNY", "0.060"},
            {"CNY", "USD", "0.15"}
        };
        String[][] queries2 = {
            {"USD", "CNY"},
            {"JPY", "USD"},
            {"CNY", "JPY"}
        };
        
        System.out.println("\nTest Case 2:");
        double[] results2 = convertCurrencies(rates2, queries2);
        System.out.println(Arrays.toString(results2));
        
        String[][] rates3 = {
            {"USD", "JPY", "100"},
            {"JPY", "CHN", "20"},
            {"CHN", "THAI", "200"}
        };
        String[][] queries3 = {
            {"USD", "CHN"},
            {"JPY", "THAI"},
            {"USD", "AUD"}
        };
        
        System.out.println("\nTest Case 3:");
        double[] results3 = convertCurrencies(rates3, queries3);
        System.out.println(Arrays.toString(results3));
    }
}
