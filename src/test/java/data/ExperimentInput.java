package data;

public class ExperimentInput {
    private final int experimentNumber;
    private final int f1, f2, f3, f4, f5, f6, f7, f8;

    public ExperimentInput(int experimentNumber, int f1, int f2, int f3, int f4,
                           int f5, int f6, int f7, int f8) {
        this.experimentNumber = experimentNumber;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f5 = f5;
        this.f6 = f6;
        this.f7 = f7;
        this.f8 = f8;
    }

    public int getExperimentNumber() { return experimentNumber; }
    public int getF1() { return f1; }
    public int getF2() { return f2; }
    public int getF3() { return f3; }
    public int getF4() { return f4; }
    public int getF5() { return f5; }
    public int getF6() { return f6; }
    public int getF7() { return f7; }
    public int getF8() { return f8; }

    @Override
    public String toString() {
        return "Exp#" + experimentNumber +
                " [f1=" + f1 + ", f2=" + f2 + ", f3=" + f3 + ", f4=" + f4 +
                ", f5=" + f5 + ", f6=" + f6 + ", f7=" + f7 + ", f8=" + f8 + "]";
    }
}
