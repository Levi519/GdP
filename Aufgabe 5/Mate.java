public class Mate extends Getraenke{
    public Mate(String name, int menge) {
        super(name, 500);
        this.name = name;
    }

    public String getName(){
        return name;
    }



    @Override
    public boolean trinken() {
        if(menge >= 200){
            menge -= 200;
            return true;
        }
        else{
            menge = 0;
            return false;
        }
    }





}
