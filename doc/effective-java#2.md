# Effective Java#2
see: https://github.com/jbloch/effective-java-3e-source-code/tree/master/src/effectivejava

## 柴田さん
- 1996年から Java をはじめている。
- JDK を家に持って帰るのが大変。フロッピーディスク10枚にもなった。
- 当時は雑誌についている CD-ROM が大変貴重だった。JDK が入っていることがあるので。

## 項目3 private のコンストラクタか enum 型でシングルトン特性を強制する
```
public void leaveTheBuilding() {...}
```

プレスリーはアンコールを行わないアーティスト。  
プレスリーは去った、というメソッド。なんだ！この裏話！  

シリアライズする = 復元したら別の Elvis になってしまう？  

Java はシリアライズしてディスクの保存・ネットワークに送るということができる。  
異なる Java VM 上で、デシリアライズすれば復元できる。


### transient (トランジェント):
シリアライズのときにフィールドの値が保存されなくなる。  
インスタンスフィールドに transient と書いていないと、攻撃にあう。  

復元してしまった Elvis を破棄したい。  
デシリアライズのときに復元してしまった Elvis を破棄したい。  
readResolve メソッドがあれば、復元時に呼ばれる。(デシリアライズ完了後)  
復元したオブジェクトに対して、readResolve が呼びだされる。  

そうすると、そのまま誰にも使われずに GC される。

```
private Object readResolve {
    return INSTANCE;
}
```

"static おじさん"

```java
class Util {
    public static void foor() {}
    public static void bar() {}
}
```

final をつけるのは、サブクラスを作らせないという意図である。

## 項目6 不必要なオブジェクトの生成を避ける
ひどい例:

```
class Lock {
    private final myLock = "lock";
    void foo() {
        synchronized (mylock) {
        }
    }
    void bar() {
        synchronized (mylock) {
        }
    }
}

class AnotherClass {
    private final myLock = "lock";
    void foo() {
        synchronized (mylock) {
        }
    }
    void bar() {
        synchronized (mylock) {
        }
    }
}
```

防御的コピー: セキュリティ  
自分の Class のオブジェクトは書き直しできないようにしたい。  
自分が持っているリストをそのまま返却することがよいのか。否。

## GC
C++ の場合は:
```
stener = ...;

y-AddListener(listener);
...
y->RemoveListenere(listener);
delete(listener);
```

## 項目8 ファイナライザとクリーナーを避ける
推奨はしていないけれども、一部のライブラリでは利用している。 
finalizer は定義しているだけで遅くなる。  
static を宣言すると何が起きるか。していないときとの違い。  

Java VM はどういった条件が揃ったら終了するのか。
- 明示的に呼ばない場合。Hello world は終了する。
- ユーザースレッドが全部いなくなって、デーモンスレッドだけになったら、VM は終わる。

## 項目9 try-finally よりも try-with-resources
Java Puzzlers にも間違いがあるらしい。  
後処理を書くための手段としての finally を使ってはならない、というわけではない。  
Close 処理などは利用しない。

## Java Puzzlers の話
ニール・ガフター: マイクロソフトにいる。Java5 のコンパイラを一人で書いた。

### パズル

```
public class Elementary {
    public static void main(String[] args) {
        System.out.println(12345 + 5432l):
    }
}
```

最後の1にみえるものが、`Long` の `l` だった。

```
public class JoyOfHex {
    public static void main(String[] args) {
        System.out.println(
            Long.toHexString(0x100000000L + 0xcafebabe);
        )
    }
}
```

0x100000000L: Long 型  
0xcafebabe: int 型 (4バイト)

```
public class Text {
    public static void main() {
        System.out.print("Hell");
        System.out.println("o world");
    }
}
```

Hello world が出力されると思った。

正解: コンパイルエラー

よく見たら、コードじゃなくて、その上に表示されているコメントが原因であった。(Unicode の問題)

```
\u
```

ユニコードエスケープシーケンスは、ソースコードが読み込まれた時点で全部変換される。  
その後に、Java の文法に合っているかのチェックが走る。
