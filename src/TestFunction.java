import java.util.List;
import java.util.Random;

/**
 * Created by Axim- on 02.02.2018.
 */
public class TestFunction {

    public TestFunctionType type;
    private Random generator;
    private double f7Random;

    public TestFunction(TestFunctionType type) {
        this.type = type;
        generator = new Random();
        f7Random = generator.nextDouble();
    }

    public double lowerBound(){
        switch (type){
            case F1:
                return -100;
            case F2:
                return -10;
            case F3:
                return -100;
            case F4:
                return -100;
            case F5:
                return -30;
            case F6:
                return -100;
            case F7:
                return -1.28;
            case F8:
                return -500;
            case F9:
                return -5.12;
            case F10:
                return -32;
            case F11:
                return -600;
            case F12:
                return -50;
            case F13:
                return -50;
            case F14:
                return -65.53;
            case F15:
                return -5;
            case F16:
                return -5;
            case F17:
                return -5;
            case F18:
                return -5;
            case F19:
                return 0;
            case F20:
                return 0;
            case F21:
                return 0;
            case F22:
                return 0;
            case F23:
                return 0;
            default:
                throw new IllegalStateException("Lowerbound");
        }
    }

    public double higherBound(){
        switch (type){
            case F1:
                return 100;
            case F2:
                return 10;
            case F3:
                return 100;
            case F4:
                return 100;
            case F5:
                return 30;
            case F6:
                return 100;
            case F7:
                return 1.28;
            case F8:
                return 500;
            case F9:
                return 5.12;
            case F10:
                return 32;
            case F11:
                return 600;
            case F12:
                return 50;
            case F13:
                return 50;
            case F14:
                return 65.53;
            case F15:
                return 5;
            case F16:
                return 5;
            case F17:
                return 10;
            case F18:
                return 5;
            case F19:
                return 1;
            case F20:
                return 1;
            case F21:
                return 10;
            case F22:
                return 10;
            case F23:
                return 10;
            default:
                throw new IllegalStateException("Higherbound");
        }
    }

    public int[] dimensions(){
        switch (type){
            case F14:
                return new int[] {2};
            case F15:
                return new int[] {4};
            case F16:
                return new int[] {2};
            case F17:
                return new int[] {2};
            case F18:
                return new int[] {2};
            case F19:
                return new int[] {3};
            case F20:
                return new int[] {6};
            case F21:
                return new int[] {4};
            case F22:
                return new int[] {4};
            case F23:
                return new int[] {4};
            default:
                return new int[] {20, 30, 50};
        }
    }

    public double runFunction(double[] parameters){
        switch (type){
            case F1:
                return f1(parameters);
            case F2:
                return f2(parameters);
            case F3:
                return f3(parameters);
            case F4:
                return f4(parameters);
            case F5:
                return f5(parameters);
            case F6:
                return f6(parameters);
            case F7:
                return f7(parameters);
            case F8:
                return f8(parameters);
            case F9:
                return f9(parameters);
            case F10:
                return f10(parameters);
            case F11:
                return f11(parameters);
            case F12:
                return f12(parameters);
            case F13:
                return f13(parameters);
            case F14:
                return f14(parameters);
            case F15:
                return f15(parameters);
            case F16:
                return f16(parameters);
            case F17:
                return f17(parameters);
            case F18:
                return f18(parameters);
            case F19:
                return f19(parameters);
            case F20:
                return f20(parameters);
            case F21:
                return f21(parameters);
            case F22:
                return f22(parameters);
            case F23:
                return f23(parameters);
            default:
                throw new IllegalStateException("The type was"+type.name());

        }
    }

    public double nectar(double[] parameters){
        switch (type){
            case F8:
                return f8Real(parameters);
            default:
                return runFunction(parameters);
        }
    }

    public double optimalValue(){
        switch (type){
            case F8:
                return -12569.5;
            default:
                return 0;
        }
    }

    private double f23 (double[] parameters){
        return calcF21AndF22AndF23(parameters, 10);
    }

    private double f22 (double[] parameters){
        return calcF21AndF22AndF23(parameters, 7);
    }

    private double f21 (double[] parameters){
        return calcF21AndF22AndF23(parameters, 5);
    }

    private double calcF21AndF22AndF23(double[] parameters, int i2) {
        double sum = 0.0;
        double[][] a = new double[][] {
                {4, 4, 4, 4},
                {1, 1, 1, 1},
                {8, 8, 8, 8},
                {6, 6, 6, 6},
                {3, 7, 3, 7},
                {2, 9, 2, 9},
                {5, 5, 3, 3},
                {8, 1, 8, 1},
                {6, 2, 6, 2},
                {7, 3.6, 7, 3.6}
        };
        double[] c = new double[] {0.1, 0.2, 0.2, 0.4, 0.4, 0.6, 0.3, 0.7, 0.5, 0.5};
        for(int i = 0; i < i2; i++){
            sum += Math.pow( vectorMult(parameters, a[i]) + c[i] , -1);
        }
        return -sum;
    }

    private double vectorMult(double[] x, double[] a){
        double rVal = 0.0;
        for(int i  = 0; i < x.length; i++){
            rVal += (x[i] - a[i]) * (x[i] - a[i]);
        }
        return rVal;
    }

    private double f20 (double[] parameters){
        double[] c = new double[]{1, 1.2, 3, 3.2};
        double[][] a = new double[][]{
                {10, 3, 17, 3.5, 1.7, 8},
                {0.05, 10, 17, 0.1, 8, 14},
                {3, 3.5, 1.7, 10, 17, 8},
                {17, 8, 0.05, 10, 0.1, 14}};
        double[][] p = new double[][]{
                {0.131, 0.169, 0.556, 0.012, 0.828, 0.588},
                {0.232, 0.413, 0.830, 0.373, 0.100, 0.999},
                {0.234, 0.141, 0.352, 0.288, 0.304, 0.665},
                {0.404, 0.882, 0.873, 0.574, 0.109, 0.038}
        };
        double sum = 0.0;
        sum = getSumF18AndF19(parameters, c, a, p, sum, 6);
        return -sum;
    }

    private double f19 (double[] parameters){
        double[] c = new double[]{1, 1.2, 3, 3.2};
        double[][] a = new double[][]{
                {3, 10, 30},
                {0.1, 10, 35},
                {3, 10, 30},
                {0.1, 10, 30}};
        double[][] p = new double[][]{
                {0.3689, 0.1170, 0.2673},
                {0.4699, 0.4387, 0.7470},
                {0.1091, 0.8732, 0.5547},
                {0.03815, 0.5743, 0.8828
                }
        };
        double sum = 0.0;
        sum = getSumF18AndF19(parameters, c, a, p, sum, 3);
        return -sum;
    }

    private double getSumF18AndF19(double[] parameters, double[] c, double[][] a, double[][] p, double sum, int i2) {
        for(int i  = 0; i < 4; i++){
            double partSum = 0.0;
            for(int j = 0; j < i2; j++){
                partSum += a[i][j] * Math.pow(parameters[j] - p[i][j], 2);
            }
            sum += c[i] * Math.exp(-partSum);
        }
        return sum;
    }

    private double f18 (double[] parameters){
        double x1 = parameters[0];
        double x2 = parameters[1];
        return
        (1+ (Math.pow(x1 + x2 + 1, 2)
            )
           *(19 - 14 * x1
              + Math.pow(3 * x1, 2)
              - 14 * x2
              + 6 * x1 * x2
              + Math.pow(3 * x2, 2)
            )


        ) *
        (30
            + Math.pow(2*x1 - 3 * x2, 2)
            *(18
            - 32*x1
            + 12 * Math.pow(x1, 2)
            + 48 * x2
            - 36 * x1 * x2
            + 27 * Math.pow(x2, 2))
         );
    }

    private double f17 (double[] parameters){
        double x1 = parameters[0];
        double x2 = parameters[1];
        return Math.pow(x2
                        - (5.1/(4*Math.pow(Math.PI, 2))) * Math.pow(x1, 2)
                + (5.0 / Math.PI) * x1 - 6
                , 2)
                + 10 * (1 - 1.0 / (8 * Math.PI)) * Math.cos(x1) + 10;
    }

    private double f16 (double[] parameters){
        double x1 = parameters[0];
        double x2 = parameters[1];
        return 4*Math.pow(x1, 2)
                - 2.1 * Math.pow(x1, 4)
                + (1.0 / 3.0) * Math.pow(x1, 6)
                + x1 * x2
                - 4 * Math.pow(x2, 2)
                + 4 * Math.pow(x2, 4);
    }

    private double f15 (double[] parameters){
        double sum = 0.0;
        double x1 = parameters[0];
        double x2 = parameters[1];
        double x3 = parameters[2];
        double x4 = parameters[3];
        double[] a = new double[] {0.1957, 0.1947, 0.1735,
                0.1600, 0.0844, 0.0627, 0.0456, 0.0342, 0.0342, 0.0235, 0.0246};
        double[] b =  new double[] {1.0 / 0.25, 1.0 / 0.5, 1.0 / 1, 1.0 / 2,
                1.0 / 4, 1.0 / 6, 1.0 / 8, 1.0 / 10, 1.0 / 12, 1.0 / 14, 1.0 / 16};
        for(int i  = 0; i < 11; i++){
            sum += Math.pow(a[i] - (x1 * (Math.pow(b[i], 2) + b[i]*x2) ) /
                    (Math.pow(b[i], 2) + b[i] * x3 + x4) , 2);
        }
        return sum;
    }

    private double f14 (double[] parameters){
        double[][] a = new double[][]{{-32, -16, 0, 16, 32,
                -32, -16, 0, 16, 32,-32, -16, 0, 16, 32,
                -32, -16, 0, 16, 32,-32, -16, 0, 16, 32 },
                {-32, -32, -32, -32, -32, -16, -16, -16, -16, -16,
                 0, 0, 0, 0, 0, 16, 16, 16, 16, 16, 32, 32, 32, 32, 32}};
        double sum = 0.0;
        for(int j = 0; j < 25; j++){
            double partSum = 0.0;
            for(int i = 0; i < 2; i++){
                double x = parameters[i];
                partSum += Math.pow(x - a[i][j], 6);
            }
            sum += 1.0 / (j+1+partSum);
        }
        return Math.pow(1.0 / 500.0 + sum, -1);
    }

    private double f13 (double[] parameters){
        double sum1 = 0.0;
        double sum2 = 0.0;
        int n = parameters.length;
        double a = Math.pow( Math.sin( 3 * Math.PI * parameters[0] ) , 2 );
        double xn = parameters[n-1];
        double b = Math.pow(xn-1, 2) * (1 + Math.pow(Math.sin(2*Math.PI*xn), 2));

        for(int i  = 0; i < n; i++){
            double x = parameters[i];
            sum1 += Math.pow(x-1, 2) * (1 + Math.pow(Math.sin(3 * Math.PI * x + 1), 2));
            sum2 += u(x, 5, 100, 4);
        }
        return 0.1 * (a + sum1 + b) + sum2;
    }

    private double f12 (double[] parameters){
        double sum1 = 0.0;
        double sum2 = 0.0;
        int n = parameters.length;
        double nn = n;
        double a = 10 * Math.sin( Math.PI * y(parameters[0]) );
        double b = Math.pow(y(parameters[n-1]) - 1, 2);
        for(int i = 0; i < n -1; i++){
            double c = Math.pow(y(parameters[i])-1, 2);
            double d = 1 + 10 * Math.pow( Math.sin( Math.PI * y(parameters[i+1])) , 2);
            sum1 += c*d;
        }
        for (double parameter : parameters) {
            sum2 += u(parameter, 10, 100, 4);
        }
        double e = a + sum1 + b;
        double f = (Math.PI / nn) * e;
        if(f+sum2 < 0){
            //System.out.println("What");
        }
        return Math.abs(f+sum2);
    }

    private double u(double x, double a, double k, double m){
        if(x>a){
            return k*Math.pow(x-a, m);
        }
        if(x < -a){
            return k*Math.pow(-x-a, m);
        }
        return 0;
    }

    private double y(double x){
        return 1 + ((x + 1) / 4);
    }

    private double f11 (double[] parameters){
        double sum = 0.0;
        double multiple = 1.0;

        for(int index = 0; index < parameters.length ; index++){
            int i = index+1;
            double d = parameters[index];
            sum += Math.pow(d, 2);
            multiple *= Math.cos( d / Math.sqrt(i) );
        }
        return  (1/4000) * sum - multiple + 1;
    }

    private double f10 (double[] parameters){
        double sumA = 0.0;
        double sumB = 0.0;
        for(double d: parameters){
            sumA += Math.pow(d, 2);
            sumB += Math.cos( 2 * Math.PI * d );
        }
        double n = parameters.length;
        double nD = 1 / n;
        double a = -0.2 * Math.sqrt( nD * sumA );
        double b = Math.exp( nD * sumB );
        return -20 * Math.exp(a) - b + 20 + Math.E;
    }

    private double f9 (double[] parameters){
        double sum = 0.0;
        for(double d: parameters){
            double a = Math.pow(d, 2);
            double b = 10 * Math.cos( 2 * Math.PI * d );
            sum += a - b + 10;
        }
        return sum;
    }

    private double f8 (double[] parameters){
        return f8Real(parameters) - optimalValue();
    }

    private double f8Real (double[] parameters){
        double sum = 0.0;
        for(double d: parameters){
            sum += -d * Math.sin( Math.sqrt(Math.abs(d)) );
        }
        return sum;
    }

    private double f7 (double[] parameters){
        double sum = 0.0;
        for(int i = 0 ; i < parameters.length ; i++){
             sum += (i + 1) *  Math.pow(parameters[i], 4);
        }
        return sum + /*f7Random*/ generator.nextDouble();
    }

    private double f6 (double[] parameters){
        double sum = 0.0;
        for(double d: parameters){
            sum += Math.pow(d + 0.5, 2);
        }
        return sum;
    }

    private double f5 (double[] parameters){
        double sum = 0.0;

        for(int i = 0; i < parameters.length -1 ; i++){
            double a = parameters[i+1] - Math.pow(parameters[i], 2);
            double b = 100 * Math.pow(a, 2);
            double c = parameters[i] - 1;
            double d = Math.pow(c, 2);
            sum += b + d;
        }
        return sum;
    }

    private double f4 (double[] parameters){
        double max = -101;
        for(double d: parameters){
            double n = Math.abs(d);
            max = max > n ? max : n;
        }
        return max;
    }

    private double f3 (double[] parameters){
        double sum = 0;
        for(int i = 0; i < parameters.length ; i++){
            double subSum = 0.0;
            for(int j = 0; j <= i; j++){
                subSum += parameters[j];
            }
            sum += Math.pow(subSum, 2);
        }
        return sum;
    }

    private double f2 (double[] parameters){
        double additionSum = 0.0;
        double multipleSum = 1.0;

        for(double p: parameters){
            double n = p;
            if (p < 0){
                n = 0-p;
            }
            additionSum += n;
            multipleSum *= n;
        }
        double totalSum = additionSum + multipleSum;
        return totalSum;
    }

    private double f1 (double[] parameters){
        double sum = 0.0;

        for(Double p: parameters){
            sum += Math.pow(p, 2);
        }

        return sum;
    }




}
