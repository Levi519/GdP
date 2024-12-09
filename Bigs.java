class Bigs {

    //addiert die Ziffernfelder a und b
    public static int[] add(int[] a, int[] b) {
        int zaehler = 0;
        while (zaehler < b.length && zaehler < a.length) {
            if (a[zaehler] + b[zaehler] < 9) {
                a[zaehler] += b[zaehler];
            } else {
                a[zaehler + 1] += 1;
                if (a[zaehler + 1] > 9) {
                    a[zaehler + 1] -= 9;
                    a[zaehler + 2] += 1;
                }
                a[zaehler] += b[zaehler] - 9;
            }
            zaehler++;
        }
        return a;
    }

    // gibt das Ziffernfeld n in lesbarer dezimaler Form aus
    static void print(int[] n) {
        String output = "";
        for (int i = 0; i < n.length; i++) {
            output += n[i] + "";
        }
        System.out.println(output);
    }

    // konstruiert ein einstelliges Ziffernfeld aus d
    static int[] digit(int d) {
        int[] a = new int[1];
        a[0] = d;
        return a;
    }

    // konstruiert das Ziffernfeld, welches den Wert Null repraesentiert
    static int[] Null() {
        int[] out = new int[1];
        out[0] = 0;
        return out;
    }

    // konstruiert das Ziffernfeld, welches den Wert Eins repraesentiert
    static int[] One() {
        int[] out = new int[1];
        out[0] = 1;
        return out;
    }

    // Rest des Ziffernfeldes n bei Division durch 10 (eine int-Zahl!)
    static int mod10(int[] n) {
        return n[0];
    }

    // ganzzahliger Quotient bei Division durch 10
    static int[] div10(int[] n) {
        int[] newn = new int[n.length - 1];
        //Array kopieren und dabei einen nach links verschieben
        System.arraycopy(n, 1, newn, 0, n.length - 1);
        return newn;
    }

    // Umwandlung einer int-Zahl in ein Ziffernfeld
    static int[] fromInt(int n) {
        int[] out = new int[(int) Math.log10(n) + 1];
        for (int i = 0; i < out.length; i++) {
            out[i] = n % 10;
            n /= 10;
        }
        return out;
    }

    // kopiert den Wert von a
    static int[] copy(int[] n) {

        return n;
    }

    // multipliziert das Ziffernfeld a mit einer int-Zahl
    static int[] times(int[] n, int d) {
        int speicher = 0;
        int[] a = new int[n.length + 1];
        for (int i = 0; i < n.length; i++) {
            a[i] = n[i] * d + speicher;
            speicher = a[i] / 10;
            a[i] %= 10;
        }
        if (speicher > 0) {
            a[n.length] = speicher;
        }
        if (a[a.length - 1] != 0) {
            return a;
        } else {
            System.arraycopy(a, 0, n, 0, n.length);
            return n;
        }
    }

    // multipliziert das Ziffernfeld n mit 10
    static int[] times10(int[] n) {
        int[] a = new int[n.length + 1];
        System.arraycopy(n, 0, a, 1, n.length);
        return a;
    }

    static int[] times(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                int position = i + j;
                c[position] += a[i] * b[j];
            }
        }

        for (int i = 0; i < c.length - 1; i++) {
            c[i + 1] += c[i] / 10;
            c[i] %= 10;
        }

        int start = c.length - 1;
        while (start > 0 && c[start] == 0) {
            start--;
        }

        int[] result = new int[start + 1];
        for (int i = 0; i <= start; i++) {
            result[i] = c[i];
        }

        return result;

    }

    // Quadratzahl eines Ziffernfeldes
    static int[] square(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] * a[i];
            if (a[i] > 9) {
                a[i + 1] += a[i] / 10;
                a[i] %= 10;
            }
        }
        return a;
    }

    // Kubikzahl eines Ziffernfeldes
    static int[] cubic(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] * a[i] * a[i];
            if (a[i] > 9) {
                a[i + 1] += a[i] / 10;
                a[i] %= 10;
                if (a[i + 1] > 9) {
                    a[i + 2] += a[i] / 10;
                    a[i] %= 10;
                    if (a[i + 2] > 9) {
                        a[i + 3] += a[i] / 10;
                        a[i] %= 10;
                    }
                }
            }

        }
        return a;
    }

    // ist dieses Ziffernfeld ein Palindrom? Bemühen Sie sich um eine Implementation,
    // die ohne ein weiteres Zahlenfeld auskommt !
    static boolean palindrom(int[] a) {
        for (int i = 0; i < a.length / 2; i++) {
            if (a[i] != a[a.length - i - 1]) {
                return false;
            }
        }
        return true;
    }

    // Test auf kleiner-Relation zweier Ziffernfelder: a < b ?
    static boolean less(int[] a, int[] b) {
        if (a.length < b.length) {
            return true;
        } else if (a.length == b.length) {
            for (int i = a.length - 1; i >= 0; i++) {
                if (a[i] > b[i]) {
                    return false;
                } else if (a[i] < b[i]) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    // Test auf Gleichheit zweier Ziffernfelder
    static boolean equal(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    // Test auf Korrektheit eines Ziffernfeldes: Feld existiert und enthaelt
    // mindenstens eine Ziffer, alle Positionen liegen zwischen 0 und 9
    // keine fuehrenden Nullen (ausser bei Null selbst)
    static boolean ok(int[] n) {
        if (n == null) {
            return false;
        }
        for (int i = 0; i < n.length; i++) {
            if (n[i] < 0 || n[i] > 9) {
                return false;
            }
        }
        if (n[0] == 0 && n.length > 1) {
            return false;
        }
        return true;
    }

    // gibt die (kleinste) Ziffer mit der groessten Haeufigkeit in n aus
    static void maxDigit(int[] n) {
        for (int i = 0; i < n.length - 1; i++) {
            for (int j = 0; j < n.length - i - 1; j++) {
                if (n[j] > n[j + 1]) {
                    int temp = n[j];
                    n[j] = n[j + 1];
                    n[j + 1] = temp;
                }
            }
        }
        int max = 0;
        int count = 1;
        int stelle = 0;
        for (int i = 0; i < n.length - 1; i++) {
            if (n[i] == n[i + 1]) {
                count++;
            } else {
                if (count > max || count == max && n[i] < stelle) {
                    max = count;
                    stelle = n[i];
                }
                count = 1;
            }
        }
        if (count > max || (count == max && n[n.length - 1] < stelle)) {
            stelle = n[n.length - 1];
            max = count;
        }
        System.out.println(stelle + ": " + max);
    }

    public static void main(String[] s) {
        int[] a = One();

        for (int i = 0; i < 33222; ++i) {
            a = times(a, 2);
        }

        System.out.println("2^33222 hat " + a.length + " Stellen");
        print(a);
        System.out.println();

        int[] b = fromInt(13);
        int[] c = copy(b);

        for (int i = 1; i < 8978; ++i) {
            c = times(c, b);
        }

        System.out.println("13^8978 hat " + c.length + " Stellen");
        print(c);
        System.out.println();

        System.out.println(less(a, c)); // beantwortet die Frage aus der Aufgabenueberschrift
        maxDigit(a);
        maxDigit(c);
    }
}