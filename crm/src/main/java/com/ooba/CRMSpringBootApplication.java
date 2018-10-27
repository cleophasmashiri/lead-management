package com.ooba;

import com.camunda.demo.environment.LicenseHelper;
import com.camunda.demo.environment.UserDataGenerator;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.authorization.Permission;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.camunda.demo.environment.DefaultFilter.FILTER_allTasksFilter;
import static com.camunda.demo.environment.DefaultFilter.FILTER_groupTasksFilter;
import static com.camunda.demo.environment.DefaultFilter.FILTER_myTasks;
import static com.camunda.demo.environment.UserDataGenerator.*;
import static com.camunda.demo.environment.UserDataGenerator.createGrantGroupAuthorization;
import static com.camunda.demo.environment.UserDataGenerator.createGrantUserAuthorization;

@SpringBootApplication
@EnableProcessApplication("leadProcess")
public class CRMSpringBootApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String PROCESS_DEFINITION_KEY = "leadProcess";

	public static void main(String... args) {
		SpringApplication.run(CRMSpringBootApplication.class, args);
	}

    @Autowired
    private ProcessEngine engine;

    @Override
    public void run(String... strings) throws Exception {

        LicenseHelper.setLicense(engine);
        UserDataGenerator.createDefaultUsers(engine);

        addUser(engine, "agent1", "agent1", "Agent1", "Smith");
        addGroup(engine, "clerk", "Clerk", "agent1");
        addFilterGroupAuthorization(engine, "clerk", FILTER_myTasks, FILTER_groupTasksFilter, FILTER_allTasksFilter);

        addUser(engine, "agent2", "agent2", "Agent2", "Maguire");
        addGroup(engine, "management", "Management", "agent2");
        addFilterUserAuthorization(engine, "agent2", FILTER_myTasks, FILTER_groupTasksFilter, FILTER_allTasksFilter);


        createGrantGroupAuthorization(engine, //
                new String[]{"clerk"}, //
                new Permission[]{Permissions.READ, Permissions.READ_HISTORY, Permissions.UPDATE_INSTANCE}, //
                Resources.PROCESS_DEFINITION, //
                new String[]{PROCESS_DEFINITION_KEY});

        // Admin Lisa
        createGrantUserAuthorization(engine, //
                new String[]{"agent2"}, //
                new Permission[]{Permissions.READ, Permissions.READ_HISTORY, Permissions.READ_INSTANCE, Permissions.UPDATE_INSTANCE}, //
                Resources.PROCESS_DEFINITION, //
                new String[]{PROCESS_DEFINITION_KEY});
        createGrantUserAuthorization(engine,
                new String[]{"agent2"},
                new Permission[]{Permissions.READ, Permissions.READ_HISTORY},
                Resources.DECISION_DEFINITION,
                new String[]{"riskAssessment"});
        createGrantUserAuthorization(engine, //
                new String[]{"agent2"}, //
                new Permission[]{Permissions.READ, Permissions.UPDATE}, //
                Resources.TASK, //
                new String[]{"*"});
        createGrantUserAuthorization(engine, //
                new String[]{"agent2"}, //
                new Permission[]{Permissions.ALL}, //
                Resources.DEPLOYMENT, //
                new String[]{"*"});
        createGrantUserAuthorization(engine, //
                new String[]{"agent2"}, //
                new Permission[]{Permissions.ACCESS}, //
                Resources.APPLICATION, //
                new String[]{"cockpit"});

    }
}