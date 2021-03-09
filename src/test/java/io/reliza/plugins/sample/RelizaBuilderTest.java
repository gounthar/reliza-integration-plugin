package io.reliza.plugins.sample;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import hudson.model.FreeStyleProject;
import hudson.model.TaskListener;
import hudson.EnvVars;
import io.reliza.plugins.reliza.RelizaBuildWrapper;
import io.reliza.plugins.reliza.RelizaBuilder;
import jenkins.tasks.SimpleBuildWrapper.Context;

/**
 * Class for testing reliza build wrapper and reliza builder.
 */
public class RelizaBuilderTest {
    
    @Rule
    public JenkinsRule jenkins = new JenkinsRule();
    
    /**
     * Simple test to make sure wrapper can perform api calls to reliza hub.
     */
    @Test
    public void testRelizaWrapper() throws Exception {	
        FreeStyleProject project = jenkins.createFreeStyleProject();
        RelizaBuildWrapper relizaBuildWrapper = new RelizaBuildWrapper();
        relizaBuildWrapper.setUri("https://test.relizahub.com");
        relizaBuildWrapper.setProjectId(null);
        Context context = relizaBuildWrapper.createContext();
        TaskListener listener = jenkins.createTaskListener();
        project.getBuildWrappersList().add(relizaBuildWrapper);
        
        EnvVars envVars = new hudson.slaves.EnvironmentVariablesNodeProperty().getEnvVars();
        envVars.put("RELIZA_API_USR", "PROJECT__314c0886-0f41-4f92-a4ef-59c2cbb0e3b0");
        envVars.put("RELIZA_API_PSW", "58f7bee5fb50919b055708b0936179ca39083f8e9ec3626d68ccee78e75613f8c0e804dbe41376afe005355eeffc22d4");
        envVars.put("GIT_BRANCH", "master");
        relizaBuildWrapper.setUp(context, null, null, null, listener, envVars);
        
        envVars.put("VERSION", context.getEnv().get("VERSION"));
        envVars.put("URI", "https://test.relizahub.com");
        RelizaBuilder relizaBuilder = new RelizaBuilder();
        relizaBuilder.perform(null, null, envVars, null, listener);
    }
}
