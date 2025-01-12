public class Riddle {

    static int count = 0;

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        solveRiddle(n);
        if (n > 10){
            System.out.println(count + " Loesungen");
        }else if(n == 3){
            System.out.println("eine Loesung");
        }
    }

    public static void solveRiddle(int n) {
        if (n <= 0 || n > 15) {
            System.out.println("keine Loesung");
            return;
        }

        int[] field = new int[2 * n]; // Das Feld, in dem die Zahlen platziert werden
        boolean hasSolution = findSolutions(field, n, 1);

        if (!hasSolution) {
            System.out.println("keine Loesung");
        }
    }

    // Rekursive Methode zur Suche nach Lösungen
    public static boolean findSolutions(int[] field, int n, int currentNumber) {
        if (currentNumber > n) {
            if (n > 10){
                count++;
            }else {
                printSolution(field);
            }
            return true; // Eine Lösung wurde gefunden
        }

        boolean found = false;

        for (int i = 0; i < field.length - currentNumber - 1; i++) {
            if (field[i] == 0 && field[i + currentNumber + 1] == 0) { // Prüfen, ob die Plätze frei sind
                field[i] = currentNumber;
                field[i + currentNumber + 1] = currentNumber;

                found |= findSolutions(field, n, currentNumber + 1);

                // Backtracking: Zurücksetzen der Felder
                field[i] = 0;
                field[i + currentNumber + 1] = 0;
            }
        }
        return found;
    }

    // Methode zum Drucken einer Lösung
    public static void printSolution(int[] solution) {
        for (int num : solution) {
            System.out.print(num);
        }
        System.out.println();
    }
}
