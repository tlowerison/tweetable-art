import java.awt.Color;

/**
Wrapper class for Color array representing a color pallete.
*/
public class Pallete {
	public Pallete(Color... arr) {
		_arr = arr;
		length = _arr.length;
	}

	/*  
		Returns the correct interpolation between
		the color at index i and the following or
		preceding color in the pallete.
	*/
	public Color interpolate(double index) {
		int i = (int) Math.floor(index);
		double coef = index - i;
		Color alpha = _arr[(i + length) % length];
		Color beta = _arr[(i + length + 1) % length];
		return new Color(
			(int) (alpha.getRed() + (beta.getRed() - alpha.getRed()) * coef),
			(int) (alpha.getGreen() + (beta.getGreen() - alpha.getGreen()) * coef),
			(int) (alpha.getBlue() + (beta.getBlue() - alpha.getBlue()) * coef)
		);
	}

	public void iterations(int iterations) {
		_iterations = iterations;
	}

	private Color[] _arr;
	public final int length;
	private int _iterations;
	public static final Pallete CORAL = new Pallete(
		new Color(0x000050),
		new Color(0xFFFFFF),
		new Color(0xE06800),
		new Color(0x800000),
		new Color(0x000000));
	public static final Pallete WARM_RAINBOW = new Pallete(
		new Color(0x8993E0),
		new Color(0xE06754),
		new Color(0xE0A529),
		new Color(0x7E9E73),
		new Color(0xA272AE));
	public static final Pallete THERMOMETER = new Pallete(
		new Color(0x1C77C3),
		new Color(0x39A9DB),
		new Color(0x40BCD8),
		new Color(0xF39237),
		new Color(0xD63230));
	public static final Pallete FRENCH = new Pallete(
		new Color(0x011627),
		new Color(0xFDFFFC),
		new Color(0x2EC4B6),
		new Color(0xE71D36),
		new Color(0xFF9F1C));
	public static final Pallete CRAYONS = new Pallete(
		new Color(0x5BC0EB),
		new Color(0xFDE74C),
		new Color(0x9BC53D),
		new Color(0xE55934),
		new Color(0xFA7921));
	public static final Pallete BRIGHT = new Pallete(
		new Color(0x6699CC),
		new Color(0xFFF275),
		new Color(0xFF8C42),
		new Color(0xFF3C38),
		new Color(0xA23E48));
	public static final Pallete[] STOCK_PALLETES = new Pallete[] {
		CORAL,
		WARM_RAINBOW,
		THERMOMETER,
		FRENCH,
		CRAYONS,
		BRIGHT
	};
}
