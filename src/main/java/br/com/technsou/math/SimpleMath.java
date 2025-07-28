package br.com.technsou.math;

import org.springframework.stereotype.Component;

@Component
public class SimpleMath {
    public double sum(Double a, Double b) { return a + b; }

    public double substration(Double a, Double b) { return a - b; }

    public double division(Double a, Double b) { return a / b; }

    public double mean(Double a, Double b) { return (a + b) / 2; }

    public double squareRoot(Double a, Double b) { return Math.sqrt(a); }

}
