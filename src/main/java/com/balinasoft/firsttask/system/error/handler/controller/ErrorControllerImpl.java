package com.balinasoft.firsttask.system.error.handler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/error")
public class ErrorControllerImpl implements ErrorController {

    private final ErrorAttributes errorAttributes;

    @Autowired
    public ErrorControllerImpl(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    public Object error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, false);
        HttpStatus status = getStatus(request);

        //if not start width api return html
//        if (!((String) body.get("path")).startsWith("/api")) {
//            return new ModelAndView("error/error", body);
//        }

        return new ResponseEntity<>(body, status);
    }

    private Map<String, Object> getErrorAttributes(javax.servlet.http.HttpServletRequest request,
                                                   boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(requestAttributes,
                includeStackTrace);

        errorAttributes.remove("message");
        errorAttributes.remove("exception");
        errorAttributes.remove("timestamp");
        errorAttributes.remove("path");

        return errorAttributes;
    }

    private HttpStatus getStatus(javax.servlet.http.HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
