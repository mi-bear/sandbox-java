package com.example;

public class JoyOfHex {
    public static void main(String[] args) {
        // 16進数リテラルを利用すると良い
        System.out.println(
                Long.toHexString(0x100000000L + 0xcafebabe)
        );
    }
}
