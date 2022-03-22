package com.example.demo.src.payment;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.payment.model.GetPaymentRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/app/payments")
public class PaymentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PaymentProvider paymentProvider;
    @Autowired
    private final PaymentService paymentService;
    @Autowired
    private final JwtService jwtService;


    public PaymentController(PaymentProvider paymentProvider, PaymentService paymentService, JwtService jwtService){
        this.paymentProvider = paymentProvider;
        this.paymentService = paymentService;
        this.jwtService=jwtService;
    }


    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPaymentRes>> getEvents()
                                               {

        try {
                List<GetPaymentRes> getEvents = paymentProvider.getEvents();
                return new BaseResponse<>(getEvents);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }



}
