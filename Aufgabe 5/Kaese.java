public class Kaese extends Speise{
    public Kaese(String name, int menge) {
        super(name, menge);
        this.name = name;

    }

    @Override
    public boolean essen() {
        menge -= 20;
        return menge >0;
    }

    @Override
    public boolean trinken() {
        return false;
    }

}
