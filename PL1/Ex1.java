import java.util.Arrays;

public class Ex1 {

    public static void main(String[] args) {
        // Teste: reverse
        String s = "Hello, World!";
        String reversed = reverse(s);
        System.out.println("Original: " + s);
        System.out.println("Reversed: " + reversed);
        System.out.println();

        // Teste: multiply
        int product = multiply(7, 6);
        System.out.println("Multiply 7 x 6 = " + product);
        System.out.println();

        // Teste: mdc
        int mdc = mdc(48, 30);
        System.out.println("MDC of 48 and 30: " + mdc);
        System.out.println();

        // Teste: cvrt
        String numStr = "12345";
        int converted = cvrt(numStr);
        System.out.println("cvrt(\"" + numStr + "\") = " + converted);
        System.out.println();

        // Teste: pali
        String p1 = "ana";
        String p2 = "sopapos";
        String p3 = "radar";
        String p4 = "hello";
        System.out.println("pali(\"" + p1 + "\") = " + pali(p1));
        System.out.println("pali(\"" + p2 + "\") = " + pali(p2));
        System.out.println("pali(\"" + p3 + "\") = " + pali(p3));
        System.out.println("pali(\"" + p4 + "\") = " + pali(p4));
        System.out.println();

        // Teste: sum (arrays)
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {10, 20, 30, 40};
        int[] result = sum(arr1, arr2);
        System.out.println("Array 1: " + Arrays.toString(arr1));
        System.out.println("Array 2: " + Arrays.toString(arr2));
        System.out.println("Sum: " + Arrays.toString(result));
    }

    public static String reverse(String s) {
        if (s.length() == 0) {
            return s;
        }
        return reverse(s, s.length() - 1);
    }
    private static String reverse(String s, int index) {
        if (index == 0) {
            return s.charAt(index) + "";
        }
        return s.charAt(index) + reverse(s, index - 1);
    }

    public static int multiply(int a, int b) {
        if (b == 0) {
            return 0;
        }
        return a + multiply(a, b - 1);
    }

    public static int mdc(int a, int b) {
        return mdc(a, b, Math.min(a, b));
    }
    private static int mdc(int a, int b, int c) {
        if (a % c == 0 && b % c == 0) {
            return c;
        }
        return mdc(a, b, c - 1);
    }

    public static int cvrt(String string) {
        if (string.length() == 0) {
            return 0;
        }
        return cvrt(string, string.length() - 1);
    }
    private static int cvrt(String string, int index) {
        if (index == 0) {
            return string.charAt(index) - '0';
        }
        return (string.charAt(index) - '0') + 10 * cvrt(string, index - 1);
    }

    public static String pali(String string) {
        if (string.length() <= 1) {
            return string;
        }
        return pali(string, 0, string.length() - 1);
    }
    private static String pali(String string, int start, int end) {
        if (start >= end) {
            return string;
        }
        if (string.charAt(start) != string.charAt(end)) {
            return "Not a palindrome";
        }
        return pali(string, start + 1, end - 1);
    }

    public static int[] sum(int[] a1, int[] a2) {
        if (a1.length != a2.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }
        return sum(a1, a2, 0);
    }
    private static int[] sum(int[] a1, int[] a2, int index) {
        if (index == a1.length) {
            return new int[a1.length];  // <-- CORRIGIDO: tamanho correto
        }
        int[] result = sum(a1, a2, index + 1);
        result[index] = a1[index] + a2[index];
        return result;
    }
}