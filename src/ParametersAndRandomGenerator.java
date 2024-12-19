import java.util.Random;

/**
 * Created by Axim- on 02.02.2018.
 */
public class ParametersAndRandomGenerator {
    public  Random generator;
    public final int d;
    public final int sn;
    public final int mcn;
    public final int limit;
    public final TestFunction testFunction;
    public final Version version;
    public double maxNectar;
    public final boolean naturalFitness;
    public double ap;
    public FoodSource bestSoFar;
    public int convergenceIteration;

    public ParametersAndRandomGenerator(int d, int sn, int mcn, int limit, TestFunction testFunction, Version version, boolean naturalFitness) {
        this.generator = new Random();
        this.d = d;
        this.sn = sn;
        this.mcn = mcn;
        this.limit = limit;
        this.testFunction = testFunction;
        this.version = version;
        this.naturalFitness = naturalFitness;
        maxNectar = -1;
    }

    public double valueWithinLimit(double v, double index){
        double sHigher = higherBound(index);
        double sLower = lowerBound(index);
        if (v > sHigher){
            v = sHigher;
        }
        if(v < sLower){
            v = sLower;
        }
        return v;
    }

    public double lowerBound(double index){
        if(testFunction.type == TestFunctionType.F17){
            if(index == 0){
                return -5;
            }
            else{
                return 0;
            }
        }
        else{
            return testFunction.lowerBound();
        }
    }

    public double higherBound(double index){
        if(testFunction.type == TestFunctionType.F17){
            if(index == 0){
                return 10;
            }
            else{
                return 15;
            }
        }
        else{
            return testFunction.higherBound();
        }
    }

    public int randomInt(int max){
        return generator.nextInt(max);
    }

    public int randomIndex(int max, int exception){
        int index;
        do{
            index = randomInt(max);
        }while(index==exception);
        return index;
    }

    public double randomDouble(double low, double high){
        return low + generator.nextDouble() * (high - low);
    }

}
