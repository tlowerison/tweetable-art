package util;

public class C {
	public C(double re, double im) {
		_re = re;
		_im = im;
	}

	public C(C cplx) {
		_re = cplx.R();
		_im = cplx.Im();
	}

	public double R() {
		return _re;
	}

	public double Im() {
		return _im;
	}

	public void R(double re) {
		_re = re;
	}

	public void Im(double im) {
		_im = im;
	}

	@Override
	public String toString() {
		return _re.toString() + " " + _im.toString();
	}

	private Double _re, _im;
}
