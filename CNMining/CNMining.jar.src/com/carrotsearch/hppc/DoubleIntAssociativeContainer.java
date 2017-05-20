package com.carrotsearch.hppc;

import com.carrotsearch.hppc.cursors.DoubleIntCursor;
import com.carrotsearch.hppc.predicates.DoublePredicate;
import com.carrotsearch.hppc.procedures.DoubleIntProcedure;
import java.util.Iterator;

public abstract interface DoubleIntAssociativeContainer
  extends Iterable<DoubleIntCursor>
{
  public abstract Iterator<DoubleIntCursor> iterator();
  
  public abstract boolean containsKey(double paramDouble);
  
  public abstract int size();
  
  public abstract boolean isEmpty();
  
  public abstract int removeAll(DoubleContainer paramDoubleContainer);
  
  public abstract int removeAll(DoublePredicate paramDoublePredicate);
  
  public abstract <T extends DoubleIntProcedure> T forEach(T paramT);
  
  public abstract void clear();
  
  public abstract DoubleCollection keys();
  
  public abstract IntContainer values();
}


/* Location:              /home/menick/Scrivania/ProcessMining/CNMining/CNMining.jar!/com/carrotsearch/hppc/DoubleIntAssociativeContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */