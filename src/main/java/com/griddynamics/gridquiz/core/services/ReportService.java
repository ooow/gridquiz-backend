package com.griddynamics.gridquiz.core.services;

import java.io.IOException;

public interface ReportService {
    byte[] generateReport() throws IOException;
}
