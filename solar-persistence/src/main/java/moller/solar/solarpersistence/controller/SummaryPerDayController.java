package moller.solar.solarpersistence.controller;

import moller.solarpersistence.openapi.api.SummaryPerDayControllerApiController;
import moller.solarpersistence.openapi.api.SummaryPerDayControllerApiDelegate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummaryPerDayController extends SummaryPerDayControllerApiController {
    public SummaryPerDayController(SummaryPerDayControllerApiDelegate delegate) {
        super(delegate);
    }
}
