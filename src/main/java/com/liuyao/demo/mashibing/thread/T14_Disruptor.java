package com.liuyao.demo.mashibing.thread;

/**
 * 高并发九
 *
 * disruptor
 * 分裂 瓦解
 * 单机最快的MQ
 * 性能极高 无锁cas 单机支持高并发
 * 内存中高效队列
 *
 * 观察者模式
 *
 * 核心 环形buffer(RingBufffer) 直接覆盖 降低GC
 *  有ConcurrentLinkedQueue,使用首尾指针,
 *  没有ConcurrentArrayQueue,因为array长度不变
 *  而disruptor只维护sequence(序列),用array(数组)实现,效率快
 *
 * 进度 01:18:00
 *
 */
public class T14_Disruptor {



}
