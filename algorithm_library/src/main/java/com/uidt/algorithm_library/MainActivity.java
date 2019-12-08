package com.uidt.algorithm_library;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.uidt.common_base.base.BaseActivity;

/**
 * 算法
 */
public class MainActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected View getTopView() {
        return toolbar;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Algorithm算法");
        setSupportActionBar(toolbar);
    }

    public static void main(String[] args) {
        //单向链表测试
        OneLinkedTest();
    }

    //适合少读多写 数组适合于多读少写
    private static void OneLinkedTest() {
        OnewayLinkedList onewayLinkedList = new OnewayLinkedList();
        onewayLinkedList.insertNode(1, 0);
        onewayLinkedList.insertNode(2, 1);
        onewayLinkedList.insertNode(3, 2);
        onewayLinkedList.insertNode(4, 3);
        onewayLinkedList.insertNode(5, 4);

        onewayLinkedList.output();

        onewayLinkedList.updateNode(90, 3);
        onewayLinkedList.updateNode(80, 4);
        onewayLinkedList.updateNode(10, 1);
        onewayLinkedList.output();

        Node node = onewayLinkedList.get(3);
        System.out.println("第4个数据=" + node.data);

        onewayLinkedList.removeNode(0);
        onewayLinkedList.output();

        onewayLinkedList.removeNode(2);
        onewayLinkedList.output();
    }
}
