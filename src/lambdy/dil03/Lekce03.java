package lambdy.dil03;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.time.*;
import java.time.format.*;

public class Lekce03 {
    public static void main(String[] args) {
        // Odkomentuj prave jedno volani podle pracovniho snimku.
        // Prvni spusteni je bez vypisu; zatim neni zapnuta zadna uloha.
        // ukazkaConsumer();
        // ukazkaPredikaty();
        // ukazkaSupplier();
        // consumer();
        // interval();
        // transformace();
        // dodavatele();
        // pozdrav();
        // jmena();
    }

    // Spolecna ukazka: podle pokynu v prezentaci.
    static void ukazkaConsumer() {
        Consumer<String> print = text -> {
            System.out.println(text);
        };
        print.accept("Java");

        Consumer<String> length = s -> System.out.println(s.length());
        Consumer<String> both = print.andThen(length);
        both.accept("Java");
    }

    // Spolecna ukazka: podle pokynu v prezentaci.
    static void ukazkaPredikaty() {
        Predicate<Integer> positive = n -> n > 0;
        Predicate<Integer> even = n -> n % 2 == 0;
        System.out.println(positive.test(-5));
        System.out.println(even.test(0));

        Predicate<Integer> positiveEven = positive.and(even);
        Predicate<Integer> positiveOrEven = positive.or(even);
        System.out.println(positiveEven.test(4));
        System.out.println(positiveEven.test(3));
        System.out.println(positiveOrEven.test(-2));

        Predicate<Integer> odd = even.negate();
        Predicate<Integer> odd2 = Predicate.not(even);
        Predicate<String> isJava = Predicate.isEqual("Java");
        System.out.println(odd.test(3));
        System.out.println(odd2.test(4));
        System.out.println(isJava.test("java"));
    }

    // Spolecna ukazka: podle pokynu v prezentaci.
    static void ukazkaSupplier() {
        Supplier<Double> random = () -> Math.random();
        double a = random.get();
        double b = random.get();
        System.out.println(a);
        System.out.println(b);
    }

    // Uloha 3.1. Čtyři řádky: Java, 4, prázdný řádek, 0.
    static void consumer() {
        Consumer<String> print = s -> System.out.println(s);
        Consumer<String> length = s -> System.out.println(s.length());
        Consumer<String> both = print.andThen(length);
        both.accept("Java");
        both.accept("");
    }

    // Uloha 3.2. inRange: false, true, true, false. outside má vždy opačný výsledek.
    static void interval() {
        int[] values = {10, 11, 100, 101};
        Predicate<Integer> above10 = n -> n > 10;
        Predicate<Integer> atMost100 = n -> n <= 100;
        Predicate<Integer> inRange = above10.and(atMost100);
        Predicate<Integer> outside = inRange.negate();
        for (int n : values) {
            System.out.println(n + ": " + inRange.test(n) + ", " + outside.test(n));
        }
    }

    // Uloha 3.3. Vysledek: JAVA / Vysledek: s koncovou mezerou / Java.
    static void transformace() {
        Function<String, String> strip = s -> s.strip();
        Function<String, String> upper = s -> s.toUpperCase(Locale.ROOT);
        Function<String, String> prefix = s -> "Vysledek: " + s;
        Function<String, String> all = strip.andThen(upper).andThen(prefix);
        Function<String, String> same = Function.identity();
        System.out.println(all.apply(" java "));
        System.out.println(all.apply(" "));
        System.out.println(same.apply("Java"));
    }

    // Uloha 3.4. Pevné testy: vyborne, nedostatecne, 09:05. Náhodná známka i aktuální čas se mění.
    static void dodavatele() {
        IntFunction<String> describe = grade -> switch (grade) {
            case 1 -> "vyborne";
            case 2 -> "chvalitebne";
            case 3 -> "dobre";
            case 4 -> "dostatecne";
            case 5 -> "nedostatecne";
            default -> throw new IllegalArgumentException("znamka");
        };
        System.out.println(describe.apply(1));
        System.out.println(describe.apply(5));
        Supplier<String> grade = () -> describe.apply(1 + (int) (Math.random() * 5));
        System.out.println(grade.get());

        DateTimeFormatter hhmm = DateTimeFormatter.ofPattern("HH:mm");
        Supplier<String> time = () -> LocalTime.now().format(hhmm);
        System.out.println(time.get());
        System.out.println(LocalTime.of(9, 5).format(hhmm));
    }

    // Uloha 3.5. Celkem tři řádky Ahoj; volání s nulou nic nepřidá.
    static void pozdrav() {

    }

    // Uloha 3.6. Výpis ADAM a ALENA. Pro prázdný seznam žádný výpis.
    static void jmena() {
        List<String> names = List.of("Eva", "", "Adam", "Alena");
        Predicate<String> startsA = s -> s.startsWith("A");
        Function<String, String> upper = s -> s.toUpperCase(Locale.ROOT);
        Consumer<String> print = s -> System.out.println(s);
        for (String name : names) {
            if (startsA.test(name)) {
                print.accept(upper.apply(name));
            }
        }
    }
}