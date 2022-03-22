package com.example.demo.src.event.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetEventsRes {
   private int eventIdx;
  private String bannerImgUrl;
  private String endTime;
}
