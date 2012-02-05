# Copyright Info

The archive `scripts.tar.gz` contains three sql scripts:

1. language.sql
2. lexicon.sql (from perseus hib_lemmas.sql)
3. morphology.sql (from greek.morph.xml and latin.morph.xml)

each of which creates and populates a table (repectively):

1. ARS.language
2. ARS.lexicon
3. ARS.morphology

The data in these files originates from the [Tufts University Perseus project](http://www.perseus.tufts.edu).

The data contained in `lexicon.sql` originates from the file `hib_lemmas.sql` in the open-source
[Java Hopper](http://sourceforge.net/projects/perseus-hopper/) which is licensed under the
[MPL (Mozilla Public License) 1.1](http://www.mozilla.org/MPL/1.1/). The modifications here pertain to eliding some
columns, eliding records pertaining to languages other than Greek and Latin, and altering the SQL to make it possible
to import into `Derby` (the original targeted `MySQL`).

The data contained in `morphology.sql` originated from the Perseus files `greek.morph.xml` and `latin.morph.xml` which
are contained in the
[Greek and Roman collection texts](http://www.perseus.tufts.edu/hopper/opensource/downloads/texts/hopper-texts-GreekRoman.tar.gz)
and which are released under the
[Creative Commons ShareAlike 3.0 License](http://creativecommons.org/licenses/by-sa/3.0/us/).

Therefore:

- `language.sql` and `lexicon.sql` are released under the MPL 1.1
- `morphology.sql` is release under the Creative Commons ShareAlike 3.0 License


