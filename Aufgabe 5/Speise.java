public abstract  class Speise extends Lebensmittel{

    public Speise(String name, int menge) {
        super(name, menge);

    }


    @Override
    public boolean trinken()
    {
        return false;
    }
}
