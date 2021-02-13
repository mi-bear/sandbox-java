# Effective Java#9
see: https://github.com/jbloch/effective-java-3e-source-code/tree/master/src/effectivejava

## 標準の関数型インタフェース
Template Method パターン
```java
class BaseClass {
    // Sort の機能を持っている
    // abstract compare()
}

class SubClass {
    // DoubleSort
    // compare()
}
```

- P203 `強く直交していません` の例:
    - List の機能がいくつかある
    - List.sublist(): List の範囲を指定して新しい List の View を返却する
    - 上記の範囲に対して、いろんなことができる。
    - 特定のリストをクリアしたいとき: List.sublist().clear()
    - ↑ 上記のような状態ではない = 強く直交していない (似て非なる)
    
## ストリームを注意して使う
- 終端操作を含めるまで、式は評価されない。
- 実質的 final (effectively final)
    - 書き換えコードがないことをコンパイラは検査する。(Java8 以降)
    - 昔は final を書かないとコンパイルが通らない。
