package io.reliza.plugins.sample;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import hudson.EnvVars;
import hudson.model.FreeStyleProject;
import hudson.model.TaskListener;
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
		envVars.put("RELIZA_API_USR", "PROJECT__24625ac0-0256-4638-99d2-f245cc56ff8f");
		envVars.put("RELIZA_API_PSW", "917dca5223f2203346c25a5ecbb1e98a2bec136ac2bbcdf1bcfeec797c0879c0ef09cc45d5337e0cd6e0b69db12af5e3");
		envVars.put("GIT_BRANCH", "master");
		relizaBuildWrapper.setUp(context, null, null, null, listener, envVars);
		
		envVars.put("VERSION", context.getEnv().get("VERSION"));
		envVars.put("URI", "https://test.relizahub.com");
		RelizaBuilder relizaBuilder = new RelizaBuilder();
		relizaBuilder.perform(null, null, envVars, null, listener);
	}
}
