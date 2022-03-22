package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCrditRes {
    private final List<GetCardRes> getCardRes;
    private final List<GetBankTransferRes> getBankTransferRes;
    private final String cashReceiptNumber;
}
