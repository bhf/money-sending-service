package com.neverless.sendingservice.REST.service;

import com.neverless.sendingservice.REST.dto.requests.TransferRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.TransferStatusRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.WithdrawRequestDTO;
import com.neverless.sendingservice.REST.dto.requests.WithdrawStatusRequestDTO;
import com.neverless.sendingservice.REST.dto.response.TransferReqResponseDTO;
import com.neverless.sendingservice.REST.dto.response.TransferStatusResponseDTO;
import com.neverless.sendingservice.REST.dto.response.WithdrawReqResponseDTO;
import com.neverless.sendingservice.REST.dto.response.WithdrawStatusResponseDTO;

import jakarta.validation.Valid;

public interface MoneySendingService {

	public TransferReqResponseDTO handlePendingTransferRequest(@Valid TransferRequestDTO req);

	public WithdrawReqResponseDTO handlePendingWithdrawRequest(@Valid WithdrawRequestDTO req);

	public WithdrawStatusResponseDTO getWithdrawStatus(@Valid WithdrawStatusRequestDTO req);

	public TransferStatusResponseDTO getTransferStatus(@Valid TransferStatusRequestDTO req);

}
