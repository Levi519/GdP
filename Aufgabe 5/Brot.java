public class Brot extends Speise{
     static final String[] BROT_TYPEN = {"Weißbrot","Schwarzbrot","Mischbrot"};
     static final int[] BROT = {0, 1 ,2 };

    public Brot(int typ, int menge) {
        super(getBrotTyp(typ), menge);

    }

    public static String getBrotTyp(int typ) {
        if (typ < 0 || typ >= BROT.length) {
            return "Spezialbrot";
        }
        return BROT_TYPEN[typ];
    }

    @Override
    public boolean essen() {
      if(menge >= 50){
          menge -= 50;
          return true;
      }
      else{
          menge = 0;
          return false;
      }

    }
    @Override
    public String toString() {
        return "Brot" + " ("+ name + ", " + menge +"g)" ;
    }

}
