package de.op.atom.core;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import de.op.atom.gen.core.model.ErrorDTO;


public class ErrorResponseBuilder {

    public static ResponseBuilder error(int statusCode, String statusReason) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(statusCode);
        errorDTO.setMessage(statusReason);
        return Response.status(statusCode, statusReason)
                       .entity(errorDTO);
    }
    
    public static ResponseBuilder error(int statusCode, String statusReason, Object... stringFormats) {
        return error(statusCode, String.format(statusReason, stringFormats));
    }

    public static ResponseBuilder error(Status status, String statusReason) {
        return error(status.getStatusCode(), statusReason);
    }
    
    public static ResponseBuilder error(Status status, String statusReason, Object... stringFormats) {
        return error(status.getStatusCode(), statusReason, stringFormats);
    }

    public static ResponseBuilder error(Status status) {
        return error(status.getStatusCode(), status.getReasonPhrase());
    }
}
