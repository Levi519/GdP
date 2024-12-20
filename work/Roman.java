public class Roman {
    public static final String[]
            SYMBOLE = {
            "M", "D", "C", "L", "X", "V", "I"
    };
    public static final int[]
            NUMMER = {
             1000, 500, 100, 50, 10, 5, 1
    };

    static String roman(int n) {
        String result = new String();
        for(int i=0; i<NUMMER.length; i++){
            while (n >= NUMMER[i]){
               result += (SYMBOLE[i]);
                n -= NUMMER[i];
            }
        }
        return result;
    }
    public static void main (String [] args) {
        if (args.length == 0) {
            System.out.println("Bitte eine Zahl als Parameter eingeben.");
            return;
        }


        int N = Integer.parseInt(args[0]);
        if (1 <= N && N <= 5000) {
            System.out.println(roman(N));
        } else {
            System.out.println("Die Zahl muss zwischen 1 und 5000 liegen.");
        }
    }

    }

