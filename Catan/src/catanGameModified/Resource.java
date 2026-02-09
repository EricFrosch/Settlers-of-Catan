package catanGameModified;

public class Resource {
	
	private String name;
	private int amount;
	
	public Resource(String n, int a) {
		name = n;
		amount = a;
	}
	
	public String getName() { return name; }
	
	public int getAmount() { return amount; }
	
	public void addAmount(int a) { amount += a; }
	
	public void subtractAmount(int s) { amount -= s; }

}
