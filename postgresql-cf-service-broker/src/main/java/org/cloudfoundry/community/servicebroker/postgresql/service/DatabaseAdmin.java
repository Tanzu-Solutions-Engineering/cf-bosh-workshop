/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cloudfoundry.community.servicebroker.postgresql.service;

import org.springframework.cloud.servicebroker.model.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseAdmin {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseAdmin.class);

    public void createDatabaseForInstance(String instanceId, String serviceId, String planId, String organizationGuid, String spaceGuid) throws SQLException {
        Utils.checkValidUUID(instanceId);
        PostgreSQLDatabase.executeUpdate("CREATE DATABASE \"" + instanceId + "\" ENCODING 'SQL_ASCII'");
        PostgreSQLDatabase.executeUpdate("REVOKE all on database \"" + instanceId + "\" from public");

//        Map<Integer, String> parameterMap = new HashMap<Integer, String>();
//        parameterMap.put(1, instanceId);
//        parameterMap.put(2, serviceId);
//        parameterMap.put(3, planId);
//        parameterMap.put(4, organizationGuid);
//        parameterMap.put(5, spaceGuid);
//
//        PostgreSQLDatabase.executePreparedUpdate("INSERT INTO service (serviceinstanceid, servicedefinitionid, planid, organizationguid, spaceguid) VALUES (?, ?, ?, ?, ?)", parameterMap);
    }

    public void deleteDatabase(String instanceId) throws SQLException {
        Utils.checkValidUUID(instanceId);

        Map<Integer, String> parameterMap = new HashMap<Integer, String>();
        parameterMap.put(1, instanceId);

        Map<String, Object> result = PostgreSQLDatabase.executeSelect("SELECT current_user");
        String currentUser = null;

        if(result != null) {
            currentUser = result.get("current_user").toString();
        }

        if(currentUser == null) {
            logger.error("Current user for instance '" + instanceId + "' could not be found");
        }

        PostgreSQLDatabase.executePreparedSelect("SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = ? AND pid <> pg_backend_pid()", new Object[] {instanceId});
        PostgreSQLDatabase.executeUpdate("ALTER DATABASE \"" + instanceId + "\" OWNER TO \"" + currentUser + "\"");
        PostgreSQLDatabase.executeUpdate("DROP DATABASE IF EXISTS \"" + instanceId + "\"");
        //PostgreSQLDatabase.executePreparedUpdate("DELETE FROM service WHERE serviceinstanceid=?", parameterMap);
    }

    // TODO needs to be implemented
    public List<ServiceInstance> getAllServiceInstances() {
        return Collections.emptyList();
    }
}
