
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
        double pointAngle = Math.atan2(pointYRelative, pointXRelative); // угол точки относительно начала полосы
        double pointDistance = Math.hypot(pointXRelative, pointYRelative); // расстояние от точки до начала полосы

        // Проверяем условия, при которых точка будет лежать на полосе
        return pointAngle >= angle && pointAngle <= angle + Math.PI / 2 && pointDistance <= length
                && pointDistance * Math.tan(angle) <= width;
    }
}
