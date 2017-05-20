package bsh.org.objectweb.asm;

final class Item
{
  short index;
  int type;
  int intVal;
  long longVal;
  float floatVal;
  double doubleVal;
  String strVal1;
  String strVal2;
  String strVal3;
  int hashCode;
  Item next;
  
  Item() {}
  
  Item(short paramShort, Item paramItem)
  {
    this.index = paramShort;
    this.type = paramItem.type;
    this.intVal = paramItem.intVal;
    this.longVal = paramItem.longVal;
    this.floatVal = paramItem.floatVal;
    this.doubleVal = paramItem.doubleVal;
    this.strVal1 = paramItem.strVal1;
    this.strVal2 = paramItem.strVal2;
    this.strVal3 = paramItem.strVal3;
    this.hashCode = paramItem.hashCode;
  }
  
  void set(int paramInt)
  {
    this.type = 3;
    this.intVal = paramInt;
    this.hashCode = (this.type + paramInt);
  }
  
  void set(long paramLong)
  {
    this.type = 5;
    this.longVal = paramLong;
    this.hashCode = (this.type + (int)paramLong);
  }
  
  void set(float paramFloat)
  {
    this.type = 4;
    this.floatVal = paramFloat;
    this.hashCode = (this.type + (int)paramFloat);
  }
  
  void set(double paramDouble)
  {
    this.type = 6;
    this.doubleVal = paramDouble;
    this.hashCode = (this.type + (int)paramDouble);
  }
  
  void set(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    this.type = paramInt;
    this.strVal1 = paramString1;
    this.strVal2 = paramString2;
    this.strVal3 = paramString3;
    switch (paramInt)
    {
    case 1: 
    case 7: 
    case 8: 
      this.hashCode = (paramInt + paramString1.hashCode());
      return;
    case 12: 
      this.hashCode = (paramInt + paramString1.hashCode() * paramString2.hashCode());
      return;
    }
    this.hashCode = (paramInt + paramString1.hashCode() * paramString2.hashCode() * paramString3.hashCode());
  }
  
  boolean isEqualTo(Item paramItem)
  {
    if (paramItem.type == this.type)
    {
      switch (this.type)
      {
      case 3: 
        return paramItem.intVal == this.intVal;
      case 5: 
        return paramItem.longVal == this.longVal;
      case 4: 
        return paramItem.floatVal == this.floatVal;
      case 6: 
        return paramItem.doubleVal == this.doubleVal;
      case 1: 
      case 7: 
      case 8: 
        return paramItem.strVal1.equals(this.strVal1);
      case 12: 
        return (paramItem.strVal1.equals(this.strVal1)) && (paramItem.strVal2.equals(this.strVal2));
      }
      return (paramItem.strVal1.equals(this.strVal1)) && (paramItem.strVal2.equals(this.strVal2)) && (paramItem.strVal3.equals(this.strVal3));
    }
    return false;
  }
}


/* Location:              /home/menick/Scrivania/ProcessMining/CNMining/CNMining.jar!/bsh/org/objectweb/asm/Item.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */