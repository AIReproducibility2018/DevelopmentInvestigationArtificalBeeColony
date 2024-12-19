import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Version version = Version.ABC;
        /*
        TestFunctionType[] functions = new TestFunctionType[] {TestFunctionType.F1, TestFunctionType.F2,
                TestFunctionType.F3, TestFunctionType.F4, TestFunctionType.F5, TestFunctionType.F6,
                TestFunctionType.F7, TestFunctionType.F8, TestFunctionType.F9, TestFunctionType.F10,
                TestFunctionType.F11};
                */

        //TestFunctionType[] functions = new TestFunctionType[] {TestFunctionType.F5};


        /*
        TestFunctionType[] functions = new TestFunctionType[] {TestFunctionType.F1, TestFunctionType.F2,
                TestFunctionType.F3, TestFunctionType.F4, };
        */


        TestFunctionType[] functions = new TestFunctionType[23];
        int index = 0;
        for(TestFunctionType fType: TestFunctionType.values()) {
            functions[index] = fType;
            index++;
        }


        double numberOfRuns = 1;

        for(TestFunctionType type : functions){
            TestFunction testFunction = new TestFunction(type);
            for(int dimension: testFunction.dimensions()){
                ParametersAndRandomGenerator parametersAndRandomGenerator = new ParametersAndRandomGenerator(dimension, 40,1000,200, testFunction,version, false);
                //ParametersAndRandomGenerator parametersAndRandomGenerator = new ParametersAndRandomGenerator(20, 20,1000,200, TestFunctionType.F2, -10, 10);
                ABC program = new ABC(parametersAndRandomGenerator);

                for(int j = 0; j < numberOfRuns ; j++){
                    double totalNectar = 0.0;

                    int rounds = 30;
                    double[] results = new double[rounds];
                    double convergencetotal = 0.0;

                    for(int i = 0 ; i < rounds; i ++){
                        Result result = program.abc();
                        double res = result.nectar;
                        results[i] = res;

                        totalNectar += res;
                        convergencetotal += result.convergenceIteration;
                        //System.out.println(res);
                    }

                    double avg = totalNectar/rounds;
                    double sd = calculateSD(results, avg);
                    double ci = convergencetotal / rounds;

                    //System.out.println(testFunction.type.name() + "\tDim: " + dimension + "\t\tC.I.: " + Math.round(ci) + "\t\tMean: " + new DecimalFormat("#.####E0").format(avg) + "\t\tSD: " + new DecimalFormat("#.####E0").format(sd));
                    System.out.println(testFunction.type.name() + "\t" + dimension + "\t" + Math.round(ci) + "\t" + new DecimalFormat("#.####E0").format(avg) + "\t" + new DecimalFormat("#.####E0").format(sd));

                }
            }
            System.out.println();

        }




    }


    public static double calculateSD(double[] results, double avg){
        double sum = 0.0;
        for(int i = 0; i < results.length ; i++){
            double a = results[i] - avg;
            sum += Math.pow(a, 2);
        }
        double b = sum / (results.length - 1);
        return Math.sqrt(b);
    }








}


