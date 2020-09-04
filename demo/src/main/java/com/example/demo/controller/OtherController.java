package com.example.demo.controller;



import com.example.demo.bean.BaseResponse;
import com.example.demo.enums.UserQualificationVerifierStatus;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/other")
public class OtherController {

    @Resource(name = "consumerQueueThreadPool")
    private ExecutorService executorService;


    @RequestMapping("/serviceStart")
    public String  ServiceStart(){
//        Future<BaseResult<PlatformLoginData>> future =
//                executorService.submit(() -> authHandler.checkCookie(platformAccountVo.getConfig()));
        for(int i = 0; i < 3; i++) {
            /**
             * 简略写法 execute
             */
            executorService.execute(() -> {
                System.out.println("当前正在执行线程:" + Thread.currentThread().getName());
            });


            /**
             * ExecutorService  submit方法
             */
//            Future future = executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("当前正在执行线程:" + Thread.currentThread().getName());
//                }
//            });
//
//            try {
//                future.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                System.out.println("线程执行错误:" + e.getMessage());
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//                System.out.println("线程执行错误:" + e.getMessage());
//            }

            /**
             * 完全写法 execute
             */
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("当前正在执行线程:" + Thread.currentThread().getName());
//                }
//            });

        }
        return "xxx";
    }



    @PostMapping("/{userId}/qualification/status")
    public BaseResponse setQualificationStatus(
            @PathVariable("userId") Long userId,
            @RequestParam("status") UserQualificationVerifierStatus status,
            @RequestParam("msg") String msg) {
        System.out.println(userId+":"+status.code+":"+msg);
        return BaseResponse.createBasicOK();
    }

}
