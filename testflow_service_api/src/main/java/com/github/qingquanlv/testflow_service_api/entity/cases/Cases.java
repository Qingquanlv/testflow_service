package com.github.qingquanlv.testflow_service_api.entity.cases;

import com.github.qingquanlv.testflow_service_api.entity.cases.database.DataBaseCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.parse.ParseCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.request.RequestCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.verification.VerificationCases;

import java.util.List;

public class Cases {

    private List<RequestCases> requestCases;

    private List<ParseCases> parserCases;

    private List<DataBaseCases> dataBaseCases;

    private List<VerificationCases> verificationCases;


    public List<RequestCases> getRequestCases() {
        return requestCases;
    }

    public void setRequestCases(List<RequestCases> requestCases) {
        this.requestCases = requestCases;
    }

    public List<ParseCases> getParserCases() {
        return parserCases;
    }

    public void setParserCases(List<ParseCases> parserCases) {
        this.parserCases = parserCases;
    }


    public List<DataBaseCases> getDataBaseCases() {
        return dataBaseCases;
    }

    public void setDataBaseCases(List<DataBaseCases> dataBaseCases) {
        this.dataBaseCases = dataBaseCases;
    }


    public List<VerificationCases> getVerificationCases() {
        return verificationCases;
    }

    public void setVerificationCases(List<VerificationCases> verificationCases) {
        this.verificationCases = verificationCases;
    }
}
