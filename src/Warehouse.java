public class Warehouse {
    private final int capacity;
    private int currentStock;

    public Warehouse(int capacity) {
        this.capacity = capacity;
        this.currentStock = capacity / 2; // стартовое заполнение
    }

    public synchronized boolean takeItems(int count) {
        if (currentStock >= count) {
            currentStock -= count;
            return true;
        }
        return false;
    }

    public synchronized void addItems(int count) {
        currentStock = Math.min(capacity, currentStock + count);
    }

    public synchronized int getCurrentStock() {
        return currentStock;
    }
}