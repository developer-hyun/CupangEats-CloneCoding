package com.example.demo.src.menu;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.menu.model.GetMenuRes;
import com.example.demo.src.menu.model.GetSideCategoryRes;
import com.example.demo.src.store.model.GetStoreDetailRes;
import com.example.demo.src.store.model.GetStoreRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/app/menus")
public class MenuController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MenuProvider menuProvider;
    @Autowired
    private final MenuService menuService;


    public MenuController(MenuProvider menuProvider, MenuService menuService){
        this.menuProvider = menuProvider;
        this.menuService = menuService;

    }


    @ResponseBody
    @GetMapping("/{menuIdx}/side-menu") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetSideCategoryRes>> getSideCategory(
                                              @PathVariable int menuIdx) {

        try {

               List< GetSideCategoryRes> getSideCategory = menuProvider.getSideCategory(menuIdx);
                return new BaseResponse<>(getSideCategory);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    @ResponseBody
    @GetMapping("/{menuIdx}") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<GetMenuRes> getMenu(
            @PathVariable int menuIdx) {

        try {

             GetMenuRes getMenu = menuProvider.getMenu(menuIdx);
            return new BaseResponse<>(getMenu);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }



}
