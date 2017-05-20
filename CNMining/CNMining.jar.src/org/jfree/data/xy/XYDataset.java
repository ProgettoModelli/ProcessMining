package org.jfree.data.xy;

import org.jfree.data.DomainOrder;
import org.jfree.data.general.SeriesDataset;

public abstract interface XYDataset
  extends SeriesDataset
{
  public abstract DomainOrder getDomainOrder();
  
  public abstract int getItemCount(int paramInt);
  
  public abstract Number getX(int paramInt1, int paramInt2);
  
  public abstract double getXValue(int paramInt1, int paramInt2);
  
  public abstract Number getY(int paramInt1, int paramInt2);
  
  public abstract double getYValue(int paramInt1, int paramInt2);
}


/* Location:              /home/menick/Scrivania/ProcessMining/CNMining/CNMining.jar!/org/jfree/data/xy/XYDataset.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */