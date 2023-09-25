
class Polosa {
     double centerX;
     double centerY;
     double angle;
    double width;
    double length;

    public Polosa(double centerX, double centerY, double width, double length, double angle) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.length = length;
        this.angle = angle;
    }

    public String toString() {
        return "Polosa: centerX=" + centerX + ", centerY=" + centerY + ",width=" + width + ", length=" + length + ", angle=" + angle ;
    }
    public boolean containsTochki(Tochka tochka) {
        // Проверяем, лежит ли точка на полосе
        double pointXRelative = tochka.centerX - centerX; // относительная координата X точки
        double pointYRelative = tochka.centerY - centerY; // относительная координата Y точки
        int rotatedX = (int) (pointXRelative * Math.cos(-angle) - pointYRelative * Math.sin(-angle));
        int rotatedY = (int) (pointXRelative * Math.sin(-angle) + pointYRelative * Math.cos(-angle));
        // Проверяем условия, при которых точка будет лежать на полосе
        return rotatedX >= 0 && rotatedX <= width && rotatedY >= 0 && rotatedY <= length; }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public double getAngle() {
        return angle;
    }
}
