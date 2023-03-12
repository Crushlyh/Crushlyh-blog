package com.lyh.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Author:crushlyh
 * Date:2023/3/5 21:25
 */
@Component
public class TestJob {
   //@Scheduled(cron="0/5 * * * * ?")
    private void testjob(){
        System.out.println("schedule job process");
    }
}
