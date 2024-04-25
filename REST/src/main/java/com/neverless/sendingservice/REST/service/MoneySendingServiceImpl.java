package com.neverless.sendingservice.REST.service;

import org.springframework.stereotype.Service;

import com.neverless.sendingservice.RESTDelegator;
import com.neverless.sendingservice.REST.dto.requests.TransferRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.TransferStatusRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.WithdrawRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.WithdrawStatusRequestDTO;
import com.neverless.sendingservice.REST.dto.response.TransferReqResponseDTO;
import com.neverless.sendingservice.REST.dto.response.TransferStatusResponseDTO;
import com.neverless.sendingservice.REST.dto.response.WithdrawReqResponseDTO;
import com.neverless.sendingservice.REST.dto.response.WithdrawStatusResponseDTO;
import com.neverless.sendingservice.transfer.TransferService.TransferState;
import com.neverless.sendingservice.withdraw.WithdrawalService.WithdrawalState;

import jakarta.validation.Valid;

@Service
public class MoneySendingServiceImpl implements MoneySendingService{

	RESTDelegator delegator=new RESTDelegator();
	
	@Override
	public TransferReqResponseDTO handlePendingTransferRequest(@Valid TransferRequestDTO req) {
		return null;
	}

	@Override
	public WithdrawReqResponseDTO handlePendingWithdrawRequest(@Valid WithdrawRequestDTO req) {
		return null;
	}

	@Override
	public WithdrawStatusResponseDTO getWithdrawStatus(@Valid WithdrawStatusRequestDTO req) {
		WithdrawalState status = delegator.getWithdrawStatus(req.requestId);
		WithdrawStatusResponseDTO resp=new WithdrawStatusResponseDTO();
		resp.status=status.toString();
		return resp;
	}

	@Override
	public TransferStatusResponseDTO getTransferStatus(@Valid TransferStatusRequestDTO req) {
		TransferState status = delegator.getTransferStatus(req.requestId);
		TransferStatusResponseDTO resp=new TransferStatusResponseDTO();
		resp.status=status.toString();
		return resp;
	}

}
