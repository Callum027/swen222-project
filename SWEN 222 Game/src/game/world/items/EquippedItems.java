package game.world.items;

public class EquippedItems {

	private Equipment head;
	private Equipment body;
	private Equipment boots;

	private Equipment mainHand;
	private Equipment offHand;
	
	public void equipHead(Equipment head){
		this.head = head;
	}
	
	public void equipBody(Equipment body){
		this.body = body;
	}
	
	public void equipBoots(Equipment boots){
		this.boots = boots;
	}

	public void equipMainHand(Equipment mainHand){
		this.mainHand = mainHand;
	}
	
	public void equipOffHand(Equipment offHand){
		this.offHand = offHand;
	}
	
	public Equipment getHead(){
		return head;
	}
	
	public Equipment getBody(){
		return body;
	}
	
	public Equipment getBoots(){
		return boots;
	}
	
	public Equipment getMainHand(){
		return mainHand;
	}
	
	public Equipment getoffHand(){
		return offHand;
	}
}
