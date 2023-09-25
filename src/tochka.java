class Tochka {
    double centerX;
    double centerY;


    public Tochka(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public String toString() {
        return "Tochka: centerX=" + centerX + ", centerY=" + centerY ;
    }
    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }
}
