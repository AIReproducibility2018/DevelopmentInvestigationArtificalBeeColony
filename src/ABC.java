import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Axim- on 01.02.2018.
 */
public class ABC {


    private ParametersAndRandomGenerator params;
    private FoodSource[] foodSources;
    RouletteWheelSelection rouletteWheelSelection;
    StochasticUniversalSampling universalSampling;


    public ABC(ParametersAndRandomGenerator params){
        this.params = params;
        rouletteWheelSelection = new RouletteWheelSelection();
        universalSampling = new StochasticUniversalSampling();
    }


    private FoodSource[] initializeFoodsources(){
        FoodSource[] foodSources = new FoodSource[params.sn];
        for(int i = 0; i < params.sn; i++){
            foodSources[i] = new FoodSource( params );
        }
        return foodSources;
    }

    private void sendEmployeeBees(){
        for(int i = 0 ; i < params.sn ; i++){
            int goalIndex = params.randomIndex(params.sn, i);
            foodSources[i].modifyWorker(foodSources[goalIndex]);
        }
    }

    private void printMaxAndMinMeasure(List<EvaluatedCandidate<Integer>> candidates){
        double min = 100000000;
        double max = -1;

        for(EvaluatedCandidate<Integer> c : candidates){
            if(min > c.getFitness()){
                min = c.getFitness();
            }
            if(max < c.getFitness()){
                max = c.getFitness();
            }
        }
        System.out.println(min + "\t" + max);
    }

    private int getIndexOfHighest(){
        int index = 0;
        for(int i = 0 ; i < params.sn ; i++){
            if (foodSources[i].nectar >= foodSources[index].nectar){
                index = i;
            }
        }
        return index;
    }

    private void sendOnLookerBees(){
        List<EvaluatedCandidate<Integer>> candidates = new ArrayList<>();
        List<RouletteSlot> slots = new ArrayList<>();
        CustomRoulette roulette = new CustomRoulette(params.generator, slots);

        for(int j = 0 ; j < params.sn ; j++){
            double measure = foodSources[j].nectar;
            //candidates.add(new EvaluatedCandidate<>(j, measure));
            slots.add(new RouletteSlot(measure, j, params.naturalFitness));
        }


        //printMaxAndMinMeasure(candidates);

        for(int i = 0 ; i < params.sn ; i++){

            //List<Integer> selected = rouletteWheelSelection.select(candidates, params.naturalFitness, 1, params.generator);
            //List<Integer> selected = universalSampling.select(candidates, false, 1, params.generator);
            //int index = selected.get(0);
            int index = roulette.select();
            int goalIndex = params.randomIndex(params.sn, index);
            foodSources[index].modifyOnLooker(foodSources[goalIndex]);


            roulette.update(index, foodSources[index].nectar);

            /*
            if(foodSources[index].nectar < params.bestSoFar.nectar){
                params.bestSoFar = new FoodSource(foodSources[index]);
            }
            */
        }
    }

    private void sendScoutBees(){
        for(int i = 0 ; i < params.sn ; i++){
            foodSources[i].modifyScout();
        }
    }

    private void memorizeBestFoodSource(int cycle){
        for(FoodSource f : foodSources){
            if(f.nectar < params.bestSoFar.nectar ){
                params.bestSoFar = new FoodSource(f);
                params.convergenceIteration = cycle;
            }
        }
    }

    private double getAvgW(){
        double sum = 0.0;
        for(FoodSource f: foodSources){
            sum += f.getW();
        }
        return sum / (double) foodSources.length;
    }


    public Result abc(){

        foodSources = initializeFoodsources();
        Arrays.sort(foodSources);
        params.bestSoFar = foodSources[0];
        params.ap = params.bestSoFar.nectar;
        //params.ap = foodSources[foodSources.length-1].nectar;
        params.convergenceIteration = 0;
        int cycle = 1;
        //System.out.println("\n\n\n\nStart");
        while(cycle != params.mcn && params.bestSoFar.nectar != params.testFunction.optimalValue()){

            sendEmployeeBees();

            //memorizeBestFoodSource(cycle);

            sendOnLookerBees();

            //memorizeBestFoodSource(cycle);

            sendScoutBees();

            memorizeBestFoodSource(cycle);

            //System.out.println(bestSoFar.nectar);

            //System.out.println(getAvgW());

            cycle += 1;
        }
        //System.out.println("End\n\n\n\n");

        //System.out.println("Nectar" + bestSoFar.nectar + "\t" + bestSoFar);
        //System.out.println(bestSoFar);
        //return bestSoFar.nectar;
        return new Result(params.convergenceIteration, params.testFunction.nectar(params.bestSoFar.optimizationParameters));
    }



}
