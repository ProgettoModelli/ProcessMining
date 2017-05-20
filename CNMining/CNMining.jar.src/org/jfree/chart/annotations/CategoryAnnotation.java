package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;

public abstract interface CategoryAnnotation
{
  public abstract void draw(Graphics2D paramGraphics2D, CategoryPlot paramCategoryPlot, Rectangle2D paramRectangle2D, CategoryAxis paramCategoryAxis, ValueAxis paramValueAxis);
}


/* Location:              /home/menick/Scrivania/ProcessMining/CNMining/CNMining.jar!/org/jfree/chart/annotations/CategoryAnnotation.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */