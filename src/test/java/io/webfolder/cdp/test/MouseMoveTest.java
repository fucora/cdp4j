/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017, 2018 WebFolder OÜ (support@webfolder.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.webfolder.cdp.test;

import static java.nio.file.Paths.get;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class MouseMoveTest {

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testMouseMove() throws Exception {
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
			assertEquals(4, positionsX.size());
            assertEquals(4, positionsY.size());
		}
	}
}
