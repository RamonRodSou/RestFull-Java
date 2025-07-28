package br.com.technsou.controllers;

import br.com.technsou.exception.UnsupportedMathOperationException;
import br.com.technsou.math.SimpleMath;
import br.com.technsou.utils.NumberConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    private final SimpleMath simpleMath;

    @Autowired
    public MathController(SimpleMath simpleMath) {
         this.simpleMath = simpleMath;
    }

    @RequestMapping("/{operation}/{numberOne}/{numberTwo}")
    public double operation(
            @PathVariable("operation") String operation,
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws Exception {
        if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        var a = NumberConverter.convertToDouble(numberOne);
        var b = NumberConverter.convertToDouble(numberTwo);

        if(operation.equals("sum")) return simpleMath.sum(a, b);
        if(operation.equals("substration")) return simpleMath.substration(a, b);
        if(operation.equals("division")) return simpleMath.division(a, b);
        if(operation.equals("mean")) return simpleMath.mean(a, b);
        if(operation.equals("square-root")) return simpleMath.squareRoot(a, 0.0);
        throw new UnsupportedMathOperationException("Unsupported operation for single parameter: " + operation);
    }

}
