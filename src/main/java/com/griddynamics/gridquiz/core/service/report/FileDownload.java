package com.griddynamics.gridquiz.core.service.report;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

public class FileDownload {
    public static IData newFileDownload() {
        return new Builder();
    }

    public interface IBuild {
        void send() throws IOException;
    }

    public interface IContentType {
        IBuild withContentType(String val);

        IBuild withXlsContentType();

        IBuild withXlsxContentType();
    }

    public interface IResponse {
        IContentType applyToResponse(HttpServletResponse val);
    }

    public interface IFileName {
        IResponse withFileName(String val);
    }

    public interface IData {
        IFileName withData(byte[] val);

        IFileName withData(InputStream val);
    }

    public static final class Builder implements IContentType, IResponse, IFileName, IData, IBuild {
        private String contentType;
        private HttpServletResponse response;
        private String fileName;
        private InputStream is;

        private Builder() {
        }

        @Override
        public IBuild withContentType(String val) {
            contentType = val;
            return this;
        }

        @Override
        public IBuild withXlsContentType() {
            contentType = "application/vnd.ms-excel";
            return this;
        }

        @Override
        public IBuild withXlsxContentType() {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            return this;
        }

        @Override
        public IContentType applyToResponse(HttpServletResponse val) {
            response = val;
            return this;
        }

        @Override
        public IResponse withFileName(String val) {
            fileName = val;
            return this;
        }

        @Override
        public IFileName withData(byte[] val) {
            is = new ByteArrayInputStream(val);
            return this;
        }

        @Override
        public IFileName withData(InputStream val) {
            is = val;
            return this;
        }

        @Override
        public void send() throws IOException {
            addContentLength();
            addExcelContentType();
            addDispositionHeader();

            writeStream();
        }

        private void writeStream() throws IOException {
            IOUtils.copy(is, response.getOutputStream());
        }

        private void addContentLength() {

        }

        private void addExcelContentType() {
            response.setContentType(contentType);
        }

        private void addDispositionHeader() {
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        }
    }
}
