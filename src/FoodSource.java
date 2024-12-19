/**
 * Created by Axim- on 01.02.2018.
 */
public class FoodSource implements Comparable<FoodSource> {
    private ParametersAndRandomGenerator params;
    public double[] optimizationParameters;
    public double nectar;
    private int counterForAbandonment;
    //private double r1;
    //private double r2;
    private double[] random1;
    private double[] random2;
    private double[] prevValues;

    public FoodSource(ParametersAndRandomGenerator params){
        this.params = params;

        random1 = new double[params.d];
        random2 = new double[params.d];
        randomizeParameters();

        //r1 = params.randomDouble(0,1);
        //r2 = params.randomDouble(0,1);
    }

    public FoodSource(FoodSource original){
        this.nectar = original.nectar;
        this.counterForAbandonment = original.counterForAbandonment;
        this.params = original.params;
        optimizationParameters = new double[this.params.d];
        for(int i = 0; i < this.params.d ; i++){
            optimizationParameters[i] = original.optimizationParameters[i];
        }
    }

    private void randomizeParameters() {
        prevValues = new double[params.d];
        optimizationParameters = new double[this.params.d];
        for(int i = 0; i < this.params.d; i++){
            optimizationParameters[i] = params.randomDouble(params.lowerBound(i), params.higherBound(i));
            prevValues[i] = 1;
        }
        counterForAbandonment = 0;

        for(int i = 0; i < params.d; i++){
            random1[i] = params.randomDouble(0, 1);
            random2[i] = params.randomDouble(0, 1);
        }

        calculateNectar();
    }

    public void modifyWorker(FoodSource goal){
        counterForAbandonment += 1;
        if(params.version == Version.ABC){
            modifyABC(goal);
        }
        else if(params.version == Version.IABC){
            modifyIABC(goal, false);
        }
        else if(params.version == Version.PSABC){
            modifyABC(goal);
            modifyIABC(goal, false);
            modifyPSABC(goal);
        }
    }

    private void modifyPSABC(FoodSource goal){
        int dIndex = params.randomInt(params.d);
        double r1 = params.randomDouble(0,1);
        double r2 = params.randomDouble(0,1);
        double x = optimizationParameters[dIndex];
        double k = goal.optimizationParameters[dIndex];
        double j = params.bestSoFar.optimizationParameters[dIndex];
        double v = x + 2*(r1 - 0.5)*(x - k) + r2*(j - k);
        changeValue(dIndex, v);
    }


/*
    private void modifyIABC(FoodSource goal, boolean onLooker){
        double a = 1 + Math.exp(-nectar / params.ap);
        //double a = 1 + Math.pow(Math.E, -nectar / params.ap);
        //System.out.println(nectar + "\t" + a);
        double phi1 = 1 / a;
        double phi2 = onLooker ? phi1 : 1;
        double w = phi1;

        double vv = 0.0;

        for(int dIndex = 0; dIndex < params.d; dIndex++){
            //double r1 = random1[dIndex];
            //double r2 = random2[dIndex];
            double r1 = params.randomDouble(0, 1);
            double r2 = params.randomDouble(0, 1);
            double x = optimizationParameters[dIndex];
            double vOld = prevValues[dIndex];
            double k = goal.optimizationParameters[dIndex];
            double j = params.bestSoFar.optimizationParameters[dIndex];
            //double v = vOld*w + 2*(r1 - 0.5)*(x - k) * phi1 + r2*(j - k) * phi2;
            double v = x*w + 2*(r1 - 0.5)*(x - k) * phi1 + r2*(j - k) * phi2;       // IABC
            //double v = x + w + r1 *(k - x) * phi1 + r2*(j - x) * phi2;
            //double newX = x + v;
            //double v = x*w + r1 * (x - k) * phi1 + r2*(j - k) * phi2;
            //this.optimizationParameters[dIndex] = v;
            changeValue(dIndex, v);
            if(dIndex == 1 && counterForAbandonment==0){
                return;
            }
            //vv = v;
        }
        //changeValue(params.d-1, vv);

    }
*/

    public double getW(){
        double a = 1 + Math.exp(-nectar / params.ap);
        return 1.0 / a;
    }

    private void modifyIABC(FoodSource goal, boolean onLooker){
        double a = 1.0 + Math.exp(-nectar / params.ap);
        //double a = 1 + Math.pow(Math.E, -nectar / params.ap);
        //System.out.println(nectar + "\t" + a);

        double phi1 = 1.0 / a;
        double phi2 = onLooker ? phi1 : 1.0;
        double w = phi1;
        int dIndex = params.randomInt(params.d);
        //double r1 = random1[dIndex];
        //double r2 = random2[dIndex];
        double r1 = params.randomDouble(0, 1);
        double r2 = params.randomDouble(0, 1);
        double x = optimizationParameters[dIndex];
        double vOld = prevValues[dIndex];
        double k = goal.optimizationParameters[dIndex];
        double j = params.bestSoFar.optimizationParameters[dIndex];
        //double v = vOld*w + 2*(r1 - 0.5)*(x - k) * phi1 + r2*(j - k) * phi2;
        double v = x*w + 2*(r1 - 0.5)*(x - k) * phi1 + r2*(j - k) * phi2;         //IABC
        //double v = x*w + r1 * (x - k) * phi1 + r2 * (j - k) * phi2;         //IABC  v2
        //double v = x * w + r1 *(k - x) * phi1 + r2*(j - x) * phi2;
        //double newX = x + v;
        //double v = x*w + r1 * (x - k) * phi1 + r2*(j - k) * phi2;
        changeValue(dIndex, v);
    }


    private void modifyABC(FoodSource goal){
        int dIndex = params.randomInt(params.d);

        double theta = params.randomDouble(-1.0,1.0);
        //double newValue = optimizationParameters[dIndex] + theta * (optimizationParameters[dIndex] - goal.optimizationParameters[dIndex]);  // (2)
        double change = optimizationParameters[dIndex] +  theta * (optimizationParameters[dIndex] - goal.optimizationParameters[dIndex]);
        changeValue(dIndex, change);
    }

    private void changeValue(int dIndex, double newValue) {
        newValue = params.valueWithinLimit(newValue, dIndex);
        double prevNectar = nectar;
        double prevValue = optimizationParameters[dIndex];

        //System.out.println((newValue - prevValue) + "\t\t" + newValue + "\t" + prevValue);

        this.optimizationParameters[dIndex] = newValue;

        calculateNectar();

        if (prevNectar <= nectar) {
            this.optimizationParameters[dIndex] = prevValue;
            calculateNectar();
        }
        else{
            counterForAbandonment = 0;
            prevValues[dIndex] = newValue;
        }


        /*
        if(nectar < params.bestSoFar.nectar){
            params.bestSoFar = new FoodSource(this);
        }
        */
    }

    public void modifyOnLooker(FoodSource goal){
        if(params.version == Version.ABC){
            modifyABC(goal);
        }
        else if(params.version == Version.IABC){
            modifyIABC(goal, true);
        }
        else if(params.version == Version.PSABC){
            modifyABC(goal);
            modifyIABC(goal, true);
            modifyPSABC(goal);
        }
    }

    public void modifyScout() {
        if(counterForAbandonment == params.limit){
            randomizeParameters();
        }
    }

    public void calculateNectar(){
        this.nectar = params.testFunction.runFunction(optimizationParameters);
    }

    @Override
    public int compareTo(FoodSource o) {
        return Double.compare(this.nectar, o.nectar);
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(Double d: optimizationParameters){
            str.append(d);
            str.append(", ");
        }
        return str.substring(0, str.length()-1);
    }
}
