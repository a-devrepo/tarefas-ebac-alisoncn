package com.nca.clienteservice.resources.handler;

import com.nca.clienteservice.exceptions.DAOException;
import com.nca.clienteservice.exceptions.RegistroNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

//    @Override
//    @ResponseStatus(BAD_REQUEST)
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
//                                                                          HttpHeaders headers,
//                                                                          HttpStatusCode status,
//                                                                          WebRequest request) {
//        String error = ex.getParameterName() + " parameter is missing";
//        ApiError apiError = new ApiError(BAD_REQUEST);
//        apiError.setMessage(error);
//        return buildResponseEntity(apiError);
//    }
//
//    @Override
//    @ResponseStatus(BAD_REQUEST)
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatusCode status,
//                                                                  WebRequest request) {
//        ApiError apiError = new ApiError(BAD_REQUEST);
//        apiError.setMessage(ex.getMessage());
//        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
//        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
//        return buildResponseEntity(apiError);
//    }
//
//    @Override
//    @ResponseStatus(BAD_REQUEST)
//    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
//                                                                          HttpHeaders headers, HttpStatusCode status,
//                                                                          WebRequest request) {
//        ApiError apiError = new ApiError(BAD_REQUEST);
//        apiError.setMessage(ex.getMessage());
//        return buildResponseEntity(apiError);
//    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DAOException.class)
    protected ResponseEntity<ApiError> handleDaoException(DAOException ex
            , HttpServletRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(RegistroNaoEncontradoException.class)
    protected ResponseEntity<ApiError> handleRegistroNaoEnonctradoException(RegistroNaoEncontradoException ex
            , HttpServletRequest request) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
