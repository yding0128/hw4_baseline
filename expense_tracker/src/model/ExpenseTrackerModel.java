package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ExpenseTrackerModel class represents the model in the Expense Tracker
 * application, applying the Observer design pattern. This class acts as the 
 * Observable (subject) where observers (listeners) can register and receive 
 * notifications about changes in the model's state.
 */

public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;
  private List<ExpenseTrackerModelListener> listeners;

  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 
    
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
    listeners = new ArrayList<>();
  }

  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
  }

  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	  throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	  if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	      throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	  }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);
  }

  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      if (listener != null && !listeners.contains(listener)) {
        listeners.add(listener);
        return true; 
      }
      return false;
  }

  public int numberOfListeners() {
      // For testing, this is one of the methods.
      return listeners.size();
  }

  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.
      return listeners.contains(listener);
  }

  protected void stateChanged() {
      // For the Observable class, this is one of the methods.
      for (ExpenseTrackerModelListener listener : listeners) {
         listener.update(this); 
      }
  }
}
