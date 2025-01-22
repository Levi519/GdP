public abstract class Getraenke extends Lebensmittel{

    public Getraenke(String name, int menge) {

        super(name, menge);
    }

    @Override
    public boolean essen() {
        return false;
    }


}
