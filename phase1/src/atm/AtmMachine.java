package atm;

import java.util.List;
import java.util.TreeMap;

/**
 * Performs tasks & holds data for an atm machine (e.g. handling cash).
 *
 * @author zhaojuna
 * @version 1.0
 */
public class AtmMachine {
    private static int prev_id = 0;
    private CashHandler cashHandler;
    private AtmTime time;
    private final String id;

    /**
     * Constructs an atm machine with a common time, an initial stock and a distributor for the cash handler
     *
     * @param time         the common time
     * @param initialStock the initial stock of the machine.
     *                     The key of the map is cash type and the value is amount of cash in that type.
     *                     This map defines the allowed cash types in this machine, so all cash types must be declared
     *                     as a key in this map even if they have an initial amount of 0.
     * @param distributor  The distributor instance to be used in the cash handler
     */
    AtmMachine(AtmTime time, TreeMap<Integer, Integer> initialStock,
               CashDistributor distributor) {
        this.time = time;
        id = String.format("ATM%04d", prev_id);
        prev_id++;
        cashHandler = new CashHandler(time, initialStock, Currency.CAD, 20, distributor);
    }

    /**
     * @return List of valid cash types allowed in this machine
     */
    public List<Integer> getValidCashTypes() {
        return cashHandler.getValidCashTypes();
    }

    /**
     * @return the unique id of this atm machine in format of "ATM####"
     */
    public String getId() {
        return id;
    }

    /**
     * Decrease the stock of this atm machine (i.e. cash being taken out).
     *
     * @param amount the total amount to be taken out
     * @return A TreeMap representing types of cash taken and their corresponding amount.
     * key is type and value is amount.
     * @throws CashShortageException when the amount requested can only be partially produced
     * @throws EmptyStockException   when there is no cash at all left in the machine
     */
    public TreeMap<Integer, Integer> reduceStock(int amount) throws CashShortageException, EmptyStockException {
        return cashHandler.takeAmountOfCash(amount);
    }

    /**
     * Increase the stock of this atm machine (i.e. adding cash into the machine).
     *
     * @param increment the amount of different types of cash put into the machine. key is type and value is amount.
     */
    public void increaseStock(TreeMap<Integer, Integer> increment) throws InvalidCashTypeException {
        cashHandler.storeCashStock(increment);
    }

    /**
     * @return the current time stamp of this atm machine
     */
    public String displayTime() {
        return time.getCurrentTimeStamp();
    }

    /**
     * @return String representation of this atm machine in the format of "ID id STOCK stock"
     * @see CashHandler#toString()
     */
    public String toString() {
        return String.format("ID %s STOCK %s", id, cashHandler);
    }
}
