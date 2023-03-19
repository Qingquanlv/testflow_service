package com.github.qingquanlv.testflow_service_api.entity.cases;

import com.github.qingquanlv.testflow_service_api.entity.cases.database.DataBaseCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.parse.ParseCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.request.RequestCasesRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.verification.VerificationCases;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public class Cases {

    private List<RequestCasesRequest> requestCases;

    private List<ParseCases> parserCases;

    private List<DataBaseCases> dataBaseCases;

    private List<VerificationCases> verificationCases;


    public List<RequestCasesRequest> getRequestCases() {
        return requestCases;
    }

    public void setRequestCases(List<RequestCasesRequest> requestCases) {
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
