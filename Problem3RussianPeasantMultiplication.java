package homework4;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Problem3RussianPeasantMultiplication {	
	private BigDecimal countDivisions = new BigDecimal(0), countMultiplications = new BigDecimal(0), countAdditions = new BigDecimal(0);
	
	public static void main(String[] args) {
		new Problem3RussianPeasantMultiplication();
	}

	public Problem3RussianPeasantMultiplication() {
		Data data = new Data(getClass());
		BigDecimal a = new BigDecimal(50), b = new BigDecimal(65);
		a = new BigDecimal(33); b = new BigDecimal(33);
		a = new BigDecimal(101); b = new BigDecimal(830);
		if ((a.multiply(b)).compareTo(russianPeasantMultiplicationIteration(a, b)) == 0) {
			System.out.println(countMultiplications + " " + countDivisions + " " + countAdditions + "\n");
			countMultiplications = countDivisions = countAdditions = new BigDecimal(0);
			final String r = "Russian Peasant Multiplication";
			data.write("Size of N\t" + r + "\tMultiplications\tDivisions\tAdditions\tTotal\n", true);
			long start = System.nanoTime();
			BigDecimal i = new BigDecimal("100000"), max = new BigDecimal("100000000");
			while (i.compareTo(max) <= 0) {
				BigDecimal n = randomNumber(i), m = randomNumber(i);
				TimeAlgorithm russianPeasantMultiplication = new TimeAlgorithm() {
					@Override
					public String algorithmName() {
						return r;
					}
					@Override
					public Object algorithm() {
						return russianPeasantMultiplicationIteration(n, m);
					}
				};
				if ((n.multiply(m)).compareTo((BigDecimal) russianPeasantMultiplication.getResult()) == 0) {
					data.write(n + "\t" + russianPeasantMultiplication.getTime() + "\t" + countMultiplications + "\t" + countDivisions + "\t" + countAdditions + "\t" + (countMultiplications.add(countDivisions).add(countAdditions)) + "\n", true);
					data.write(i + " -> " + n + " * " + m + " = " + russianPeasantMultiplication.getResult() + " -> " + russianPeasantMultiplication.toString() + ((i.compareTo(new BigDecimal("100000000")) >= 0) ? "" : "\n"), false);
					System.out.println(i + " -> " + ((System.nanoTime() - start) / 60000000000L) + " minutes");
					countMultiplications = countDivisions = countAdditions = new BigDecimal(0);
					i = (i.compareTo(max) == 0) ? i.add(new BigDecimal(1)) : (i.add(new BigDecimal(1)).max(n.min(m))).min(max);
				} else {
					System.out.println("Multiplication results don't match."); System.exit(0);
				}
			}
		}
	}
	
	public BigDecimal russianPeasantMultiplicationRecursion(BigDecimal n, BigDecimal m) {
		BigDecimal one = new BigDecimal(1), two = new BigDecimal(2);
		if ((n.remainder(two)).compareTo(new BigDecimal(0)) == 0) {
			 n = n.divide(two); m = m.multiply(two); countDivisions = countDivisions.add(one); countMultiplications = countMultiplications.add(one);
		} 
		if ((n.remainder(two)).compareTo(one) == 0) {
			if (n.compareTo(one) > 0) {
				m = m.add(russianPeasantMultiplicationRecursion(n.subtract(one), m)); countAdditions = countAdditions.add(one);
			}
			return m;
		} 		
		return russianPeasantMultiplicationRecursion(n, m);
	}
	
	public BigDecimal russianPeasantMultiplicationIteration(BigDecimal n, BigDecimal m) {
		BigDecimal one = new BigDecimal(1), two = new BigDecimal(2), result = new BigDecimal(0);
		while (true) {
			if ((n.remainder(two)).compareTo(new BigDecimal(0)) == 0) {
				 n = n.divide(two); m = m.multiply(two); countDivisions = countDivisions.add(one); countMultiplications = countMultiplications.add(one);
			} 
			if ((n.remainder(two)).compareTo(one) == 0) {
				result = result.add(m); 
				if (n.compareTo(one) > 0) {
					n = n.subtract(one); countAdditions = countAdditions.add(one);
				} else {
					break;
				}
			} 		
		}
		return result;
	}
	
	private BigDecimal randomNumber(BigDecimal number) {
		BigDecimal step = new BigDecimal("100000"), min = number.subtract(step), max = number.add(step);
	    return (min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)))).setScale(0, RoundingMode.HALF_EVEN);
	}
}