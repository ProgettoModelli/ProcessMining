/*    */ package com.carrotsearch.hppc.cursors;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LongIntCursor
/*    */ {
/*    */   public int index;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long key;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int value;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 30 */     return "[cursor, index: " + this.index + ", key: " + this.key + ", value: " + this.value + "]";
/*    */   }
/*    */ }


/* Location:              /home/menick/Scrivania/ProcessMining/CNMining/CNMining.jar!/com/carrotsearch/hppc/cursors/LongIntCursor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */