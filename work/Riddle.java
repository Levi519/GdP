public class Riddle {

    static int count = 0;

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        solveRiddle(n);
        if (n > 5){
            System.out.println(count + " Loesungen");
        }else if(n == 3 || n == 4){
            System.out.println("eine Loesung");
        }
    }

    public static void solveRiddle(int n) {
        // Wenn n ausserhalb des vorgegebenen Bereichs liegt
        if (n <= 0 || n > 15) {
            System.out.println("Bitte Zahl zwischen 1 und 15 wählen");
            return;
        }

        int[] feld = new int[2 * n];
        boolean hatLoesung = loesen(feld, n, 1);

        // Fehlermeldung keine Loesung ausgeben
        if (!hatLoesung) {
            System.out.println("keine Loesung");
        }
    }

    // Rekursive Methode zur Suche nach Lösungen
    public static boolean loesen(int[] feld, int n, int position) {
        if (position > n) {
            if (n > 10){
                count++;
            }else {
                count++;
                ausgeben(feld);
            }
            return true; // Eine Lösung wurde gefunden
        }

        boolean loesung = false;

        for (int i = 0; i < feld.length - position - 1; i++) {
            if (feld[i] == 0 && feld[i + position + 1] == 0) { // Prüfen, ob die Plätze frei sind
                feld[i] = position;
                feld[i + position + 1] = position;

                loesung |= loesen(feld, n, position + 1);

                // Zurücksetzen der Felder
                feld[i] = 0;
                feld[i + position + 1] = 0;
            }
        }
        return loesung;
    }

    // Methode zum Ausgeben einer Lösung
    public static void ausgeben(int[] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i]);
        }
        System.out.println();
    }
}
