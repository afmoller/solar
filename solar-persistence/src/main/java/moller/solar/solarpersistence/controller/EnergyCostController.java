package moller.solar.solarpersistence.controller;

import moller.solarpersistence.openapi.api.EnergyCostControllerApiController;
import moller.solarpersistence.openapi.api.EnergyCostControllerApiDelegate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnergyCostController extends EnergyCostControllerApiController {
    public EnergyCostController(EnergyCostControllerApiDelegate delegate) {
        super(delegate);
    }
}
