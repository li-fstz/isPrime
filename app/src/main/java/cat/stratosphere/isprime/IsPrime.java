package cat.stratosphere.isprime;

import java.util.ArrayList;

public class IsPrime {
    private long num;
    private ArrayList<Long> factors = new ArrayList<Long>();
    IsPrime (long num) {
        this.num = num;
        long x = 2;
        while (num != 1) {
            if (x * x > num) {
                factors.add(num);
                return;
            }
            if (num % x != 0) {
                x ++;
            } else {
                factors.add (x);
                num /= x;
            }
        }
    }
    public boolean isPrime () {
        return factors.size() == 1;
    }
    public String toString () {
        StringBuffer s = new StringBuffer();
        if (isPrime()) {
            s.append(num).append(" 是一个质数");
        } else {
            s.append(num).append(" = ");
            boolean first = true;
            for (long i: factors) {
                if (!first) {
                    s.append (" × ");
                } else {
                    first = false;
                }
                s.append (i);
            }
        }
        return s.toString();
    }
}
