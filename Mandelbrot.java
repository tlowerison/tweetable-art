import util.C;
import static util.CMath.*;
import static java.lang.Math.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public class Mandelbrot extends Design {
	public Mandelbrot(
		int quality,
		double[] rangeX,
		double[] rangeY,
		Pallete pallete,
		int iterations) {
		super(quality, rangeX, rangeY, pallete, iterations);
        _fn = fn();
	}

	@Override
	public double business() {
		C z = new C(c());
		double k;
		for (k = 0; k < iterations() && NORM(z) <= Mandelbrot.RADIUS; k += 1) {
			z = _fn.apply(z);
		}
		if (k < iterations()) {
			k += (1 - log(log(NORM(z))) / log(Mandelbrot.POWER));
		}
		return k;
	}

	@Override
	public Color color(double k) {
		if (k == iterations()) {
			return Color.BLACK;
		}
		return pallete().interpolate(Mandelbrot.SENSITIVITY * k / iterations() * pallete().length);
	}

    @Override
    public Function<C, C> fn() {
        return new Function<C, C>() {
            @Override
            public C apply(C z) {
                return ADD(POW(z, 2), c());
            }
        };
    }

	private static final double RADIUS = 1000;
	private static final double POWER = 2;
	private static final double SENSITIVITY = 1.5;
    private Function<C, C> _fn;
}
