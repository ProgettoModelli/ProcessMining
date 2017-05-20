package org.jfree.data.xy;

public abstract interface IntervalXYZDataset
  extends XYZDataset
{
  public abstract Number getStartXValue(int paramInt1, int paramInt2);
  
  public abstract Number getEndXValue(int paramInt1, int paramInt2);
  
  public abstract Number getStartYValue(int paramInt1, int paramInt2);
  
  public abstract Number getEndYValue(int paramInt1, int paramInt2);
  
  public abstract Number getStartZValue(int paramInt1, int paramInt2);
  
  public abstract Number getEndZValue(int paramInt1, int paramInt2);
}


/* Location:              /home/menick/Scrivania/ProcessMining/CNMining/CNMining.jar!/org/jfree/data/xy/IntervalXYZDataset.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */