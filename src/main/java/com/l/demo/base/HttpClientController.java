package com.l.demo.base;

import com.l.demo.model.Bbs;
import com.l.demo.model.Inter;
import com.l.demo.service.BbsService;
import com.l.demo.util.HttpClientUtils;
import lombok.Cleanup;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by luocheng on 2019/4/9.
 */
@RestController
public class HttpClientController {

    public static final SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Autowired
    private BbsService bbsService;

    @GetMapping ("/inter/test")
    public void test() {
        String url = "http://localhost:8080/inter/getParam";
        String localFile = "D:\\test1.png";
        Map<String, String> param = new HashMap<String, String>();
        param.put("name","luocheng");
        param.put("pass","123456");
        HttpClientUtils.upload(url,localFile,param);

        try {
            //  @Cleanup 放在需要关闭的资源前面  可以自动关闭资源
            @Cleanup InputStream in = new FileInputStream("D:\\test1.png");
            @Cleanup OutputStream out = new FileOutputStream("D:\\test1.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @PostMapping("/inter/getParam")
    public void getParam(@NonNull  Inter inter) {
        int i = 1;
        System.out.println(inter.toString());
    }

    @GetMapping ("/inter/testThred2")
    public void testThred2() {
        String curDate = sdf.format(new Date());
        List<Bbs> bbsList = new ArrayList<Bbs>();
        for(int i =0;i<1000;i++){
            Bbs bs = new Bbs("name:" + i, "content:" + i,curDate);
            bbsList.add(bs);
        }

        ExecutorService pool = Executors.newSingleThreadExecutor();
        FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try{
                    for (Bbs bbs : bbsList) {
                        String curThrNm = Thread.currentThread().getName() +  "线程创建的Content";
                        bbs.setBbsCont(bbs.getBbsCont() + "   ---  " + curThrNm);
                        bbsService.inserBbs(bbs);
                    }

                    return "执行成功";

                }catch(Exception e){
                    e.printStackTrace();
                }

                return "执行失败";
            }
        });

        pool.execute(future);

        try {
            String result = future.get();
            System.out.println(result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @GetMapping ("/inter/testThred")
    public void testThred() {
        String curDate = sdf.format(new Date());

        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<String>> resultList = new ArrayList<>();

        List<Bbs> bbsList = new ArrayList<Bbs>();
        for(int i =0;i<1000;i++){
            Bbs bs = new Bbs("name:" + i, "content:" + i,curDate);
            bbsList.add(bs);
        }


        for (Bbs bbs : bbsList) {
            Future<String> future = pool.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    try{
                        String curThrNm = Thread.currentThread().getName() +  "线程创建的Content";
                        bbs.setBbsCont(bbs.getBbsCont() + "   ---  " + curThrNm);
                        return String.valueOf(bbs.getBbsName() + "创建结果为： " + (bbsService.inserBbs(bbs) == 0?"失败":"成功"));
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    return "0";
                }
            });
            resultList.add(future);
        }

        pool.shutdown();
        for (Future<String> future : resultList) {
            try {
                String result = future.get();
                System.out.println(result);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
}
