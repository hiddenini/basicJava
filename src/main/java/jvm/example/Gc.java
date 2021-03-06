package jvm.example;

import java.util.Random;

/**
 * -Xms8m -Xmx8m -XX:+PrintGCDetails
 * GC算法:
 * 1--标记清除算法（ Mark-Sweep）
 * 最基础的垃圾回收算法，分为两个阶段，标注和清除。标记阶段标记出所有需要回收的对象，清除阶段回收被标记的对象所占用的空间
 * 优点:不需要额外的空间
 * 缺点:该算法最大的问题是内存碎片化严重，后续可能发生大对象不能找到可利用空间的问题
 * <p>
 * 2--复制算法
 * 一般将新生代划分为一块较大的 Eden 空间和两个较小的 Survivor 空间(From Space, To Space)，
 * 每次使用Eden 空间和其中的一块 Survivor 空间，当进行回收时，将该两块空间中还存活的对象复制到另一块 Survivor 空间中。
 * 每一次GC后Eden和To 是空的 如果幸存15次(默认)进入养老区
 * 好处:这种算法虽然实现简单，内存效率高，不易产生碎片
 * 坏处:但是最大的问题是可用内存被压缩到了原本的一半。且存活对象增多的话， Copying算法的效率会大大降低。
 * <p>
 * 3--标记清除算法
 * 最基础的垃圾回收算法，分为两个阶段，标注和清除。标记阶段标记出所有需要回收的对象，清除阶段回收被标记的对象所占用的空间
 * 该算法最大的问题是内存碎片化严重，后续可能发生大对象不能找到可利用空间的问题
 * <p>
 * 4--标记整理算法(Mark-Compact)
 * 标记后不是清理对象，而是将存活对象移向内存的一端。然后清除端边界外的对象
 * 可以标记清除几次，再进行整理(压缩) 这样比较有效率
 * <p>
 * 对比:
 * 内存效率:复制算法>标记清除算法>标记压缩算法(时间复杂度)
 * 内存整齐度:复制算法=标记清除算法>标记压缩算法
 * 内存利用率:标记压缩算法=标记清除算法>复制算法
 * <p>
 * 没有最好的算法,只有最合适的算法 GC:分代收集算法
 * <p>
 * 年轻代:
 * 存活率低 复制算法
 * 老年代:
 * 区域大 存活率
 * 标记清除(内存碎片不多时)+标记压缩混合(碎片多的情况下)实现
 *
 *
 * <p>
 * 目前大部分 JVM 的 GC 对于新生代都采取 Copying 算法，因为新生代中每次垃圾回收都要回收大部分对象、
 * <p>
 * 即要复制的操作比较少，但通常并不是按照 1： 1 来划分新生代。
 * <p>
 * 一般将新生代划分为一块较大的 Eden 空间和两个较小的 Survivor 空间(From Space, To Space)，
 * <p>
 * 每次使用Eden 空间和其中的一块 Survivor 空间，当进行回收时，将该两块空间中还存活的对象复制到另一块 Survivor 空间中。
 * <p>
 * 每一次GC后Eden和To 是空的 如果幸存15次(默认)进入养老区
 * 好处:这种算法虽然实现简单，内存效率高，不易产生碎片
 * 坏处:但是最大的问题是可用内存被压缩到了原本的一半。且存活对象增多的话， Copying算法的效率会大大降低。
 * <p>
 * 而老年代因为每次只回收少量对象，因而采用 Mark-Compact 算法。
 * 1--JAVA 虚拟机提到过的处于方法区的永生代(Permanet Generation)， 它用来存储 class 类，常量，方法描述等。对永生代的回收主要包括废弃常量和无用的类。
 * 2--对象的内存分配主要在新生代的 Eden Space 和 Survivor Space 的 From Space(Survivor 目前存放对象的那一块)，少数情况会直接分配到老生代。
 * 3--当新生代的 Eden Space 和 From Space 空间不足时就会发生一次 GC，进行 GC 后， EdenSpace 和 From Space 区的存活对象会被挪到 To Space，然后将 Eden Space 和 FromSpace 进行清理。
 * 4--如果 To Space 无法足够存储某个对象，则将这个对象存储到老生代。
 * 5--在进行 GC 后，使用的便是 Eden Space 和 To Space 了，如此反复循环。
 * 6--当对象在 Survivor 区躲过一次 GC 后，其年龄就会+1。 默认情况下年龄到达 15 的对象会被移到老生代中。
 */
public class Gc {
    public static void main(String[] args) {
        /**
         * 模拟java.lang.OutOfMemoryError: Java heap space
         */
        String str = "hello gc";
        while (true) {
            str += str + new Random().nextInt(8888888) + new Random().nextInt(8888888);
        }
    }
}
