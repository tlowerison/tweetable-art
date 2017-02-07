package util;

import static java.lang.Math.*;
import java.util.function.Function;
import java.util.function.BiFunction;

public class CMath {

	public static C NEG(C num) {
		return new C(-num.R(), -num.Im());
	}

	public static C CONJ(C num) {
		return new C(num.R(), -num.Im());
	}

	public static double NORM(C num) {
		return MUL(num, CONJ(num)).R();
	}

	public static double ARG(C num) {
		if (num.Im() == 0) {
			return signum(num.R());
		}
		return atan(num.Im() / num.R());
	}

	public static C ADD(C num1, C num2) {
		return new C(
			num1.R() + num2.R(),
			num1.Im() + num2.Im()
		);
	}

	public static C SUB(C num1, C num2) {
		return new C(
			num1.R() - num2.R(),
			num1.Im() - num2.Im()
		);
	}

	public static C MUL(C num1, C num2) {
		return new C(
			num1.R() * num2.R() - num1.Im() * num2.Im(),
			num1.R() * num2.Im() + num1.Im() * num2.R()
		);
	}

	public static C DIV(C num1, C num2) {
		C prod = MUL(num1, num2);
		double norm = NORM(num2);
		return new C(prod.R() / norm, prod.Im() / norm);
	}

	public static C POW(C base, double power) {
		C z = new C(base);
		for (int i = 1; i < power; i += 1) {
			z = MUL(z, base);
		}
		return z;
		/*
		double radius = pow(NORM(base), power);
		double angle = power * ARG(base);
		if (radius == 0) {
			return new C(0, 0);
		}
		return new C(
			radius * cos(angle),
			radius * sin(angle)
		);
		*/
	}

	public static C EXP(C num1, C num2) {
		if (num1.Im() == 0) {
			return new C(
				exp(num1.R()) * cos(num2.R()),
				exp(num1.R()) * sin(num2.Im())
			);
		} else {
			return EXP(E, MUL(num2, LN(num1)));
		}
	}

	public static C LN(C num) {
		return new C(
			log(NORM(num)),
			ARG(num)
		);
	}

	public static C COS(C num) {
		return new C(
			cos(num.R()) * cosh(num.Im()),
			-sin(num.R()) * sinh(num.Im())
		);
	}

	public static C SIN(C num) {
		return new C(
			sin(num.R()) * cosh(num.Im()),
			cos(num.R()) * sinh(num.Im())
		);
	}

	public static C TAN(C num) {
		double arb = cos(2 * num.R()) + cosh(2 * num.Im());
		return new C(
			sin(2 * num.R()) / arb,
			sinh(2 * num.Im()) / arb
		);
	}

	public static C COSH(C num) {
		return new C(
			cos(num.Im()) * cosh(num.R()),
			sin(num.Im()) * sinh(num.R())
		);
	}

	public static C SINH(C num) {
		return new C(
			cos(num.Im()) * sinh(num.R()),
			sin(num.Im()) * cosh(num.R())
		);
	}

	public static C TANH(C num) {
		double arb1 = exp(2 * NORM(num));
		double arb2 = 2 * exp(2 * num.R());
		return new C(
			(arb1 - 1) / (arb1 - arb2 * cos(2 * num.Im()) + 1),
			- arb2 * sin(2 * num.Im()) / (arb1 - arb2 * cos(2 * num.Im()) + 1)
		);
	}

	/* Function f must mutate the first argument. */
	public static C accumulate(C[] nums, BiFunction<C, C, C> acc_fn, Function<C, C> term_fn, C base) {
		for (C num : nums) {
			acc_fn.apply(base, term_fn.apply(num));
		}
		return base;
	}

	public static C sum(C[] nums) {
		return accumulate(nums, new BiFunction<C, C, C>() {
			@Override
			public C apply(C num1, C num2) {
				num1.R(num1.R() + num2.R());
				num1.Im(num1.Im() + num2.Im());
				return num1;
			}
		}, new Function<C, C>() {
			@Override
			public C apply(C num) {
				return num;
			}
		}, new C(ZERO));
	}

	public static C product(C[] nums) {
		return accumulate(nums, new BiFunction<C, C, C>() {
			@Override
			public C apply(C num1, C num2) {
				num1.R(num1.R() * num2.R());
				num1.Im(num1.Im() * num2.Im());
				return num1;
			}
		}, new Function<C, C>() {
			@Override
			public C apply(C num) {
				return num;
			}
		}, new C(ONE));
	}

	public static final C E = new C(Math.E, 0);
	public static final C ONE = new C(1, 0);
	public static final C I = new C(0, 1);
	public static final C ZERO = new C(0, 0);
}
