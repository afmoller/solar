package moller.solar.solarpersistence.controller;

import moller.solarpersistence.openapi.api.CsvControllerApiController;
import moller.solarpersistence.openapi.api.CsvControllerApiDelegate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsvController extends CsvControllerApiController {
    public CsvController(CsvControllerApiDelegate delegate) {
        super(delegate);
    }
}
