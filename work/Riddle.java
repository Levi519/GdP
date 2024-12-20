public class Riddle {
    public static void main(String[] args) {
        int input = Integer.parseInt(args[0]);
        int[] arr = new int[input * 2];


    }

    public static int[] fuellen(int[] arr) {
        fuellen(arr);
    }

    public static boolean pruefer(int[] arr){
        for(int i = 0; i < arr.length; i++){
            if(arr[i] + i + 1 >= arr.length){
                return false;
            }
            if(arr[i] != arr[i + arr[i]] + 1){
                return false;
            }
        }
        return true;
    }
}
