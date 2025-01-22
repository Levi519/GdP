public class Brot extends Speise{
     static final String[] BROT_TYPEN = {"Weißbrot","Schwarzbrot","Mischbrot"};
    public Brot(String typ, int menge) {
        super((typ), menge);

    }


    @Override
    public boolean essen() {
        menge -= 50;
        return menge > 0;

    }


}
