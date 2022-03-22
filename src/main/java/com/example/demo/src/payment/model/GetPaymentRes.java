package com.example.demo.src.payment.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPaymentRes {
   private int eventIdx;
  private String bannerImgUrl;
  private String endTime;
}
