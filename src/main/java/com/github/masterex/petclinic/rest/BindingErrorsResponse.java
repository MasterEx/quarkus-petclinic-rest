/*
 * Copyright 2019 Periklis Ntanasis
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.masterex.petclinic.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.validation.ConstraintViolation;

/**
 * @author Vitaliy Fedoriv
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 * @param <T> The type of the error.
 */
public class BindingErrorsResponse<T> {

    BindingErrorsResponse(Set<ConstraintViolation<T>> violations) {
        addAllErrors(violations);
    }

    private List<BindingError> bindingErrors = new ArrayList<>();

    public List<BindingError> getBindingErrors() {
        return bindingErrors;
    }

    public void setBindingErrors(List<BindingError> bindingErrors) {
        this.bindingErrors = bindingErrors;
    }

    public void addError(BindingError bindingError) {
        this.bindingErrors.add(bindingError);
    }

    public final <T> void addAllErrors(Set<ConstraintViolation<T>> violations) {
        for (ConstraintViolation<T> fieldError : violations) {
            BindingError error = new BindingError();
            error.setObjectName(fieldError.getRootBean().getClass().getName());
            error.setFieldName(fieldError.getPropertyPath().toString());
            error.setFieldValue(fieldError.getLeafBean().toString());
            error.setErrorMessage(fieldError.getMessage());
            addError(error);
        }
    }

    public String toJSON() {

        JsonArrayBuilder jsonErrors = Json.createArrayBuilder();

        bindingErrors.stream().forEach(error -> {
            JsonObjectBuilder jsonError = Json.createObjectBuilder();
            jsonError
                    .add("objectName", error.getObjectName())
                    .add("fieldName", error.getFieldName())
                    .add("fieldValue", error.getFieldValue())
                    .add("errorMessage", error.getErrorMessage());
            jsonErrors.add(jsonError);
        });

        JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        jsonObject.add("bindingErrors", jsonErrors).build();

        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "BindingErrorsResponse [bindingErrors=" + bindingErrors + "]";
    }

    protected class BindingError {

        private String objectName;
        private String fieldName;
        private String fieldValue;
        private String errorMessage;

        public BindingError() {
            this.objectName = "";
            this.fieldName = "";
            this.fieldValue = "";
            this.errorMessage = "";
        }

        protected String getObjectName() {
            return objectName;
        }

        protected void setObjectName(String objectName) {
            this.objectName = objectName;
        }

        protected String getFieldName() {
            return fieldName;
        }

        protected void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        protected String getFieldValue() {
            return fieldValue;
        }

        protected void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        protected String getErrorMessage() {
            return errorMessage;
        }

        protected void setErrorMessage(String error_message) {
            this.errorMessage = error_message;
        }

        @Override
        public String toString() {
            return "BindingError [objectName=" + objectName + ", fieldName=" + fieldName + ", fieldValue=" + fieldValue
                    + ", errorMessage=" + errorMessage + "]";
        }

    }

}
