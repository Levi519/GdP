public class PlaetzchenPruefer {
    public static void main(String[] args) {
        System.out.println(pruefePlaetzchen(Integer.parseInt(args[0])));
    }

    public static int pruefePlaetzchen(int p) {
        if (p == 0){
            return 0;
        }
        if (p == 1){
            return 1;
        }

        if (p % 2 == 0) {
            return 2 + pruefePlaetzchen((p-2) / 2);
        } else {
            return 1 + pruefePlaetzchen(p - 1);
        }
    }
}
