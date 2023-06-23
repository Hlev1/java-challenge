package io.carbonchain.hiring.java.controller;

import io.carbonchain.hiring.java.models.request.Request;
import io.carbonchain.hiring.java.exception.ApplicationException;

public interface Controller {
    public String handle(Request request) throws ApplicationException;
}
