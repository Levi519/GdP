/**
 * Mengen nichtnegativer ganzer Zahlen in kompakter
 * Speicherrepraesentation: ob eine Zahl in der Menge enthalten
 * ist, wird durch EIN BIT im Speicher erfasst!
 *
 * <br>
 * Beispiel:
 * <br>
 * <code>
 * <br>IntSet set = new IntSet(8);
 * <br>int a[] = { 1, 3, 4, 5 };
 * <br>set.include( a );
 * <br>
 * <br> ... +---+---+---+---+---+---+---+---+
 * <br> ... | 0 | 0 | 1 | 1 | 1 | 0 | 1 | 0 |
 * <br> ... +---+---+---+---+---+---+---+---+
 * <br></code>
 */
public class IntSet implements Iterable<Integer> {
	private static final int BitsPerWord = Integer.SIZE;
	private int[] data;
	private int size;

	/**
	 * Konstruiert ein leere Zahlenmenge der Kapazitaet <code>n</code>:
	 * eine Menge, die (nichtnegative ganze) Zahlen im
	 * Bereich 0 bis n-1 als Elemente enthalten kann.
	 *
	 * @param n die Kapazitaet der Menge
	 */
	public IntSet(int n) {
		data = new int[(n + 31) / 32];
		size = n;
	}

	/**
	 * Ermittelt die Kapazitaet der Menge.
	 *
	 * @return die Kapazitaet der Menge
	 */
	public int capacity() {
		return size;
	}

	/**
	 * Erzeugt aus <code>this</code> eine neue (identisch belegte) Zahlenmenge,
	 * die Werte im Bereich 0 bis n-1 als Elemente enthalten kann.
	 *
	 * Die Originalmenge bleibt unveraendert!
	 *
	 * @param n die Kapazitaet der Ergebnismenge
	 * @return die Ergebnismenge mit veraenderter Kapazitaet
	 */
	public IntSet resize(int n) {
		IntSet s = new IntSet(n);

		for (int i = 0; i < n; i++) {
			if (i >= this.size){
				break;
			}
			if (this.contains(i)) {
				s.insert(i);
			}
		}

		return s;
	}

	/**
	 * Ermittelt, ob eine nicht-negative ganze Zahl in der Menge enthalten ist.
	 *
	 * @param e eine nichtnegative ganze Zahl
	 * @return ist e in dieser Menge enthalten?
	 */
	public boolean contains(int e) {
		int stelle = e / 32;
		e %= 32;
		return ((data[stelle] >> e) & 1) == 1;
	}

	/**
	 * Nimmt die Zahl <code>e</code> in diese Menge auf.
	 *
	 * @param e eine nichtnegative ganze Zahl zwischen 0 und capacity
	 */
	public void insert(int e) {
		int stelle = e / 32;
		e %= 32;
		int mask = 1 << e;
		data[stelle] = data[stelle] | mask;
	}

	/**
	 * Nimmt alle Elemente aus dem Array <code>es</code> in die Menge auf.
	 *
	 * @param es ein Array von nichtnegativen ganzen Zahlen
	 */
	public void insert(int[] es) {
		for (int i = 0; i < es.length; i++) {
			insert(es[i]);
		}
	}


	/**
	 * Entfernt die Zahl <code>e</code> aus dieser Menge.
	 *
	 * @param e eine nichtnegative ganze Zahl zwischen 0 und capacity
	 */
	public void remove(int e) {
		int stelle = e / 32;
		e %= 32;
		int mask = 1 << e;
		data[stelle] = data[stelle] ^ mask;
	}

	/**
	 * Entfernt alle Elemente aus dem Array <code>es</code> aus der Menge.
	 *
	 * @param es ein Array von nichtnegativen ganzen Zahlen
	 */
	public void remove(int[] es) {
		for (int i = 0; i < es.length; i++) {
			remove(es[i]);
		}
	}

	/**
	 * Berechnet die Komplementaermenge zu dieser Menge: die Menge gleicher
	 * Kapazitaet, die genau alle Elemente enthaelt, die nicht in
	 * <code>this</code> enthalten sind.
	 *
	 * Originalmenge bleibt unveraendert !
	 *
	 * @return die Komplementaermenge
	 */
	public IntSet complement() {
		IntSet s = new IntSet(size);
		int zahl = 0;

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < 32; j++) {
				if ((data[i] & (1 << j)) == 0) {
					if (zahl < this.capacity() && j < this.capacity()) {
						s.insert(zahl);
					}
				}
				zahl++;
			}
		}

		return s;
	}

	/**
	 * Erzeuge aus <code>s1</code> und <code>s2</code> die Vereinigungsmenge
	 * <br>
	 * es wird eine Menge der Kapazitaet der groesseren
	 * Kapazitaet der beiden Mengen erzeugt
	 * <br>
	 * <code>s1</code> und <code>s2</code> bleiben unveraendert !
	 *
	 * @param s1 Mengen, die
	 * @param s2 verknuepft werden sollen
	 * @return die Vereinigungsmenge
	 */
	public static IntSet union(IntSet s1, IntSet s2) {
		IntSet s3;
		IntSet s4;
		boolean bigger = false;
		if (s1.capacity() > s2.capacity()) {
			s3 = new IntSet(s1.capacity());
			s4 = s2.resize(s1.capacity());
		}else {
			s3 = new IntSet(s2.capacity());
			s4 = s1.resize(s2.capacity());
			bigger = true;
		}

		if (bigger) {
			for (int i = 0; i < s3.capacity(); i++) {
				if (s4.contains(i) || s2.contains(i)) {
					s3.insert(i);
				}
			}
		}else {
			for (int i = 0; i < s3.capacity(); i++) {
				if (s1.contains(i) || s4.contains(i)) {
					s3.insert(i);
				}
			}
		}

		return s3;
	}

	/**
	 * Erzeuge aus <code>s1</code> und <code>s2</code> die symmetrische
	 * Differenzmenge.
	 *
	 * Die Eingabemengen bleiben unveraendert!
	 *
	 * @param s1 erste Menge
	 * @param s2 zweite Menge
	 * @return die symmetrische Differenzmenge
	 */
	public static IntSet intersection(IntSet s1, IntSet s2) {

		IntSet s3;
		if (s1.capacity() > s2.capacity()) {
			s3 = new IntSet(s1.capacity());
		}else{
			s3 = new IntSet(s2.capacity());
		}

		for (int i = 0; i < s3.capacity(); i++) {
			s3.data[i] = s1.data[i] ^ s2.data[i];
		}

		return s3;
	}

	/**
	 * Erzeugt aus <code>s1</code> und <code>s2</code> die Differenzmenge mit
	 * der Kapazitaet von s1.
	 *
	 * Beide Eingabemengen bleiben unveraendert!
	 *
	 * @param s1 erste Menge
	 * @param s2 zweite Menge
	 * @return die Differenzmenge
	 */
	public static IntSet difference(IntSet s1, IntSet s2) {
		IntSet s3 = new IntSet(s1.capacity());

		for (int i = 0; i < s3.data.length; i++) {
			if (i < s2.data.length) {
				s3.data[i] = s1.data[i] & ~s2.data[i];
			} else {
				s3.data[i] = s1.data[i];
			}
		}


		return s3;
	}

	/**
	 * Stringrepraesentation der Bits dieser Menge beginnend mit Index 0,
	 * etwa "01011100".
	 *
	 * @return Stringrepraesentation der Bits der Menge
	 */
	public String bits() {
		String bitString = "";
		int laufen = 0;

		for (int i = 0; i < data.length; i++) {
			laufen = size;
			for (int j = 0; j < laufen; j++) {
				if ((data[i] & (1 << j)) != 0){
					bitString += "1";
				}else {
					bitString += "0";
				}
			}
			laufen -= 32;
		}

		return bitString;
	}

	/**
	 * Ermittelt die Stringrepraesentation dieser Menge, etwa "{1, 3, 4, 6}".
	 *
	 * @return Stringrepraesentation der Menge
	 */
	@Override
	public String toString() {
		String s = "{";
		int zahl = 0;

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < 32; j++) {
				if ((data[i] & (1 << j)) != 0){
					s += zahl + ", ";
				}
				zahl++;
			}
		}

		if (s.length() > 1) { // Prüfe, ob mehr als nur die "{" vorhanden ist
			s = s.substring(0, s.length() - 2); // Letztes Komma und Leerzeichen entfernen
		}

		return s + "}";
	}

	/**
	 * Erzeugt einen Iterator, mit dem ueber die Menge iteriert werden kann:
	 * <br>
	 * <code>
	 * <br>for (IntSet.Iterator it = menge.iterator(); it.hasNext(); )
	 * <br>         { ... it.next() ... }
	 * </code>
	 *
	 * @return ein Iterator auf diese Menge
	 */
	@Override
	public Iterator iterator() {
		return new Iterator(this);
	}

	/**
	 * IntSet Mengen-Iterator
	 */
	/**
	 * IntSet Mengen-Iterator
	 */
	public class Iterator implements java.util.Iterator<Integer> {
		private final IntSet s;
		private final int cap;
		private int index;

		/**
		 * Erzeugt einen Iterator über <code>s</code>.
		 *
		 * @param s die Menge, über die iteriert werden soll
		 */
		public Iterator(IntSet s) {
			this.s = s;
			this.cap = s.capacity();
			this.index = 0;
		}

		/**
		 * Ermittelt, ob noch weitere Elemente in der Menge existieren.
		 */
		@Override
		public boolean hasNext() {
			while (index < cap) {
				int stelle = index / 32;
				int bitPosition = index % 32;
				if ((s.data[stelle] & (1 << bitPosition)) != 0) {
					return true;
				}
				index++;
			}
			return false;
		}

		/**
		 * Gibt das nächste Element zurück und setzt den Iterator weiter.
		 *
		 * @return das nächste Element
		 */
		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException("Keine Elemente mehr in s");
			}
			return index++;
		}
	}
}