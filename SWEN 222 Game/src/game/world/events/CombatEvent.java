package game.world.events;

import java.io.IOException;
import java.io.InputStream;

import game.exceptions.GameException;
import game.net.NetIO;
import game.world.GameEvent;
import game.world.Position;
import game.world.GameEvent.Type;
import game.world.characters.Attackable;
import game.world.characters.Enemy;
import game.world.characters.Player;
import game.world.items.Item;

public class CombatEvent extends GameEvent{

	private final Attackable attacker;
	private final Attackable target;

	public CombatEvent(Attackable attacker, Attackable target){
		if (attacker == null){
			throw new IllegalArgumentException("attacker is null");
		}
		if (target == null){
			throw new IllegalArgumentException("target is null");
		}

		this.attacker = attacker;
		this.target = target;

	}

	public Attackable getAttacker(){
		return attacker;
	}

	public Attackable getTarget(){
		return target;
	}

	@Override
	public Type getType() {
		return GameEvent.Type.COMBAT;
	}

	/**
	 * Read a CombatEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return CombatEvent
	 * @throws IOException
	 */
	public static CombatEvent read(InputStream is) throws IOException, GameException {
		Attackable attacker = Attackable.read(is);
		Attackable target = Attackable.read(is);
		return new CombatEvent(attacker, target);
	}


}
