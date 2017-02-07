import util.C;
import static util.CMath.*;
import static java.lang.Math.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public abstract class Design {
	public Design(
		int quality,
		double[] rangeX,
		double[] rangeY,
		Pallete pallete,
		int iterations) {
		_quality = quality;
		_rangeX = rangeX;
		_rangeY = rangeY;
		_pallete = pallete;
		_iterations = iterations;
		_pallete.iterations(_iterations);
		_deltaX = (_rangeX[1] - _rangeX[0]) / _quality;
		_deltaY = (_rangeY[1] - _rangeY[0]) / _quality;
	}

	public BufferedImage image() {
		BufferedImage image = new BufferedImage(_quality, _quality, BufferedImage.TYPE_INT_RGB);
		Color color;

		for (double x = _rangeX[0]; x < _rangeX[1]; x += _deltaX) {
			for (double y = _rangeY[0]; y < _rangeY[1]; y += _deltaY) {
				_c.R(x);
				_c.Im(y);

				color = color(business());

				try {
					image.setRGB(
						(int) Math.round((x - _rangeX[0]) / _deltaX),
						(int) Math.round((y - _rangeY[0]) / _deltaY),
						color.getRed() * 256 * 256 + color.getGreen() * 256 + color.getBlue()
					);
				} catch (ArrayIndexOutOfBoundsException ex) {}
			}
		}

		return image;
	}

	abstract public Color color(double k);
	abstract public double business();

	public void rangeX(double[] rangeX) {
		_rangeX = rangeX;
        if (_deltaX < (_rangeX[1] - _rangeX[0]) / _quality) {
            _scale *= 10;
        }
		_deltaX = (_rangeX[1] - _rangeX[0]) / _quality;
        iterations((int) (sqrt(abs(2*sqrt(abs(1-sqrt(5*_scale)))))*66.5));
	}
	public void rangeY(double[] rangeY) {
		_rangeY = rangeY;
		_deltaY = (_rangeY[1] - _rangeY[0]) / _quality;
	}
	public double[] rangeX() {
		return _rangeX;
	}
	public double[] rangeY() {
		return _rangeY;
	}
    public double xWidth() {
        return _rangeX[1] - _rangeX[0];
    }
    public double yHeight() {
        return _rangeY[1] - _rangeY[0];
    }
	public C c() {
		return _c;
	}
    public void iterations(int iterations) {
        _iterations = iterations;
    }
	public int iterations() {
		return _iterations;
	}
	public Pallete pallete() {
		return _pallete;
	}
    abstract public Function<C, C> fn();

	private C _c = new C(ZERO);
	private int _quality;
	private double[] _rangeX = new double[2];
	private double[] _rangeY = new double[2];
	private double _deltaX, _deltaY;
	private Pallete _pallete;
	private int _iterations;
    private double _scale;

	public static final double[] DEFAULTX = new double[] {-2, 1};
	public static final double[] DEFAULTY = new double[] {-1.5, 1.5};
    public static final double[] ORIGINX = new double[] {-1.5, 1.5};
    public static final double[] ORIGINY = new double[] {-1.5, 1.5};
}
