/*     */ package com.carrotsearch.hppc;
/*     */ 
/*     */ import com.carrotsearch.hppc.cursors.CharCursor;
/*     */ import com.carrotsearch.hppc.predicates.CharPredicate;
/*     */ import com.carrotsearch.hppc.procedures.CharProcedure;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharArrayDeque
/*     */   extends AbstractCharCollection
/*     */   implements CharDeque, Cloneable
/*     */ {
/*     */   public static final int DEFAULT_CAPACITY = 5;
/*     */   public char[] buffer;
/*     */   public int head;
/*     */   public int tail;
/*     */   protected final ArraySizingStrategy resizer;
/*     */   
/*     */   public CharArrayDeque()
/*     */   {
/*  59 */     this(5);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharArrayDeque(int initialCapacity)
/*     */   {
/*  69 */     this(initialCapacity, new BoundedProportionalArraySizingStrategy());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharArrayDeque(int initialCapacity, ArraySizingStrategy resizer)
/*     */   {
/*  77 */     assert (initialCapacity >= 0) : ("initialCapacity must be >= 0: " + initialCapacity);
/*  78 */     assert (resizer != null);
/*     */     
/*  80 */     this.resizer = resizer;
/*  81 */     initialCapacity = resizer.round(initialCapacity);
/*  82 */     this.buffer = new char[initialCapacity];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharArrayDeque(CharContainer container)
/*     */   {
/*  91 */     this(container.size());
/*  92 */     addLast(container);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addFirst(char e1)
/*     */   {
/* 101 */     int h = oneLeft(this.head, this.buffer.length);
/* 102 */     if (h == this.tail)
/*     */     {
/* 104 */       ensureBufferSpace(1);
/* 105 */       h = oneLeft(this.head, this.buffer.length);
/*     */     }
/* 107 */     this.buffer[(this.head = h)] = e1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addFirst(char... elements)
/*     */   {
/* 118 */     ensureBufferSpace(elements.length);
/*     */     
/*     */ 
/* 121 */     for (int i = 0; i < elements.length; i++) {
/* 122 */       addFirst(elements[i]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int addFirst(CharContainer container)
/*     */   {
/* 133 */     int size = container.size();
/* 134 */     ensureBufferSpace(size);
/*     */     
/* 136 */     for (CharCursor cursor : container)
/*     */     {
/* 138 */       addFirst(cursor.value);
/*     */     }
/*     */     
/* 141 */     return size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int addFirst(Iterable<? extends CharCursor> iterable)
/*     */   {
/* 151 */     int size = 0;
/* 152 */     for (CharCursor cursor : iterable)
/*     */     {
/* 154 */       addFirst(cursor.value);
/* 155 */       size++;
/*     */     }
/* 157 */     return size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addLast(char e1)
/*     */   {
/* 166 */     int t = oneRight(this.tail, this.buffer.length);
/* 167 */     if (this.head == t)
/*     */     {
/* 169 */       ensureBufferSpace(1);
/* 170 */       t = oneRight(this.tail, this.buffer.length);
/*     */     }
/* 172 */     this.buffer[this.tail] = e1;
/* 173 */     this.tail = t;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addLast(char... elements)
/*     */   {
/* 184 */     ensureBufferSpace(1);
/*     */     
/*     */ 
/* 187 */     for (int i = 0; i < elements.length; i++) {
/* 188 */       addLast(elements[i]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int addLast(CharContainer container)
/*     */   {
/* 199 */     int size = container.size();
/* 200 */     ensureBufferSpace(size);
/*     */     
/* 202 */     for (CharCursor cursor : container)
/*     */     {
/* 204 */       addLast(cursor.value);
/*     */     }
/*     */     
/* 207 */     return size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int addLast(Iterable<? extends CharCursor> iterable)
/*     */   {
/* 217 */     int size = 0;
/* 218 */     for (CharCursor cursor : iterable)
/*     */     {
/* 220 */       addLast(cursor.value);
/* 221 */       size++;
/*     */     }
/* 223 */     return size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char removeFirst()
/*     */   {
/* 232 */     assert (size() > 0) : "The deque is empty.";
/*     */     
/* 234 */     char result = this.buffer[this.head];
/* 235 */     this.buffer[this.head] = '\000';
/* 236 */     this.head = oneRight(this.head, this.buffer.length);
/* 237 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char removeLast()
/*     */   {
/* 246 */     assert (size() > 0) : "The deque is empty.";
/*     */     
/* 248 */     this.tail = oneLeft(this.tail, this.buffer.length);
/* 249 */     char result = this.buffer[this.tail];
/* 250 */     this.buffer[this.tail] = '\000';
/* 251 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getFirst()
/*     */   {
/* 260 */     assert (size() > 0) : "The deque is empty.";
/*     */     
/* 262 */     return this.buffer[this.head];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getLast()
/*     */   {
/* 271 */     assert (size() > 0) : "The deque is empty.";
/*     */     
/* 273 */     return this.buffer[oneLeft(this.tail, this.buffer.length)];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int removeFirstOccurrence(char e1)
/*     */   {
/* 282 */     int index = bufferIndexOf(e1);
/* 283 */     if (index >= 0) removeAtBufferIndex(index);
/* 284 */     return index;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int bufferIndexOf(char e1)
/*     */   {
/* 297 */     int last = this.tail;
/* 298 */     int bufLen = this.buffer.length;
/* 299 */     for (int i = this.head; i != last; i = oneRight(i, bufLen))
/*     */     {
/* 301 */       if (e1 == this.buffer[i]) {
/* 302 */         return i;
/*     */       }
/*     */     }
/* 305 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int removeLastOccurrence(char e1)
/*     */   {
/* 314 */     int index = lastBufferIndexOf(e1);
/* 315 */     if (index >= 0) removeAtBufferIndex(index);
/* 316 */     return index;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int lastBufferIndexOf(char e1)
/*     */   {
/* 329 */     int bufLen = this.buffer.length;
/* 330 */     int last = oneLeft(this.head, bufLen);
/* 331 */     for (int i = oneLeft(this.tail, bufLen); i != last; i = oneLeft(i, bufLen))
/*     */     {
/* 333 */       if (e1 == this.buffer[i]) {
/* 334 */         return i;
/*     */       }
/*     */     }
/* 337 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int removeAllOccurrences(char e1)
/*     */   {
/* 346 */     int removed = 0;
/* 347 */     int last = this.tail;
/* 348 */     int bufLen = this.buffer.length;
/*     */     int to;
/* 350 */     for (int from = to = this.head; from != last; from = oneRight(from, bufLen))
/*     */     {
/* 352 */       if (e1 == this.buffer[from])
/*     */       {
/* 354 */         this.buffer[from] = '\000';
/* 355 */         removed++;
/*     */       }
/*     */       else
/*     */       {
/* 359 */         if (to != from)
/*     */         {
/* 361 */           this.buffer[to] = this.buffer[from];
/* 362 */           this.buffer[from] = '\000';
/*     */         }
/*     */         
/* 365 */         to = oneRight(to, bufLen);
/*     */       }
/*     */     }
/* 368 */     this.tail = to;
/* 369 */     return removed;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAtBufferIndex(int index)
/*     */   {
/* 383 */     assert (this.head <= this.tail ? (index >= this.head) || (index < this.tail) : (index >= this.head) || (index < this.tail)) : ("Index out of range (head=" + this.head + ", tail=" + this.tail + ", index=" + index + ").");
/*     */     
/*     */ 
/*     */ 
/* 387 */     char[] b = this.buffer;
/* 388 */     int bufLen = b.length;
/* 389 */     int lastIndex = bufLen - 1;
/* 390 */     int head = this.head;
/* 391 */     int tail = this.tail;
/*     */     
/* 393 */     int leftChunk = Math.abs(index - head) % bufLen;
/* 394 */     int rightChunk = Math.abs(tail - index) % bufLen;
/*     */     
/* 396 */     if (leftChunk < rightChunk)
/*     */     {
/* 398 */       if (index >= head)
/*     */       {
/* 400 */         System.arraycopy(b, head, b, head + 1, leftChunk);
/*     */       }
/*     */       else
/*     */       {
/* 404 */         System.arraycopy(b, 0, b, 1, index);
/* 405 */         b[0] = b[lastIndex];
/* 406 */         System.arraycopy(b, head, b, head + 1, lastIndex - head);
/*     */       }
/* 408 */       b[head] = '\000';
/* 409 */       this.head = oneRight(head, bufLen);
/*     */     }
/*     */     else
/*     */     {
/* 413 */       if (index < tail)
/*     */       {
/* 415 */         System.arraycopy(b, index + 1, b, index, rightChunk);
/*     */       }
/*     */       else
/*     */       {
/* 419 */         System.arraycopy(b, index + 1, b, index, lastIndex - index);
/* 420 */         b[lastIndex] = b[0];
/* 421 */         System.arraycopy(b, 1, b, 0, tail);
/*     */       }
/* 423 */       b[tail] = '\000';
/* 424 */       this.tail = oneLeft(tail, bufLen);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 434 */     return size() == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 443 */     if (this.head <= this.tail) {
/* 444 */       return this.tail - this.head;
/*     */     }
/* 446 */     return this.tail - this.head + this.buffer.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 459 */     if (this.head < this.tail)
/*     */     {
/* 461 */       Arrays.fill(this.buffer, this.head, this.tail, '\000');
/*     */     }
/*     */     else
/*     */     {
/* 465 */       Arrays.fill(this.buffer, 0, this.tail, '\000');
/* 466 */       Arrays.fill(this.buffer, this.head, this.buffer.length, '\000');
/*     */     }
/* 468 */     this.head = (this.tail = 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void release()
/*     */   {
/* 476 */     this.head = (this.tail = 0);
/* 477 */     this.buffer = new char[this.resizer.round(5)];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void ensureBufferSpace(int expectedAdditions)
/*     */   {
/* 486 */     int bufferLen = this.buffer == null ? 0 : this.buffer.length;
/* 487 */     int elementsCount = size();
/*     */     
/* 489 */     if (elementsCount >= bufferLen - expectedAdditions - 1)
/*     */     {
/* 491 */       int newSize = this.resizer.grow(bufferLen, elementsCount, expectedAdditions + 1);
/* 492 */       assert (newSize >= elementsCount + expectedAdditions + 1) : ("Resizer failed to return sensible new size: " + newSize + " <= " + (elementsCount + expectedAdditions));
/*     */       
/*     */ 
/*     */ 
/* 496 */       char[] newBuffer = new char[newSize];
/* 497 */       if (bufferLen > 0)
/*     */       {
/* 499 */         toArray(newBuffer);
/* 500 */         this.tail = elementsCount;
/* 501 */         this.head = 0;
/*     */       }
/* 503 */       this.buffer = newBuffer;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char[] toArray()
/*     */   {
/* 514 */     int size = size();
/* 515 */     return toArray(new char[size]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char[] toArray(char[] target)
/*     */   {
/* 528 */     assert (target.length >= size()) : ("Target array must be >= " + size());
/*     */     
/* 530 */     if (this.head < this.tail)
/*     */     {
/*     */ 
/* 533 */       System.arraycopy(this.buffer, this.head, target, 0, size());
/*     */     }
/* 535 */     else if (this.head > this.tail)
/*     */     {
/*     */ 
/*     */ 
/* 539 */       int rightCount = this.buffer.length - this.head;
/* 540 */       System.arraycopy(this.buffer, this.head, target, 0, rightCount);
/* 541 */       System.arraycopy(this.buffer, 0, target, rightCount, this.tail);
/*     */     }
/*     */     
/* 544 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharArrayDeque clone()
/*     */   {
/*     */     try
/*     */     {
/* 557 */       CharArrayDeque cloned = (CharArrayDeque)super.clone();
/* 558 */       cloned.buffer = ((char[])this.buffer.clone());
/* 559 */       return cloned;
/*     */     }
/*     */     catch (CloneNotSupportedException e)
/*     */     {
/* 563 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static int oneLeft(int index, int modulus)
/*     */   {
/* 572 */     if (index >= 1) return index - 1;
/* 573 */     return modulus - 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static int oneRight(int index, int modulus)
/*     */   {
/* 581 */     if (index + 1 == modulus) return 0;
/* 582 */     return index + 1;
/*     */   }
/*     */   
/*     */ 
/*     */   private final class ValueIterator
/*     */     extends AbstractIterator<CharCursor>
/*     */   {
/*     */     private final CharCursor cursor;
/*     */     
/*     */     private int remaining;
/*     */     
/*     */     public ValueIterator()
/*     */     {
/* 595 */       this.cursor = new CharCursor();
/* 596 */       this.cursor.index = CharArrayDeque.oneLeft(CharArrayDeque.this.head, CharArrayDeque.this.buffer.length);
/* 597 */       this.remaining = CharArrayDeque.this.size();
/*     */     }
/*     */     
/*     */ 
/*     */     protected CharCursor fetch()
/*     */     {
/* 603 */       if (this.remaining == 0) {
/* 604 */         return (CharCursor)done();
/*     */       }
/* 606 */       this.remaining -= 1;
/* 607 */       this.cursor.value = CharArrayDeque.this.buffer[(this.cursor.index = CharArrayDeque.oneRight(this.cursor.index, CharArrayDeque.this.buffer.length))];
/* 608 */       return this.cursor;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private final class DescendingValueIterator
/*     */     extends AbstractIterator<CharCursor>
/*     */   {
/*     */     private final CharCursor cursor;
/*     */     
/*     */     private int remaining;
/*     */     
/*     */     public DescendingValueIterator()
/*     */     {
/* 622 */       this.cursor = new CharCursor();
/* 623 */       this.cursor.index = CharArrayDeque.this.tail;
/* 624 */       this.remaining = CharArrayDeque.this.size();
/*     */     }
/*     */     
/*     */ 
/*     */     protected CharCursor fetch()
/*     */     {
/* 630 */       if (this.remaining == 0) {
/* 631 */         return (CharCursor)done();
/*     */       }
/* 633 */       this.remaining -= 1;
/* 634 */       this.cursor.value = CharArrayDeque.this.buffer[(this.cursor.index = CharArrayDeque.oneLeft(this.cursor.index, CharArrayDeque.this.buffer.length))];
/* 635 */       return this.cursor;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<CharCursor> iterator()
/*     */   {
/* 656 */     return new ValueIterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<CharCursor> descendingIterator()
/*     */   {
/* 677 */     return new DescendingValueIterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends CharProcedure> T forEach(T procedure)
/*     */   {
/* 686 */     forEach(procedure, this.head, this.tail);
/* 687 */     return procedure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void forEach(CharProcedure procedure, int fromIndex, int toIndex)
/*     */   {
/* 697 */     char[] buffer = this.buffer;
/* 698 */     for (int i = fromIndex; i != toIndex; i = oneRight(i, buffer.length))
/*     */     {
/* 700 */       procedure.apply(buffer[i]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends CharPredicate> T forEach(T predicate)
/*     */   {
/* 710 */     int fromIndex = this.head;
/* 711 */     int toIndex = this.tail;
/*     */     
/* 713 */     char[] buffer = this.buffer;
/* 714 */     for (int i = fromIndex; i != toIndex; i = oneRight(i, buffer.length))
/*     */     {
/* 716 */       if (!predicate.apply(buffer[i])) {
/*     */         break;
/*     */       }
/*     */     }
/* 720 */     return predicate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends CharProcedure> T descendingForEach(T procedure)
/*     */   {
/* 729 */     descendingForEach(procedure, this.head, this.tail);
/* 730 */     return procedure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void descendingForEach(CharProcedure procedure, int fromIndex, int toIndex)
/*     */   {
/* 740 */     if (fromIndex == toIndex) {
/* 741 */       return;
/*     */     }
/* 743 */     char[] buffer = this.buffer;
/* 744 */     int i = toIndex;
/*     */     do
/*     */     {
/* 747 */       i = oneLeft(i, buffer.length);
/* 748 */       procedure.apply(buffer[i]);
/* 749 */     } while (i != fromIndex);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends CharPredicate> T descendingForEach(T predicate)
/*     */   {
/* 758 */     descendingForEach(predicate, this.head, this.tail);
/* 759 */     return predicate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void descendingForEach(CharPredicate predicate, int fromIndex, int toIndex)
/*     */   {
/* 770 */     if (fromIndex == toIndex) {
/* 771 */       return;
/*     */     }
/* 773 */     char[] buffer = this.buffer;
/* 774 */     int i = toIndex;
/*     */     do
/*     */     {
/* 777 */       i = oneLeft(i, buffer.length);
/* 778 */     } while ((predicate.apply(buffer[i])) && 
/*     */     
/* 780 */       (i != fromIndex));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int removeAll(CharPredicate predicate)
/*     */   {
/* 789 */     int removed = 0;
/* 790 */     int last = this.tail;
/* 791 */     int bufLen = this.buffer.length;
/*     */     int to;
/* 793 */     int from = to = this.head;
/*     */     try
/*     */     {
/* 796 */       for (from = to = this.head; from != last; from = oneRight(from, bufLen))
/*     */       {
/* 798 */         if (predicate.apply(this.buffer[from]))
/*     */         {
/* 800 */           this.buffer[from] = '\000';
/* 801 */           removed++;
/*     */         }
/*     */         else
/*     */         {
/* 805 */           if (to != from)
/*     */           {
/* 807 */             this.buffer[to] = this.buffer[from];
/* 808 */             this.buffer[from] = '\000';
/*     */           }
/*     */           
/* 811 */           to = oneRight(to, bufLen);
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 817 */       for (; from != last; from = oneRight(from, bufLen))
/*     */       {
/* 819 */         if (to != from)
/*     */         {
/* 821 */           this.buffer[to] = this.buffer[from];
/* 822 */           this.buffer[from] = '\000';
/*     */         }
/*     */         
/* 825 */         to = oneRight(to, bufLen);
/*     */       }
/* 827 */       this.tail = to;
/*     */     }
/*     */     
/* 830 */     return removed;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(char e)
/*     */   {
/* 839 */     int fromIndex = this.head;
/* 840 */     int toIndex = this.tail;
/*     */     
/* 842 */     char[] buffer = this.buffer;
/* 843 */     for (int i = fromIndex; i != toIndex; i = oneRight(i, buffer.length))
/*     */     {
/* 845 */       if (e == buffer[i]) {
/* 846 */         return true;
/*     */       }
/*     */     }
/* 849 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 858 */     int h = 1;
/* 859 */     int fromIndex = this.head;
/* 860 */     int toIndex = this.tail;
/*     */     
/* 862 */     char[] buffer = this.buffer;
/* 863 */     for (int i = fromIndex; i != toIndex; i = oneRight(i, buffer.length))
/*     */     {
/* 865 */       h = 31 * h + Internals.rehash(this.buffer[i]);
/*     */     }
/* 867 */     return h;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 877 */     if (obj != null)
/*     */     {
/* 879 */       if ((obj instanceof CharDeque))
/*     */       {
/* 881 */         CharDeque other = (CharDeque)obj;
/* 882 */         if (other.size() == size())
/*     */         {
/* 884 */           int fromIndex = this.head;
/* 885 */           char[] buffer = this.buffer;
/* 886 */           int i = fromIndex;
/* 887 */           for (CharCursor c : other)
/*     */           {
/* 889 */             if (c.value != buffer[i])
/* 890 */               return false;
/* 891 */             i = oneRight(i, buffer.length);
/*     */           }
/* 893 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 897 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CharArrayDeque newInstance()
/*     */   {
/* 907 */     return new CharArrayDeque();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CharArrayDeque newInstanceWithCapacity(int initialCapacity)
/*     */   {
/* 917 */     return new CharArrayDeque(initialCapacity);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CharArrayDeque from(char... elements)
/*     */   {
/* 926 */     CharArrayDeque coll = new CharArrayDeque(elements.length);
/* 927 */     coll.addLast(elements);
/* 928 */     return coll;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CharArrayDeque from(CharArrayDeque container)
/*     */   {
/* 937 */     return new CharArrayDeque(container);
/*     */   }
/*     */ }


/* Location:              /home/menick/Scrivania/ProcessMining/CNMining/CNMining.jar!/com/carrotsearch/hppc/CharArrayDeque.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */