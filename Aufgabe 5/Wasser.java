public class Wasser extends Getraenke{

    public Wasser(String name, int menge) {
        super(name, menge);
        this.name = name;
    }

    @Override
    public boolean trinken() {
        if(menge >= 200){
            menge -= 200;
            return true;
        }
        else {
            menge = 0;
            return false;
        }
    }
    @Override
    public String toString() {
        return "Wasser" + " ("+ name + ", " + menge +"ml)" ;
    }
}
