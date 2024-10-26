package moller.solar.solarpersistence.controller;

import moller.solarpersistence.openapi.api.ReturnOnInvestmentControllerApiController;
import moller.solarpersistence.openapi.api.ReturnOnInvestmentControllerApiDelegate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReturnOnInvestmentController extends ReturnOnInvestmentControllerApiController {
    public ReturnOnInvestmentController(ReturnOnInvestmentControllerApiDelegate delegate) {
        super(delegate);
    }
}
