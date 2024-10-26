package moller.solar.solarpersistence.controller;

import moller.solarpersistence.openapi.api.EnergySaleCompensationControllerApiController;
import moller.solarpersistence.openapi.api.EnergySaleCompensationControllerApiDelegate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnergySaleCompensationController extends EnergySaleCompensationControllerApiController {
    public EnergySaleCompensationController(EnergySaleCompensationControllerApiDelegate delegate) {
        super(delegate);
    }
}
