package io.webfolder.cdp.test;

import static java.nio.file.Paths.get;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import static org.junit.Assert.assertArrayEquals;

public class MoseMoveTest {

	@SuppressWarnings({ "unchecked" })
	@Test
	public void testMouseMove() throws MalformedURLException {
		String path = get("src/test/resources/mouse-move.html").toAbsolutePath().toUri().toString();

		Launcher launcher = new Launcher();

		try (SessionFactory factory = launcher.launch(); Session session = factory.create()) {
			session.enableConsoleLog();
			session.navigate(path);
			session.move(20, 20);
			session.move(21, 21);
			session.move(22, 22);
			session.move(23, 23);
			List<Double> positionsX = session.getVariable("positionsX", List.class);
			List<Double> positionsY = session.getVariable("positionsY", List.class);
			assertArrayEquals(new Double[] { 20.0D, 21.0D, 22.0D, 23.0D }, positionsX.toArray(new Double[] {}));
			assertArrayEquals(new Double[] { 20.0D, 21.0D, 22.0D, 23.0D }, positionsY.toArray(new Double[] {}));
		}
	}
}
