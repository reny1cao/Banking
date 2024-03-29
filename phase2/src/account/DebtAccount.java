package account;

import atm.User;
import transaction.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Defines behaviours of a debt account. A debt account is always {@link Indebtable}.
 * Debt accounts' positive balance means amount in debt, and negative balance means amount overpayed.
 *
 * @author zhaojuna
 * @version 1.0
 */
public abstract class DebtAccount extends Account implements Depositable, Indebtable {
    /**
     * The debt limit of this account. It should be positive. Default value is {@link #DEFAULT_DEBT_LIMIT}.
     */
    int debtLimit;

    /**
     * Create a default debt account with a default debt limit of {@link #DEFAULT_DEBT_LIMIT}.
     *
     * @param time  time of creation
     * @param owner owner user
     * @see Account#Account(Date, List)
     */
    DebtAccount(Date time, List<User> owner) {
        super(time, owner);
        debtLimit = DEFAULT_DEBT_LIMIT;
    }

    /**
     * Create a debt account with a given debt limit.
     *
     * @param time      time of creation
     * @param owner     owner user
     * @param debtLimit the debt limit of this account
     */
    DebtAccount(Date time, List<User> owner, int debtLimit) {
        super(time, owner);
        this.debtLimit = debtLimit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNetBalance() {
        return -balance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDebtLimit() {
        return debtLimit;
    }

    /**
     * {@inheritDoc}
     * Absolute value will be taken if given debt limit is negative.
     */
    @Override
    public void setDebtLimit(int debtLimit) {
        this.debtLimit = Math.abs(debtLimit);
    }

    /**
     * {@inheritDoc}
     * The transaction will be registered into account's transaction list.
     */
    @Override
    public void deposit(double amount, Transaction register) {
        balance -= amount;

        registerTransaction(register);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelDeposit(double amount) {
        balance += amount;
    }
}
