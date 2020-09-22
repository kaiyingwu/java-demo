package com.example.demo.service.bookkeeping;

import com.example.demo.bean.BKBaseResponse;


public interface UserSerVice {

    BKBaseResponse getUser(String account, String password);

}
