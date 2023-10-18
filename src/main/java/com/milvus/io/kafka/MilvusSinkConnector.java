package com.milvus.io.kafka;

import com.milvus.io.kafka.utils.VersionUtil;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MilvusSinkConnector extends SinkConnector{
    private Map<String, String> configProperties;

    @Override
    public void start(Map<String, String> props) {
        try {
            configProperties = props;
            // validation
            new MilvusSinkConnectorConfig(props);
        }catch (ConfigException e){
            throw new ConfigException("Couldn't start ZillizCloudSinkConnector due to configuration error", e);
        }
    }

    @Override
    public Class<? extends Task> taskClass() {
        return MilvusSinkTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        final List<Map<String, String>> taskConfigs = new ArrayList<>(maxTasks);
        for (int i = 0; i < maxTasks; i++) {
            taskConfigs.add(configProperties);
        }
        return taskConfigs;
    }

    @Override
    public void stop() {

    }

    @Override
    public ConfigDef config() {
        return MilvusSinkConnectorConfig.conf();
    }

    @Override
    public String version() {
        return VersionUtil.getVersion();
    }
}
