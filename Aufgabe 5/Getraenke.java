public abstract class Getraenke extends Lebensmittel{

    public Getraenke(String name, int menge) {
        super(name, menge);
    }

    @Override
    public boolean essen() {
        return false;
    }

    @Override
    public boolean trinken() {
        if(menge >= 100){
            menge -= 100;
            return true;
        }
        else {
            menge = 0;
            return false;
        }

    }
}
