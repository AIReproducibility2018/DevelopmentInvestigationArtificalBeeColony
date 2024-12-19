/**
 * Created by Axim- on 06.02.2018.
 */
public class RouletteSlot {
    public int index;
    public double fitness;
    private boolean naturalFitness;

    public RouletteSlot(double nectar, int index, boolean naturalFitness){
        this.naturalFitness = naturalFitness;
        update(nectar);
        this.index = index;
    }

    public void update(double nectar){
        fitness = naturalFitness ? 1/(1+nectar) : nectar;
    }
}
