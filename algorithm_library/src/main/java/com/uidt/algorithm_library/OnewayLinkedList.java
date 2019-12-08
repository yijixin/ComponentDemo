package com.uidt.algorithm_library;

/**
 * @author yijixin on 2019-12-08
 * 单向链表
 */
public class OnewayLinkedList {

    //头结点
    private Node headNode;
    //尾结点
    private Node lastNode;
    //总链表长度
    private int size;

    /**
     * 添加
     *
     * @param data  数据
     * @param index 位置
     */
    public void insertNode(int data, int index) {
        exceptionMethod(index);

        Node insertNode = new Node(data);

        if (index == 0) {
            //插入到头部位置
            insertNode.next = headNode;
            headNode = insertNode;
        } else if (index == size - 1) {
            //尾部位置
            lastNode.next = insertNode;
            lastNode = insertNode;
        } else {
            //中间
            Node prevNode = get(index - 1);
            Node nextNode = prevNode.next;
            prevNode.next = insertNode;
            insertNode.next = nextNode;
        }
        size++;
    }

    /**
     * 删除结点
     * @param index 位置
     */
    public Node removeNode(int index) {

        exceptionMethod(index);

        Node removeNode = null;
        if (index == 0) {
            //头结点
            removeNode = headNode;
            headNode = headNode.next;
        } else if (index == size - 1) {
            //尾结点
            //上一个结点
            Node prevNode = get(index - 1);
            removeNode = prevNode.next;
            prevNode.next = null;
            lastNode = prevNode;
        } else {
            //中间结点
            //删除的结点的上一个结点
            Node prveNode = get(index - 1);
            //删除的结点的下一个结点
            Node nextNode = prveNode.next.next;
            //删除的结点
            removeNode = prveNode.next;
            prveNode.next = nextNode;
        }
        size--;
        return removeNode;
    }

    /**
     * 更新结点
     */
    public void updateNode(int data,int index){
        exceptionMethod(index);

        Node updateNode = get(index);
        updateNode.data = data;
    }

    private void exceptionMethod(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("超出链表结点范围");
        }
    }

    /**
     * 查找结点
     *
     * @param index 位置
     * @return 查找到的结点返回
     */
    public Node get(int index) {
        exceptionMethod(index);

        Node temp = headNode;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    /**
     * 输出链表
     */
    public void output() {
        Node temp = headNode;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }
}
