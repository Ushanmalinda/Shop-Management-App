import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

class Storage{
    private ArrayList<Product> products = new ArrayList<>(
            Arrays.asList(
                new Product(0, "Product 01", 500.0, 8),
                new Product(1, "Product 02", 684.0, 5),
                new Product(2, "Product 03", 244.0, 7),
                new Product(3, "Product 04", 660.0, 4),
                new Product(4, "Product 05", 214.0, 3)
            )
    );

    void showItems(){
        for(Product product : products){
            System.out.println("ID: " + product.getId() + " - Name: " + product.getName() + " - Cost: " + product.getPrice() + " - In stock: " + product.getStock());
        }
    }

    void addItem(String id, String name, double price, int nstock){
        Product newProduct =new Product(products.size(), name, price, nstock);
        products.add(newProduct);
    }

    void removeItem(Scanner scnObj){
        System.out.print("ID: ");
        int index = scnObj.nextInt();
        if(index >= 0 && index < products.size()){
            products.remove(index);
        }else{
            System.out.println("Invalid ID");
        }
    }

    void setPrice(Scanner scnObj){
        System.out.print("ID: ");
        int index = scnObj.nextInt();
        if(index >= 0 && index < products.size()){
            System.out.print("New Price: ");
            products.get(index).setPrice(scnObj.nextDouble());
        }else{
            System.out.println("Invalid ID");
        }
    }

    void setStock(Scanner scnObj){
        System.out.print("ID: ");
        int index = scnObj.nextInt();
        if(index >= 0 && index < products.size()){
            System.out.print("Stock: ");
            products.get(index).setStock(scnObj.nextInt());
        }else{
            System.out.println("Invalid ID");
        }
    }

    Product getProduct(int index){
        if(index >= 0 && index < products.size()){
            return products.get(index);
        }
        return null;
    }
	
	public int getSize(){
		return products.size();
	}
	
	void decreaseStock(int productIndex, int quantity){
        if(productIndex >= 0 && productIndex < products.size()){
            Product product = products.get(productIndex);
            int currentStock = product.getStock();
            if(currentStock >= quantity){
                product.setStock(currentStock - quantity);
            }
        }
    }
}

class Product{
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getStock(){
        return stock;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setStock(int stock){
        this.stock = stock;
    }
}

class Cashier{
	public ArrayList<Product> items=new ArrayList<Product>();
	private Storage storObj;
	private Scanner scnObj;
	private int pSize;
	public int[][] itemQTYs;//{[id][qty]}
	
	public Cashier(Storage storObj,Scanner scnObj){
		this.storObj = storObj;
        this.scnObj = scnObj;
        this.pSize = storObj.getSize();
        this.itemQTYs = new int[pSize][2];
	}
	
	public void order(){
		int count = 0;
		storObj.showItems();
		System.out.println("Entering a negative number will cancel the operation...");
		while(true){
			System.out.print("Item id: ");
			int id = scnObj.nextInt();
			if(id < 0){
				break;
			}else if(id < storObj.getSize()){
				System.out.print("QTY: ");
				int qty = scnObj.nextInt();
				if(qty <= storObj.getProduct(id).getStock()){
					boolean found = false;
					// Check if the item is already in the cart
					for(int i = 0; i < count; i++){
						if (itemQTYs[i][0] == id){
							itemQTYs[i][1] += qty;
							found = true;
							break;
						}
					}
					if(!found){
						items.add(storObj.getProduct(id));
						itemQTYs[count][0] = id; // Update the item ID
						itemQTYs[count][1] = qty; // Set the quantity
						count++;
					}
					storObj.decreaseStock(id, qty);
				}else{
					System.out.println("Out Of Stock!! \nIn Stock: " + storObj.getProduct(id).getStock());
				}
			}else{
				System.out.println("Invalid ID");
			}
		}
	}
}

class Bill extends Cashier{
    public Bill(Storage storObj,Scanner scnObj){
        super(storObj, scnObj);
    }
	
    public double calculateTotalCost(){
        double totalCost = 0;
        for (int i = 0; i < items.size(); i++) {
            totalCost += items.get(i).getPrice();
        }
        return totalCost;
    }

    public void printBill(){
        System.out.println("------- Bill -------");
        System.out.printf("%-20s %-10s %-10s %-10s%n", "Item", "Price", "Quantity", "Total Cost");
        for (int i = 0; i < items.size(); i++) {
            Product product = items.get(i);
            int quantity = itemQTYs[i][1];
            double totalCost = product.getPrice() * quantity;
            System.out.printf("%-20s $%-10.2f %-10d $%-10.2f%n", product.getName(), product.getPrice(), quantity, totalCost);
        }
        double totalCost = calculateTotalCost();
        System.out.println("Total Cost: $" + totalCost);
    }
}

public class ShopManagementApp{
    public static void main(String[] args){
        Storage storage = new Storage();
        Scanner scanner = new Scanner(System.in);
		Bill bill = new Bill(storage, scanner);
        int choice;
        do{
            System.out.println("-------- Menu --------");
            System.out.println("1. Show Items");
            System.out.println("2. Add Item");
            System.out.println("3. Remove Item");
			System.out.println("4. Change Price");
			System.out.println("5. Change Stock");
            System.out.println("6. Place Order");
            System.out.println("7. Generate Bill");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch(choice){
                case 1:
                    storage.showItems();
                    break;
                case 2:
                    System.out.print("Enter ID: ");
                    String id = scanner.next();
                    System.out.print("Enter Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Stock: ");
                    int stock = scanner.nextInt();
                    storage.addItem(id, name, price, stock);
                    break;
                case 3:
                    storage.removeItem(scanner);
                    break;
                case 6:
                    bill.order();
                    break;
                case 7:
                    bill.printBill();
					bill = new Bill(storage, scanner);
                    break;
                case 5:
					storage.setStock(scanner);
					break;
				case 4:
					storage.setPrice(scanner);
					break;
				case 8:
					System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }while(choice != 8);
    }
}


