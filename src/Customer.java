public class Customer {
    private final int id;
    private final int itemsCount;

    public Customer(int id, int itemsCount) {
        this.id = id;
        this.itemsCount = itemsCount;
    }

    public int getId() { return id; }
    public int getItemsCount() { return itemsCount; }
}