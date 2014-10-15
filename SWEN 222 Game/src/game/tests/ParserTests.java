package game.tests;

import static org.junit.Assert.*;
import game.loading.AreaParser;
import game.loading.ParserError;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests to see if Areas are being created properly.
 *
 * @author Chris Allen
 *
 */
public class ParserTests {
	// filepath to the test xml files.
	private static final String FILE_PATH = "src" + File.separatorChar + "game"
			+ File.separatorChar + "tests" + File.separatorChar
			+ "TestXMLFiles" + File.separatorChar;

	/**
	 * Test to see if ParseArea using a fileName works, shouldn't throw an
	 * error.
	 */
	@Test
	public void validAreasParserTest0() {
		try {
			AreaParser.parseAreas(FILE_PATH + "ValidAreasTest0.xml");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Shouldnt have thrown a IOError");
		} catch (ParserError e) {
			e.printStackTrace();
			fail("Shouldnt have thrown :" + e.getMessage());
		}
	}

	/**
	 * Test to see if AreaParser can handle missing lines, should throw an
	 * error.
	 */
	@Test
	public void invalidAreasParserTest0() {
		try {
			AreaParser.parseAreas(FILE_PATH + "notAnXmlFile.xml");
			// if it gets this far fail, because it should throw an error.
			fail("Should have thrown a IOError");
		} catch (IOException e) {
		} catch (ParserError e) {
			e.printStackTrace();
			fail("Shouldnt have thrown :" + e.getMessage());
		}
	}

	/**
	 * Test to see if AreaParser can handle missing lines, should throw an
	 * error.
	 */
	@Test
	public void invalidAreasParserTest1() {
		try {
			AreaParser.parseAreas(FILE_PATH + "InvalidAreasTest1.xml");
			// if it gets this far fail, because it should throw an error.
			fail("Should have thrown a ParserError for the <Areas> missing");
		} catch (ParserError e) {
			if (!e.getMessage().equals(
					"Parsing Areas: Expecting <Areas>, got <Area>")) {
				fail("Error message should be: Parsing Areas: Expecting <Areas>, got <Area>");
			}
		} catch (IOException e) {
			// shouldnt get here, but im bad at implementing parsers, so it
			// might
			fail("IOException, check file is valid");
			e.printStackTrace();
		}
	}

	/**
	 * Test to see if AreaParser can handle missing lines, should throw an
	 * error.
	 */
	@Test
	public void invalidAreasParserTest2() {
		try {
			AreaParser.parseAreas(FILE_PATH + "InvalidAreasTest2.xml");
			// if it gets this far fail, because it should throw an error.
			fail("Should have thrown a ParserError for the </Areas> missing");
		} catch (ParserError e) {
			if (!e.getMessage().equals(
					"Parsing Area: Expecting <Area>, got </NotAreas>")) {
				fail("got: " + e.getMessage());
			}
		} catch (IOException e) {
			// shouldnt get here, but im bad at implementing parsers, so it
			// might
			fail("IOException, check file is valid");
			e.printStackTrace();
		}
	}

	/**
	 * Test to see if AreaParser can handle invalid <> in parseInt, should throw
	 * a Parser error.
	 */
	@Test
	public void invalidAreasParserTest3() {
		try {
			AreaParser.parseAreas(FILE_PATH + "InvalidAreasTest3.xml");
			// if it gets this far fail, because it should throw an error.
			fail("Should have thrown a ParserError for the <ID> being something else.");
		} catch (ParserError e) {
			if (!e.getMessage().equals(
					"Parsing Int: Expecting <ID>, got <NotID>")) {
				fail("got: " + e.getMessage());
			}
		} catch (IOException e) {
			// shouldnt get here, but im bad at implementing parsers, so it
			// might
			fail("IOException, check file is valid");
			e.printStackTrace();
		}
	}

	/**
	 * Test to see if AreaParser can handle invalid <> in parseInt, should throw
	 * a Parser error.
	 */
	@Test
	public void invalidAreasParserTest4() {
		try {
			AreaParser.parseAreas(FILE_PATH + "InvalidAreasTest4.xml");
			// if it gets this far fail, because it should throw an error.
			fail("Should have thrown a ParserError for the </ID> being something else.");
		} catch (ParserError e) {
			if (!e.getMessage().equals(
					"Parsing Int: Expecting </ID>, got </NotID>")) {
				fail("got: " + e.getMessage());
			}
		} catch (IOException e) {
			// shouldnt get here, but im bad at implementing parsers, so it
			// might
			fail("IOException, check file is valid");
			e.printStackTrace();
		}
	}
}
