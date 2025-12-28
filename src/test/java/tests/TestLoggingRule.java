package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class TestLoggingRule extends TestWatcher {

	private static final Logger log = LogManager.getLogger(TestLoggingRule.class);

	@Override
	protected void starting(Description description) {
		log.info("=== START {}.{} ===", description.getClassName(), description.getMethodName());
	}

	@Override
	protected void succeeded(Description description) {
		log.info("=== PASS  {}.{} ===", description.getClassName(), description.getMethodName());
	}

	@Override
	protected void failed(Throwable e, Description description) {
		log.error("=== FAIL  {}.{} ===", description.getClassName(), description.getMethodName(), e);
	}

	@Override
	protected void finished(Description description) {
		log.info("=== END   {}.{} ===", description.getClassName(), description.getMethodName());
	}
}
