import util.C;
import static util.CMath.*;
import static java.lang.Math.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public class Julia extends Design {
    public Julia(
            int quality,
            double[] rangeX,
            double[] rangeY,
            Pallete pallete,
            int iterations) {
        super(quality, rangeX, rangeY, pallete, iterations);
        _fn = fn();
        _seed = seedGenerator();
    }

    @Override
    public double business() {
        C z = new C(c());
        double k;
        for (k = 0; k < iterations() && NORM(z) <= Julia.RADIUS; k += 1) {
            z = _fn.apply(z);
        }
        if (k < iterations()) {
            k += (1 - log(log(NORM(z))) / log(Julia.POWER));
        }
        return k;
    }

    @Override
    public Color color(double k) {
        if (k == iterations()) {
            return Color.BLACK;
        }
        return pallete().interpolate(Julia.SENSITIVITY * k / iterations() * pallete().length);
    }

    @Override
    public Function<C, C> fn() {
        return new Function<C, C>() {
            @Override
            public C apply(C z) {
                return ADD(POW(z, 2), _seed);
            }
        };
    }

    public C seedGenerator() {
        int breakN = 0;
        while(true)
        {
            _seed = new C(
                2.25*Math.random()-2,
                2.25*Math.random()-1.25
            );
            breakN = 0;
            C temp = new C(_seed);

            for(short n = 1; n < 256; n++)
            {
                temp = _fn.apply(temp);

                if(NORM(temp) > 2)
                {
                    breakN = n;
                    break;
                }
            }
            double rand = Math.random();
            if(breakN >= 20) {
                if(rand < Math.exp((breakN-256)/45))
                    break;
            } else if(breakN >= 6) {
                if(rand < Math.exp((breakN-256)/30))
                    break;
            } else
            if(rand < Math.exp((breakN-256)/20))
                break;
        }
        return _seed;
    }

    private static final double RADIUS = 1000;
    private static final double POWER = 2;
    private static final double SENSITIVITY = 1;
    private Function<C, C> _fn;
    public C _seed;
}
