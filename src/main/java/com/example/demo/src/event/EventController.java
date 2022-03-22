package com.example.demo.src.event;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.event.model.*;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/app/events")
public class EventController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final EventProvider eventProvider;
    @Autowired
    private final EventService eventService;
    @Autowired
    private final JwtService jwtService;


    public EventController(EventProvider eventProvider, EventService eventService, JwtService jwtService){
        this.eventProvider = eventProvider;
        this.eventService = eventService;
        this.jwtService=jwtService;
    }


    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetEventsRes>> getEvents()
                                               {

        try {
                List<GetEventsRes> getEvents = eventProvider.getEvents();
                return new BaseResponse<>(getEvents);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }



}
