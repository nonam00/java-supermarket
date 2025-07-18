public class Supplier extends Thread {
    private final Warehouse warehouse;
    private final int minSupply;
    private final int maxSupply;
    private final int delay;

    public Supplier(Warehouse warehouse, int minSupply, int maxSupply, int delay) {
        this.warehouse = warehouse;
        this.minSupply = minSupply;
        this.maxSupply = maxSupply;
        this.delay = delay;
    }

    @Override
    public void run() {
        while (true) {
            int supply = minSupply + (int)(Math.random() * (maxSupply - minSupply + 1));
            warehouse.addItems(supply);
            System.out.println("Поставщик добавил " + supply + " товаров. На складе: " + warehouse.getCurrentStock());
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) { break; }
        }
    }
}