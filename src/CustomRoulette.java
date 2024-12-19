import java.util.List;
import java.util.Random;

/**
 * Created by Axim- on 06.02.2018.
 */
public class CustomRoulette {
    private Random generator;
    private double total;
    private List<RouletteSlot> slots;

    public CustomRoulette(Random generator, List<RouletteSlot> slots){
        this.generator = generator;
        this.slots = slots;
        total = 0.0;
        for(RouletteSlot slot : slots){
            total += slot.fitness;
        }
    }

    public int select(){
        double x = generator.nextDouble();
        double selectionLimit = total * x;
        double sum = 0.0;
        for(RouletteSlot slot: slots){
            if(sum > selectionLimit){
                return slot.index;
            }
            sum += slot.fitness;
        }
        return slots.get(slots.size()-1).index;
    }

    public void update(int index, double value){
        total -= slots.get(index).fitness;
        slots.get(index).update(value);
        total += slots.get(index).fitness;
    }
}
