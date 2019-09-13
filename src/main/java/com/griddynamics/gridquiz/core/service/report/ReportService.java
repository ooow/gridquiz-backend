package com.griddynamics.gridquiz.core.service.report;

import java.io.IOException;

public interface ReportService {
    byte[] generateReport() throws IOException;
}
