/**
 * This file is part of mycollab-scheduler.
 *
 * mycollab-scheduler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-scheduler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-scheduler.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.schedule.spring;

import com.esofthead.mycollab.schedule.AutowiringSpringBeanJobFactory;
import com.esofthead.mycollab.schedule.QuartzScheduleProperties;
import com.esofthead.mycollab.schedule.email.user.impl.SendUserInvitationEmailJob;
import com.esofthead.mycollab.schedule.email.user.impl.UserSignUpEmailNotificationJob;
import com.esofthead.mycollab.schedule.jobs.CrmSendingRelayEmailNotificationJob;
import com.esofthead.mycollab.schedule.jobs.ProjectSendingRelayEmailNotificationJob;
import com.esofthead.mycollab.schedule.jobs.SendingErrorReportEmailJob;
import com.esofthead.mycollab.schedule.jobs.SendingRelayEmailJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author MyCollab Ltd.
 * @since 4.6.0
 */
@Configuration
public class DefaultScheduleConfiguration {
    @Bean
    public JobDetailFactoryBean sendRelayEmailJob() {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(SendingRelayEmailJob.class);
        return bean;
    }

    @Bean
    public JobDetailFactoryBean sendInviteUserEmailJob() {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(SendUserInvitationEmailJob.class);
        return bean;
    }

    @Bean
    public JobDetailFactoryBean projectSendRelayNotificationEmailJob() {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(ProjectSendingRelayEmailNotificationJob.class);
        return bean;
    }

    @Bean
    public JobDetailFactoryBean crmSendRelayNotificationEmailJob() {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(CrmSendingRelayEmailNotificationJob.class);
        return bean;
    }

    @Bean
    public JobDetailFactoryBean userSignUpNotificationEmailJob() {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(UserSignUpEmailNotificationJob.class);
        return bean;
    }

    @Bean
    public JobDetailFactoryBean sendErrorReportEmailJob() {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(SendingErrorReportEmailJob.class);
        return bean;
    }

    @Bean
    public CronTriggerFactoryBean sendingRelayEmailTrigger() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(sendRelayEmailJob().getObject());
        bean.setCronExpression("0 * * * * ?");
        return bean;
    }

    @Bean
    public CronTriggerFactoryBean projectSendRelayNotificationEmailTrigger() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(projectSendRelayNotificationEmailJob().getObject());
        bean.setCronExpression("0 * * * * ?");
        return bean;
    }

    @Bean
    public CronTriggerFactoryBean crmSendRelayNotificationEmailTrigger() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(crmSendRelayNotificationEmailJob().getObject());
        bean.setCronExpression("0 * * * * ?");
        return bean;
    }

    @Bean
    public CronTriggerFactoryBean userSignUpNotificationEmailTrigger() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(userSignUpNotificationEmailJob().getObject());
        bean.setCronExpression("0 * * * * ?");
        return bean;
    }

    @Bean
    public CronTriggerFactoryBean sendErrorReportEmailTrigger() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(sendErrorReportEmailJob().getObject());
        bean.setCronExpression("0 * * * * ?");
        return bean;
    }

    @Bean
    public CronTriggerFactoryBean sendInviteUserEmailTrigger() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(sendInviteUserEmailJob().getObject());
        bean.setCronExpression("0 * * * * ?");
        return bean;
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Bean public SchedulerFactoryBean quartzScheduler() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();

//        if (DeploymentMode.site == SiteConfiguration.getDeploymentMode()) {
//            bean.setDataSource(new DataSourceConfiguration().dataSource());
//        }

        bean.setQuartzProperties(new QuartzScheduleProperties());
        bean.setOverwriteExistingJobs(true);
        AutowiringSpringBeanJobFactory factory = new AutowiringSpringBeanJobFactory();
        factory.setApplicationContext(applicationContext);
        bean.setJobFactory(factory);
        bean.setApplicationContextSchedulerContextKey("applicationContextSchedulerContextKey");

        bean.setTriggers(sendingRelayEmailTrigger().getObject(), projectSendRelayNotificationEmailTrigger().getObject
                (), crmSendRelayNotificationEmailTrigger().getObject(), sendErrorReportEmailTrigger().getObject(),
                sendInviteUserEmailTrigger().getObject(), userSignUpNotificationEmailTrigger().getObject());
        return bean;
    }
}
