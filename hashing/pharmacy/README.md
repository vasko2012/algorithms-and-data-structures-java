# Pharmacy stock lookup (hash table)

A pharmacist needs to look up medicines quickly: whether a medicine is on the *positive list* (covered by health insurance), its price, and how many pieces are in stock. When a customer orders, the stock goes down, unless they want more than is available.

The lookup is a **hand-written hash table with separate chaining** (no `java.util.HashMap`).

## Input / output

- Line 1: number of medicines `N`
- Next `N` lines: `NAME positiveList(1/0) price stock`
- Then pairs of lines: a medicine name (any letter case) and the number of pieces ordered, until `KRAJ`

For each order the program prints the medicine (name, `POZ`/`NEG`, price, stock) and then whether the order went through. The output messages are in Macedonian, as the original problem specified:

| Message | Meaning |
|---|---|
| `Nema takov lek` | No such medicine |
| `Nema dovolno lekovi` | Not enough in stock, nothing changes |
| `Napravena naracka` | Order placed, stock reduced |

## How it works

- **Hash function.** Only the first three letters of the upper-cased name are used:
  `h(w) = (29·(29·(29·0 + c1) + c2) + c3) mod 102780`.
  Treating the letters as digits of a base-29 number is the classic polynomial string hash.
- **Collisions.** `ACIKLOVIR` and `ACIPAN` start with the same three letters, so they always get the same hash. Each bucket is a **linked list**; entries in the same bucket are told apart with `equals()` on the full name.
- **Case-insensitive keys.** `MedicineKey` upper-cases the name, so `hydroCyklin20` finds `HYDROCYKLIN20`.
- `hashCode()` and `equals()` are overridden together. Two keys that are `equals` must have the same hash, otherwise lookups silently fail.

**Complexity:** *O(1)* average per lookup; *O(k)* in the worst case, where *k* is the number of medicines sharing a three-letter prefix.

## Run

```bash
javac Pharmacy.java
java Pharmacy < sample-input.txt
```

The expected output is in `sample-output.txt`.
