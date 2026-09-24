package esinf;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Supermarket {
    Map <Invoice, Set<Product>> sup;
    
    Supermarket() {
        sup = new HashMap<>();
    }
    
    // Reads invoices from a list of String
     List<Invoice> getInvoices(List<String> l) throws Exception {
        List<Invoice> invoices = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Invoice currentInvoice = null;

        for (String s : l) {
            String[] fields = s.split(",");

            if (fields[0].equals("I")) {
                if (fields.length != 3) throw new Exception("Invalid invoice line: " + s);

                String reference = fields[1];
                LocalDate.parse(fields[2], formatter);

                currentInvoice = new Invoice(reference, fields[2]);
                invoices.add(currentInvoice);
                sup.put(currentInvoice, new HashSet<>());

            } else if (fields[0].equals("P")) {
                if (fields.length != 4) throw new Exception("Invalid product line: " + s);
                if (currentInvoice == null) throw new Exception("Product without invoice: " + s);

                String id = fields[1];
                int quantity = Integer.parseInt(fields[2]);
                int price = Integer.parseInt(fields[3]);

                Product product = new Product(id, quantity, price);
                sup.get(currentInvoice).add(product);

            } else {
                throw new Exception("Invalid line: " + s);
            }
        }

        return invoices;
    }   
    
    // returns a set in which each number is the number of products in the r
    // invoice 
    Map<Invoice, Integer> numberOfProductsPerInvoice(List<Invoice> invoices) {
        Map<Invoice, Integer> map = new HashMap<>();
        for (Invoice invoice : invoices) {
            map.put(invoice, sup.get(invoice).size());
        }
        return map;
    }

    // returns a Set of invoices in which each date is >d1 and <d2
    Set<Invoice> betweenDates(LocalDate d1, LocalDate d2) {
        Set<Invoice> result = new HashSet<>();
        for (Invoice invoice : sup.keySet()) {
            if (invoice.getDate().isAfter(d1) && invoice.getDate().isBefore(d2)) {
                result.add(invoice);
            }
        }
        return result;
    }
    
    // returns the sum of the price of the product in all the invoices
    long totalOfProduct(String productId) {
        long total = 0;
        for (Set<Product> products : sup.values()) {
            for (Product product : products) {
                if (product.getIdentification().equals(productId)) {
                    total += product.getPrice() * product.getQuantity();
                }
            }
        }
        return total;
    }
    
    // converts a map of invoices and troducts to a map which key is a product 
    // identification and the values are a set of the invoices in which it appears
    Map<String, Set<Invoice>> convertInvoices(Map<Invoice, Set<Product>> invoices) {
        Map<String, Set<Invoice>> result = new HashMap<>();
        for (Map.Entry<Invoice, Set<Product>> entry : invoices.entrySet()) {
            Invoice invoice = entry.getKey();
            Set<Product> products = entry.getValue();
            for (Product product : products) {
                String productId = product.getIdentification();
                result.computeIfAbsent(productId, k -> new HashSet<>()).add(invoice);
            }
        }
        return result;
    }

}
