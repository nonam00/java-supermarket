import java.util.Queue;

public class Cashier extends Thread {
    private final int id;
    private final Queue<Customer> queue;
    private final Warehouse warehouse;
    private final int minServiceTime;
    private final int maxServiceTime;

    public Cashier(int id, Queue<Customer> queue, Warehouse warehouse, int minServiceTime, int maxServiceTime) {
        this.id = id;
        this.queue = queue;
        this.warehouse = warehouse;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
    }

    @Override
    public void run() {
        while (true) {
            Customer customer;
            synchronized (queue) {
                customer = queue.poll();
            }
            if (customer != null) {
                int serviceTime = minServiceTime + (int)(Math.random() * (maxServiceTime - minServiceTime + 1));
                boolean served = warehouse.takeItems(customer.getItemsCount());
                if (served) {
                    System.out.println("Касса #" + id + " обслуживает покупателя " + customer.getId() +
                            " (" + customer.getItemsCount() + " товаров) за " + serviceTime + " мс");
                } else {
                    System.out.println("Касса #" + id + ": недостаточно товара для покупателя " + customer.getId());
                }
                try {
                    Thread.sleep(serviceTime);
                } catch (InterruptedException e) { break; }
            } else {
                try {
                    Thread.sleep(100); // ожидание новых покупателей
                } catch (InterruptedException e) { break; }
            }
        }
    }
}