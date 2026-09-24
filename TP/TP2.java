import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TP2 {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }

    public Map<String, List<Integer>> footballWorldCup(List<Map.Entry<Integer, String>> lf) {
        Map<String, List<Integer>> result = new HashMap<>();

        for (Map.Entry<Integer, String> entry : lf) {
            Integer year = entry.getKey();
            String country = entry.getValue();

            result.putIfAbsent(country, new ArrayList<>());
            result.get(country).add(year);
        }

        // Ordena cada lista de anos por ordem decrescente
        for (List<Integer> years : result.values()) {
            years.sort(Collections.reverseOrder());
        }

        return result;
    }

    public LinkedList<Integer> calcMMS(LinkedList<Integer> serie, Integer period) {
        LinkedList<Integer> result = new LinkedList<>();

        for (int i = 0; i < serie.size(); i++) {
            if (i < period - 1) {
                result.add(0);
                continue;
            }
            int sum = 0;
            for (int j = 0; j < period; j++) {
                sum += serie.get(i - period + 1 + j);
            }
            result.add(sum / period);
        }

        return result;
    }
}