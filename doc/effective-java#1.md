# Effective Java#1
see: https://github.com/jbloch/effective-java-3e-source-code/tree/master/src/effectivejava

## Java の言語仕様
1. class の member には constructor が含まれない。
member は継承できるものである。constructor は継承できないものである。

2. class ファイルの定義上、戻り値が異なる同じシグネチャは可能。
.java (ソースコード, 言語仕様上) では、実装ができない。

## 1. static ファクトリメソッド
コンストラクタの代わりに static ファクトリメソッドを検討する。

ファクトリは作るためのメソッド。

```
class Test {
    public static void main(String[] a) {
        // constructor
        Boolean b = new Boolen(TRUE);
        
        // Class の中の static なメソッドを用意して利用する
        Boolean b = Boolean.valueOf(TRUE);
    }
}
```

https://www.infoq.com/jp/articles/java-14-feature-spotlight/

Java SE 14 (2020年3月) では、プレビュー機能として Record (jep359) が導入されます。
Record の目的は、"プレーンデータ"の集合を、より少ないセレモニーでモデル化できるようにすることです。
単純な x-y 座標の抽象化であれば、次のように宣言することができます。 
```
record Point (int x, int y) { }
```
これによって変更不可能 (immutable) なコンポーネントである x、y とその適切なアクセサ、コンストラクタ、equals、hashCode、toString の実装を持った Point という final クラスが宣言されます。


文字列を比較するときは、== ではなく equals で比較するべき。
すべての class のスーパークラス = Object class で定義されている:
```
public boolean equals(Object obj) {
    return (this == obj);
}
```
### EnumSet
EnumSet は abstructor なので New はできない。

### サービスプロバイダフレームワーク
1.6 から標準に入っている。

```
// Service interface
public interface Service {
    ... // Service-specific methods go here

    // Service access API
    static Service newInstance(String name) {
        return Registry.newInstance(name);
    }
}

// Service provider interface
public interface Provider {
    Service newService();
    
    // Provider registration API
    static void registerProvider(String name, Provider p) {
        Registry.registerProvider(name, p);
    }
}


class Registry {
    private Registry() {}
    
    .
    .
    .
}
```

最初に static ファクトリメソッドを検討せずに、コンストラクタの提供を無意識に行うのは避けるべき。

## 2. Builder
多くのコンストラクタパラメータに直面したときにはビルダーを検討する。

### JavaBeans
JavaBeans ってなに？
もともと JavaBeans はコンポーネントをあらわすもの。
コンポーネントにはプロパティがある。コンポーネントはイベントを発火できる。
とあるコンポーネントがイベント発火させたものを別のコンポーネントが受け取る、ということができる。
setter, getter のことだけを指しているわけではない。

### クラス階層に関するビルダーパターン
`self()` を使っている。`this` (インスタンスを表現している) じゃない。

Pizza: スーパークラス
T型: Builder class のサブタイプ

※ メソッド定義の際に、アクセス修飾子の右に `<T>` という型パラメーターを指定すると、そのメソッドはジェネリックメソッドとなる。

TODO: Builder の名前が同じなので変えて実装すると関係性がわかって良い。
利用する側や、コードの綺麗さを鑑みると Builder のままのほうが良い。
- NyPizza.NyBuilder 
- Calzone.CalBuilder

コンストラクタや static メソッドが多くのパラメータを持つクラス設計をする場合は、Builder パターンが良い選択である。

