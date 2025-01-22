public abstract  class Speise extends Lebensmittel{

    public Speise(String name, int menge) {
        super(name, menge);

    }

    @Override
    public boolean essen()
    {
        if(menge >= 50 ) {
            menge -= 50;
            return true;
        }
        else    {
            menge = 0;
            return false;
        }
    }

    @Override
    public boolean trinken()
    {
        return false;
    }
}
