package com.neverless.sendingservice.REST.service;

import org.springframework.stereotype.Service;

import com.neverless.sendingservice.REST.dto.requests.TransferRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.TransferStatusRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.WithdrawRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.WithdrawStatusRequestDTO;
import com.neverless.sendingservice.REST.dto.response.TransferReqResponseDTO;
import com.neverless.sendingservice.REST.dto.response.TransferStatusResponseDTO;
import com.neverless.sendingservice.REST.dto.response.WithdrawReqResponseDTO;
import com.neverless.sendingservice.REST.dto.response.WithdrawStatusResponseDTO;

import jakarta.validation.Valid;

@Service
public class MoneySendingServiceImpl implements MoneySendingService{

	@Override
	public TransferReqResponseDTO handlePendingTransferRequest(@Valid TransferRequestDTO req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WithdrawReqResponseDTO handlePendingWithdrawRequest(@Valid WithdrawRequestDTO req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WithdrawStatusResponseDTO getWithdrawStatus(@Valid WithdrawStatusRequestDTO req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferStatusResponseDTO getTransferStatus(@Valid TransferStatusRequestDTO req) {
		// TODO Auto-generated method stub
		return null;
	}

}
