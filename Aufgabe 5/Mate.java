public class Mate extends Getraenke{

    public Mate(String name) {
        super(name, 500);
        this.name = name;
    }

    public String getName(){
        return name;
    }



    @Override
    public boolean trinken() {
        if(menge >= 100){
            menge -= 100;
            return true;
        }
        else{
            menge = 0;
            return false;
        }
    }

    @Override
    public String toString() {
        return "Mate" + " ("+ name + ", " + menge +"ml)" ;
    }




}
