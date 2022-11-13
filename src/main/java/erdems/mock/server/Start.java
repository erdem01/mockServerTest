package erdems.mock.server;

public class Start {

	public static void main(String[] args) throws InterruptedException {
		new Thread(() -> {
			MockServerWrapper wrapper = new MockServerWrapper();
			wrapper.startServer();
			wrapper.createExpectation("{username: 'foo', password: 'bar'}", "{ message: 'incorrect username and password combination' }");
			wrapper.createExpectation("{username: 'foo2', password: 'bar'}", "{ message: 'incorrect username and password combination2' }");
			wrapper.reset();
			wrapper.createExpectation("{username: 'foo', password: 'bar'}", "{ message: 'incorrect username and password combination' }");
			wrapper.stop();
		}).start();
		Thread.currentThread().join();
	}

}
