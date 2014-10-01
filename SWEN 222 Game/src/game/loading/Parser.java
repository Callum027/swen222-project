package game.loading;

import game.world.GameWorld;

import java.io.File;
import java.util.Scanner;
/**
 * This Class holds all the methods for loading a world from an xml file (will make comments look swanky later).
 * @author Tsun
 *
 */
public class Parser {
	//probably obsolete but will leave in for now in case i need it later
public GameWorld parseWorld(Scanner scan){
	try{
	scan.useDelimiter("\\s*(?=<)|(?<=>)\\s*");
	if(!scan.hasNext()) throw new ParserError("Empty xml file.");
	//shouldn't get here if there is no next.
	GameWorld world = new GameWorld();
	if(!scan.next().equals("World"))throw new ParserError("Invalid declaration, should be a World.");
	while(scan.hasNext()){
		String temp = scan.next();
		if(temp.equals("World")) break;
		world.addArea(parseArea(scan));
	}
	return world;
	}catch(ParserError pError){
	System.out.println("Parser Error: "+pError.getMessage());
}
	return null;
}

private File parseArea(Scanner scan) {
	//am doubling up on checks atm, will fix later if i remove parseWorld.
	try{
		if(!scan.next().equals("Area"))throw new ParserError("Invalid declaration, should be an Area.");
		if(!scan.hasNext()) throw new ParserError("No File declaration");
		if(!scan.next().equals("File"))throw new ParserError("Invalid declaration, should be an Area.");
		
	}catch(ParserError pError){
		System.out.println("Parser Error: "+pError.getMessage());
	}
	return null;
}
}