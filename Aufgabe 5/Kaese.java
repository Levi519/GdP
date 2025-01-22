public class Kaese extends Speise{
    public Kaese(String name, int menge) {
        super(name, menge);
        this.name = name;

    }

    @Override
    public boolean essen() {
        if(menge >= 20){
            menge -= 20;
            return true;
        }
        else {
            menge = 0;
            return false;
        }
    }
    @Override
    public String toString() {
        return "Kaese" + " ("+ name + ", " + menge +"g)" ;
    }


}
