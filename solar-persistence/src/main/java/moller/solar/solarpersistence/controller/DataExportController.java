package moller.solar.solarpersistence.controller;

import moller.solarpersistence.openapi.api.DataExportControllerApiController;
import moller.solarpersistence.openapi.api.DataExportControllerApiDelegate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataExportController extends DataExportControllerApiController {
    public DataExportController(DataExportControllerApiDelegate delegate) {
        super(delegate);
    }
}
