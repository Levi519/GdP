public class Roman {
    public static final String[]
            SYMBOLE = {
            "M", "CM", "D", "C", "L", "X", "V", "I"
    };
    public static final int[]
            NUMMER = {
             1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
    };

    public static String ausgabe = "";

    static void roman(int n, int index) {
        if (n == 0) return;
        while (n >= NUMMER[index]) {
            ausgabe += SYMBOLE[index];
            n -= NUMMER[index];
        }
        roman(n, index + 1);
    }
    public static void main (String [] args) {
        if (args.length == 0) {
            System.out.println("Bitte eine Zahl als Parameter eingeben.");
            return;
        }

        int N = Integer.parseInt(args[0]);
        if (1 <= N && N <= 5000) {
            roman(N, 0);
            System.out.println(ausgabe);
        } else {
            System.out.println("Die Zahl muss zwischen 1 und 5000 liegen.");
        }
    }

    }

