package dininghall.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableScheduling
public class ScheduledConfiguration implements SchedulingConfigurer {
    private TaskScheduler taskScheduler;
    private ScheduledFuture<?> jobMembershipReminder;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(2);// Set the pool of threads
        threadPoolTaskScheduler.setThreadNamePrefix("scheduler-mail-thread");
        threadPoolTaskScheduler.initialize();

        jobMembershipReminder(threadPoolTaskScheduler);// Chech user membership

        this.taskScheduler = threadPoolTaskScheduler;

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }

    // Check user membership date to send reminder email
    private void jobMembershipReminder(TaskScheduler scheduler) {
        // TODO Send email to user
        jobMembershipReminder = scheduler.schedule(new Runnable() {
            @Override
            public void run() {



            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //String cronExp = "0 0/3 * * * ?";// Can be pulled from a db .
                String cronExp = "0 */30 * ? * *";// Can be pulled from a db .

                return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
            }
        });
    }


}
