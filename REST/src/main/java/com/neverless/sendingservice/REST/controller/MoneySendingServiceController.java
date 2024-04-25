package com.neverless.sendingservice.REST.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.neverless.sendingservice.REST.dto.requests.TransferRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.TransferStatusRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.WithdrawRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.WithdrawStatusRequestDTO;
import com.neverless.sendingservice.REST.dto.response.TransferReqResponseDTO;
import com.neverless.sendingservice.REST.dto.response.TransferStatusResponseDTO;
import com.neverless.sendingservice.REST.dto.response.WithdrawReqResponseDTO;
import com.neverless.sendingservice.REST.dto.response.WithdrawStatusResponseDTO;
import com.neverless.sendingservice.REST.service.MoneySendingService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/v1") @Tag(name = "Request Controller", description = "Money sending service operations")
public class MoneySendingServiceController
{
    private static final Logger logger = LoggerFactory.getLogger(MoneySendingServiceController.class);

    @Autowired
    MoneySendingService sendingService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferReqResponseDTO> createPendingTransfer(@RequestBody @Valid TransferRequestDTO req)
    {
        try
        {
        	TransferReqResponseDTO resp = sendingService.handlePendingTransferRequest(req);
            return ResponseEntity.ok(resp);
        }
        catch (Exception e)
        {
            logger.error("Error whilst handling " + req, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't create pending transfer "+req, e);
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawReqResponseDTO> createPendingWithdraw(@RequestBody @Valid WithdrawRequestDTO req)
    {
        try
        {
        	WithdrawReqResponseDTO resp = sendingService.handlePendingWithdrawRequest(req);
            return ResponseEntity.ok(resp);
        }
        catch (Exception e)
        {
            logger.error("Error whilst handling " + req, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't create pending withdraw "+req, e);
        }
    }

    @GetMapping("/withdraw")
    public ResponseEntity<WithdrawStatusResponseDTO> getWithdrawStatus(@Valid WithdrawStatusRequestDTO req)
    {
    	 try
         {
         	WithdrawStatusResponseDTO resp = sendingService.getWithdrawStatus(req);
             return ResponseEntity.ok(resp);
         }
         catch (Exception e)
         {
             logger.error("Error whilst handling " + req, e);
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find pending withdraw "+req, e);
         }
    }
    
    @GetMapping("/transfer")
    public ResponseEntity<TransferStatusResponseDTO> getTransferStatus(@Valid TransferStatusRequestDTO req)
    {
    	 try
         {
    		 TransferStatusResponseDTO resp = sendingService.getTransferStatus(req);
             return ResponseEntity.ok(resp);
         }
         catch (Exception e)
         {
             logger.error("Error whilst handling " + req, e);
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find pending transfer "+req, e);
         }
    }

  

    @ResponseStatus(HttpStatus.BAD_REQUEST) @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();
        errors.put("requestStatus", "error");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
